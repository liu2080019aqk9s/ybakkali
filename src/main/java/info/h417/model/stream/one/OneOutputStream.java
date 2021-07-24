package info.h417.model.stream.one;

import info.h417.model.stream.BaseOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OneOutputStream extends BaseOutputStream {

    private FileWriter fileWriter;

    /**
     * Constructor of an outputStream that write one character at time
     *
     * @param filename The path of the file
     */
    public OneOutputStream(String filename) {
        super(filename);
    }

    @Override
    public void create() throws IOException {
        this.fileWriter = new FileWriter(filename);
    }

    @Override
    public void close() throws IOException {
        this.fileWriter.close();
    }

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void writeln(String line) throws IOException {
        for(char character : line.toCharArray()){
            fileWriter.write(character);
            fileWriter.flush();
        }
        fileWriter.write("\n");
        fileWriter.flush();
    }
}
