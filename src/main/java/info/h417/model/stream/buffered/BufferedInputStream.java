package info.h417.model.stream.buffered;

import info.h417.model.stream.BaseInputStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedInputStream extends BaseInputStream {

    private BufferedReader bufferedReader;

    /**
     * Constructor of an inputStream that use a buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedInputStream(String filename) {
        super(filename);

    }

    /**
     * Open an existing file for reading with the BufferedReader.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void open() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(filename));
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.bufferedReader.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        this.bufferedReader.close();
        this.bufferedReader = new BufferedReader(new FileReader(filename));
        this.bufferedReader.skip(pos);
    }

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return !this.bufferedReader.ready();
    }

    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    @Override
    public String readln() throws IOException {
        return this.bufferedReader.readLine();
    }
}
