package info.h417.model.stream;

import java.io.IOException;

public abstract class BaseOutputStream extends BaseStream{

    /**
     * Basic Constructor of an outputStream
     *
     * @param filename The path of the file
     */
    public BaseOutputStream(String filename) {
        super(filename);
    }

    /**
     * Create a new file.
     *
     * @throws IOException If some I/O error occurs
     */
    public abstract void create() throws IOException;

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    public abstract void close() throws IOException;

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    public abstract void writeln(String line) throws IOException;

}
