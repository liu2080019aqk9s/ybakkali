package info.h417.model.stream.buffered;

import info.h417.model.stream.BaseOutputStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedOutputStream extends BaseOutputStream {

    private BufferedWriter bufferedWriter;

    /**
     * Constructor of an outputStream that uses buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedOutputStream(String filename) {
        super(filename);
    }

    /**
     * Create a new file.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void create() throws IOException {
        this.bufferedWriter = new BufferedWriter(new FileWriter(filename));
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.bufferedWriter.close();
    }

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void writeln(String line) throws IOException {

        this.bufferedWriter.write(line + "\n");
        this.bufferedWriter.flush();
    }
}
