package info.h417.model.stream;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class BaseInputStream extends BaseStream {
    protected FileInputStream in;

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
    public void open() throws IOException {
        if(in == null){
            in = new FileInputStream(filename);
        }
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    public void close() throws IOException{
        in.close();
    }

    /**
     * Move the file cursor to the new position.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    public void seek(long pos) throws IOException {
        in.getChannel().position(pos);
    }


    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    public boolean end_of_stream() throws IOException {
        return in.available() == 0;
    }


    /**
     *  Get the size of the file.
     *
     * @return The file size
     * @throws IOException If some I/O error occurs
     */
    public long sizeFile() throws IOException {
        return in.getChannel().size();
    }
}
