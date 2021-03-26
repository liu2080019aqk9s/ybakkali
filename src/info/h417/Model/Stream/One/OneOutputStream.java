package info.h417.Model.Stream.One;

import info.h417.Model.Stream.BaseOutputStream;

public class OneOutputStream extends BaseOutputStream {


    /**
     * Constructor of an outputStream that write one character at time
     *
     * @param filename The path of the file
     */
    public OneOutputStream(String filename) {
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
