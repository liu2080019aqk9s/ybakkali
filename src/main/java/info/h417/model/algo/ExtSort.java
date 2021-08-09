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
        this.outputFilename = "ExtSortOutput.csv";
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

        List<String> tempFilesNames = new ArrayList<>();
        Queue<BaseInputStream> queue = new LinkedList<>();

        filesInitialisation(k - 1, M, fileName, tempFilesNames, queue);
        multiWayMerge(k - 1, d, tempFilesNames, queue);

        deleteTemporaryFiles(tempFilesNames);
    }

    /**
     * Initialise the files to begin the algorithm.
     *
     * @param k The k-th column to sort
     * @param M The internal memory approximate size
     * @param fileName The input stream fileName
     * @param tempFilesNames The list of all the temporary files
     * @param queue The queue
     * @throws IOException If some I/O error occurs
     */
    private void filesInitialisation(int k, int M, String fileName, List<String> tempFilesNames, Queue<BaseInputStream> queue) throws IOException {

        BaseInputStream inputStream = generator.getInputStream(fileName);
        inputStream.open();
        List<List<String>> buffer = new ArrayList<>();
        int length = 0, i = 0;

        while (!inputStream.end_of_stream()) {
            String tempLine = inputStream.readln();
            length += tempLine.length();
            buffer.add(Arrays.asList(tempLine.split(",", -1)));

            if (length >= M || inputStream.end_of_stream()) {
                String tempFilename = "tempFile" + i;
                tempFilesNames.add(tempFilename);
                BaseOutputStream tempOutputStream = writeGenerator.getOutputStream(tempFilename);
                tempOutputStream.create();

                buffer.sort(Comparator.comparing(o -> o.get(k)));

                for (List<String> tempString : buffer) {
                    tempOutputStream.writeln(String.join(",", tempString));
                }
                tempOutputStream.close();

                queue.add(generator.getInputStream(tempFilename));
                i++;
                length = 0;
                buffer.clear();
            }
        }
        inputStream.close();
    }

    /**
     * Run the ExtSort algorithm.
     *
     * @param k The k-th column to sort
     * @param d The maximum number of files to merge at the same time
     * @param tempFilesNames The list of all the temporary files
     * @param queue The queue
     * @throws IOException If some I/O error occurs
     */
    private void multiWayMerge(int k, int d, List<String> tempFilesNames, Queue<BaseInputStream> queue) throws IOException {
        int i = 0;
        while (!queue.isEmpty()) {

            PriorityQueue<Map.Entry<BaseInputStream, List<String>>> toMergePriorityQueue = new PriorityQueue<>(d, Comparator.comparing(o -> o.getValue().get(k)));

            String outputStreamFilename;
            String tempFilename = "mergeFile" + i;

            if (queue.size() <= d) {
                outputStreamFilename = this.outputFilename;
            } else {
                tempFilesNames.add(tempFilename);
                outputStreamFilename = tempFilename;
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

            merge(toMergePriorityQueue, outputStreamFilename);

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
     * @param filename The base output stream filename
     * @throws IOException If some I/O error occurs
     */
    private void merge(PriorityQueue<Map.Entry<BaseInputStream, List<String>>> toMergePriorityQueue, String filename) throws IOException {

        BaseOutputStream baseOutputStream = writeGenerator.getOutputStream(filename);
        baseOutputStream.create();

        while (!toMergePriorityQueue.isEmpty()) {
            Map.Entry<BaseInputStream, List<String>> currentInputStream = toMergePriorityQueue.remove();
            baseOutputStream.writeln(String.join(",", currentInputStream.getValue()));
            if (!currentInputStream.getKey().end_of_stream()) {
                List<String> tempLine = Arrays.asList(currentInputStream.getKey().readln().split(",", -1));
                currentInputStream.setValue(tempLine);
                toMergePriorityQueue.add(currentInputStream);
            } else {
                currentInputStream.getKey().close();
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
