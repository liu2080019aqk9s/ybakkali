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

    public void begin(String fileName, int k, int M, int d) throws IOException {
        BaseInputStream inputStream = generator.getInputStream(fileName);
        BaseOutputStream outputStream = writeGenerator.getOutputStream(outputString);
        inputStream.open();
        outputStream.create();

        List<String> tempFilesNames = new ArrayList<>();
        Queue<BaseInputStream> queue = new LinkedList<>();
        List<List<String>> buffer = new ArrayList<>();

        filesInitialisation(k, M, inputStream, tempFilesNames, queue, buffer);

        multiWayMerge(k, d, outputStream, tempFilesNames, queue);

        deleteTemporaryFiles(tempFilesNames);

        inputStream.close();
    }

    private void filesInitialisation(int k, int M, BaseInputStream inputStream, List<String> tempFilesNames, Queue<BaseInputStream> queue, List<List<String>> buffer) throws IOException {
        int length = 0;
        int i = 0;
        while (!inputStream.end_of_stream()) {
            String tempLine = inputStream.readln();
            length += tempLine.length();
            List<String> temp = Arrays.asList(tempLine.split(","));
            if (temp.size() <= k) {
                throw new IOException("k bigger than the number of columns"); // TODO maybe change the exception aha ha
            }
            buffer.add(temp);

            if (length >= M) {
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
                baseOutputStream.create();
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

    private void deleteTemporaryFiles(List<String> tempFilesNames) throws IOException {
        for (String tempFileName : tempFilesNames) {
            Files.deleteIfExists(Paths.get(tempFileName));
        }
    }

    private void merge(List<BaseInputStream> toMergeList, BaseOutputStream baseOutputStream, int k) throws IOException {
        List<List<String>> current = new ArrayList<>(toMergeList.size());

        for (int i = 0; i < toMergeList.size(); i++) {
            BaseInputStream baseInputStream = toMergeList.get(i);
            baseInputStream.open();
            if (!baseInputStream.end_of_stream()) {
                current.add(Arrays.asList(baseInputStream.readln().split(",")));
            } else {
                toMergeList.remove(i);
                i--;
            }
        }

        while (!current.isEmpty()) {
            int min = current.indexOf(Collections.min(current, Comparator.comparing(o -> o.get(k))));
            baseOutputStream.writeln(String.join(",", current.remove(min)));
            if (!toMergeList.get(min).end_of_stream()) {
                current.add(min, Arrays.asList(toMergeList.get(min).readln().split(",")));
            } else {
                toMergeList.get(min).close();
                toMergeList.remove(min);
            }
        }
        baseOutputStream.close();
    }
}
