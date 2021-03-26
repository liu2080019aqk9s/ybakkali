package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseOutputStream;

public class OneBufferOutputStream extends BaseOutputStream {

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
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
