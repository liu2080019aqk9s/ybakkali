package info.h417.Model.Stream.Buffered;

import info.h417.Model.Stream.BaseInputStream;

public class BufferedInputStream extends BaseInputStream {


    /**
     * Constructor of an inputStream that use a buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedInputStream(String filename) {
        super(filename);
    }

    @Override
    public String readln() {
        return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void seek(long pos) {

    }

    @Override
    public boolean end_of_stream() {
        return false;
    }
}
