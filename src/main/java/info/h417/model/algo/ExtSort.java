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
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator The generator
     */
    public ExtSort(Generator generator,Generator writeGenerator) {
        super(generator,writeGenerator);
        this.outputString = "ExtSortOutput.csv";
    }

    /**
     * @param fileName
     * @param k
     * @param M
     * @param d
     * @throws IOException
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
     * @param k
     * @param M
     * @param inputStream
     * @param tempFilesNames
     * @param queue
     * @param buffer
     * @throws IOException
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
     * @param k
     * @param d
     * @param outputStream
     * @param tempFilesNames
     * @param queue
     * @throws IOException
     */
    private void multiWayMerge(int k, int d, BaseOutputStream outputStream, List<String> tempFilesNames, Queue<BaseInputStream> queue) throws IOException {
        int i = 0;
        while (!queue.isEmpty()) {

            List<BaseInputStream> toMergeList = new ArrayList<>();

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
                toMergeList.add(queue.remove());
                a++;
            }

            merge(toMergeList, baseOutputStream, k);

            if (!queue.isEmpty()) {
                queue.add(generator.getInputStream(tempFilename));
            }
            i++;

        }
    }

    /**
     * @param toMergeList
     * @param baseOutputStream
     * @param k
     * @throws IOException
     */
    private void merge(List<BaseInputStream> toMergeList, BaseOutputStream baseOutputStream, int k) throws IOException {
        List<List<String>> current = new ArrayList<>(toMergeList.size());

        for (int i = 0; i < toMergeList.size(); i++) {
            BaseInputStream baseInputStream = toMergeList.get(i);
            baseInputStream.open();
            if (!baseInputStream.end_of_stream()) {
                current.add(Arrays.asList(baseInputStream.readln().split(",",-1)));
            } else {
                toMergeList.remove(i);
                i--;
            }
        }

        baseOutputStream.create();
        while (!current.isEmpty()) {
            int min = 0;
            for (int i = 1; i < current.size(); i++) {
                if (current.get(i).get(k).compareTo(current.get(min).get(k)) < 0) {
                    min = i;
                }
            }

            baseOutputStream.writeln(String.join(",", current.remove(min)));
            if (!toMergeList.get(min).end_of_stream()) {
                current.add(min, Arrays.asList(toMergeList.get(min).readln().split(",", -1)));
            } else {
                toMergeList.get(min).close();
                toMergeList.remove(min);
            }
        }
        baseOutputStream.close();
    }

    /**
     * @param tempFilesNames
     * @throws IOException
     */
    private void deleteTemporaryFiles(List<String> tempFilesNames) throws IOException {
        for (String tempFileName : tempFilesNames) {
            Files.deleteIfExists(Paths.get(tempFileName));
        }
    }
}
