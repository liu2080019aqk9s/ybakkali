package info.h417.Model.Stream.One;

import info.h417.Model.Stream.BaseInputStream;

public class OneInputStream extends BaseInputStream {


    /**
     * Constructor of an inputStream that reads one character at time
     *
     * @param filename The path of the file
     */
    public OneInputStream(String filename) {
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
