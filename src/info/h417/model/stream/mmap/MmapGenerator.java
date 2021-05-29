package info.h417.model.stream.mmap;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

public class MmapGenerator extends Generator{
    private final int nbCharacters;

    public MmapGenerator(int nbCharacters) {
        this.nbCharacters = nbCharacters;
    }

    @Override
    public BaseInputStream getInputStream(String filename) {
        return new MmapInputStream(filename,nbCharacters);
    }

    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new MmapOutputStream(filename,nbCharacters);
    }
}
