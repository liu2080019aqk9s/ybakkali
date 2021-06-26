package info.h417.model.stream.mmap;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

public class MMapGenerator extends Generator{
    private final int nbCharacters;

    public MMapGenerator(int nbCharacters) {
        this.nbCharacters = nbCharacters;
    }

    @Override
    public BaseInputStream getInputStream(String filename) {
        return new MMapInputStream(filename,nbCharacters);
    }

    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new MMapOutputStream(filename,nbCharacters);
    }
}
