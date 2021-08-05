package info.h417.model.algo;

import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ExtSort extends BaseAlgo {

    /**
     * A generic Constructor that takes a generator as parameter.
     *
     * @param generator The read generator
     * @param writeGenerator The write generator
     */
    public ExtSort(Generator generator,Generator writeGenerator) {
        super(generator,writeGenerator);
        this.outputString = "ExtSortOutput.csv";
    }

    /**
     * Launch the ExtSort algorithm on the specified fileName with the specified parameters.
     *
     * @param fileName The fileName
     * @param k The k-th column to sort
     * @param M The internal memory approximate size
     * @param d The maximum number of files to merge at the same time
     * @throws IOException If some I/O error occurs
     */
    public void begin(String fileName, int k, int M, int d) throws IOException {
        BaseInputStream inputStream = generator.getInputStream(fileName);
        BaseOutputStream outputStream = writeGenerator.getOutputStream(outputString);
        inputStream.open();
        outputStream.create();

        List<String> tempFilesNames = new ArrayList<>();
        Queue<BaseInputStream> queue = new LinkedList<>();
        List<List<String>> buffer = new ArrayList<>();

        filesInitialisation(k - 1, M, inputStream, tempFilesNames, queue, buffer);
        multiWayMerge(k - 1, d, outputStream, tempFilesNames, queue);

        deleteTemporaryFiles(tempFilesNames);
        inputStream.close();
    }

    /**
     * Initialise the files to begin the algorithm.
     *
     * @param k The k-th column to sort
     * @param M The internal memory approximate size
     * @param inputStream The input stream
     * @param tempFilesNames The list of all the temporary files
     * @param queue The queue
     * @param buffer The buffer
     * @throws IOException If some I/O error occurs
     */
    private void filesInitialisation(int k, int M, BaseInputStream inputStream, List<String> tempFilesNames, Queue<BaseInputStream> queue, List<List<String>> buffer) throws IOException {
        int length = 0, i = 0;
        while (!inputStream.end_of_stream()) {
            String tempLine = inputStream.readln();
            length += tempLine.length();
            buffer.add(Arrays.asList(tempLine.split(",", -1)));

            if (length >= M || inputStream.end_of_stream()) {
                String tempFilename = "tempFile" + i;
                tempFilesNames.add(tempFilename);
                BaseOutputStream tempOutputStream = generator.getOutputStream(tempFilename);
                tempOutputStream.create();

                buffer.sort(Comparator.comparing(o -> o.get(k)));

                for (List<String> tempString : buffer) {
                    tempOutputStream.writeln(String.join(",", tempString));
                }
                tempOutputStream.close();

                BaseInputStream tempInputStream = generator.getInputStream(tempFilename);
                queue.add(tempInputStream);
                i++;
                length = 0;
                buffer.clear();
            }
        }
    }

    /**
     * Run the ExtSort algorithm.
     *
     * @param k The k-th column to sort
     * @param d The maximum number of files to merge at the same time
     * @param outputStream The output stream
     * @param tempFilesNames The list of all the temporary files
     * @param queue The queue
     * @throws IOException If some I/O error occurs
     */
    private void multiWayMerge(int k, int d, BaseOutputStream outputStream, List<String> tempFilesNames, Queue<BaseInputStream> queue) throws IOException {
        int i = 0;
        while (!queue.isEmpty()) {

            PriorityQueue<Map.Entry<BaseInputStream, List<String>>> toMergePriorityQueue = new PriorityQueue<>(d, Comparator.comparing(o -> o.getValue().get(k)));

            BaseOutputStream baseOutputStream;
            String tempFilename = "mergeFile" + i;

            if (queue.size() <= d) {
                baseOutputStream = outputStream;
            } else {
                tempFilesNames.add(tempFilename);
                baseOutputStream = generator.getOutputStream(tempFilename);
            }

            int a = 0;
            while (!queue.isEmpty() && a < d) {
                BaseInputStream tempBaseInputStream = queue.remove();
                tempBaseInputStream.open();
                List<String> tempLine = Arrays.asList(tempBaseInputStream.readln().split(",", -1));
                Map.Entry<BaseInputStream, List<String>> tempEntry = new AbstractMap.SimpleEntry<>(tempBaseInputStream, tempLine);
                toMergePriorityQueue.add(tempEntry);
                a++;
            }

            merge(toMergePriorityQueue, baseOutputStream);

            if (!queue.isEmpty()) {
                queue.add(generator.getInputStream(tempFilename));
            }
            i++;

        }
    }

    /**
     * Merge the temporary files.
     *
     * @param toMergePriorityQueue The queue of the temporary files to merge
     * @param baseOutputStream The base output stream
     * @throws IOException If some I/O error occurs
     */
    private void merge(PriorityQueue<Map.Entry<BaseInputStream, List<String>>> toMergePriorityQueue, BaseOutputStream baseOutputStream) throws IOException {

        baseOutputStream.create();

        while (!toMergePriorityQueue.isEmpty()) {
            Map.Entry<BaseInputStream, List<String>> currentStream = toMergePriorityQueue.remove();
            baseOutputStream.writeln(String.join(",", currentStream.getValue()));
            if (!currentStream.getKey().end_of_stream()) {
                List<String> tempLine = Arrays.asList(currentStream.getKey().readln().split(",", -1));
                currentStream.setValue(tempLine);
                toMergePriorityQueue.add(currentStream);
            } else {
                currentStream.getKey().close();
            }
        }

        baseOutputStream.close();
    }

    /**
     * Delete the temporary files.
     *
     * @param tempFilesNames The list of all the temporary files
     * @throws IOException If some I/O error occurs
     */
    private void deleteTemporaryFiles(List<String> tempFilesNames) throws IOException {
        for (String tempFileName : tempFilesNames) {
            Files.deleteIfExists(Paths.get(tempFileName));
        }
    }
}
