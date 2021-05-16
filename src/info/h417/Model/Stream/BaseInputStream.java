package info.h417.Model.Stream;

import java.io.*;

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
     * read the next line from the stream
     * @return the line
     */
    public abstract String readln() throws IOException;

    /**
     * open an existing file for reading
     */
    public void open() throws IOException {
        if(in == null){
            in = new FileInputStream(filename);
        }
    }

    /**
     * close the stream
     */
    public void close() throws IOException{
        in.close();
    }

    /**
     * move the file cursor to pos so that a subsequent readln reads from position pos to the next end of line
     * @param pos the position in file
     */
    public void seek(long pos) throws IOException {
        in.getChannel().position(pos);
    }

    /**
     * a boolean operation that returns true if the end of stream has been reached.
     */
    public boolean end_of_stream() throws IOException {
        return in.available() == 0;
    }

    /**
     * This function return the size of the file
     */
    public long sizeFile() throws IOException {
        return in.getChannel().size();
    }
}
