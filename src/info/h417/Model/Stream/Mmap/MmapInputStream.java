package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseInputStream;

public class MmapInputStream extends BaseInputStream {


    /**
     * Constructor of an inputStream that reads by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename The path of the file
     * @param nbCharacters
     */
    public MmapInputStream(String filename,int nbCharacters) {
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
