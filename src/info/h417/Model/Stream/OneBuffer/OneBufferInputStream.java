package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseInputStream;

public class OneBufferInputStream extends BaseInputStream {


    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
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
