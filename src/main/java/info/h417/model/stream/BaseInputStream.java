package info.h417.model.stream;

import java.io.IOException;

public abstract class BaseInputStream extends BaseStream {

    /**
     * Basic Constructor of an inputStream
     *
     * @param filename The path of the file
     */
    public BaseInputStream(String filename) {
        super(filename);
    }

    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    public abstract String readln() throws IOException;

    /**
     * Open an existing file for reading.
     *
     * @throws IOException If some I/O error occurs
     */
    public abstract void open() throws IOException;

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    public abstract void close() throws IOException;

    /**
     * Move the file cursor to the new position.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    public abstract void seek(long pos) throws IOException;

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    public abstract boolean end_of_stream() throws IOException;
}
