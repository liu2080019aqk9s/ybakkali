package info.h417.Model.Stream.Buffered;

import info.h417.Model.Stream.BaseOutputStream;

public class BufferedOutputStream extends BaseOutputStream {


    /**
     * Constructor of an outputStream that uses buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedOutputStream(String filename) {
        super(filename);
    }

    @Override
    public void create() {

    }

    @Override
    public void close() {

    }

    @Override
    public void writeln(String text) {

    }
}
