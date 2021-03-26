package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseOutputStream;

public class MmapOutputStream extends BaseOutputStream {

    /**
     * Constructor of an outputStream that write by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename The path of the file
     * @param nbCharacters
     */
    public MmapOutputStream(String filename,int nbCharacters) {
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
