package info.h417.Model.Stream.Mmap;
import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

public class MmapGenerator extends Generator{
    private int nbCharacters;

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
