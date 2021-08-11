package info.h417.model.stream.mmap;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

/**
 *  This class instance an object from MMapStream
 */
public class MMapGenerator extends Generator{
    private final int nbCharacters;

    public MMapGenerator(int nbCharacters) {
        this.nbCharacters = nbCharacters;
    }


    /**
     * Construct the Memory Map input stream
     *
     * @param filename The path of the file
     * @return the InputStream of the file
     */
    @Override
    public BaseInputStream getInputStream(String filename) {
        return new MMapInputStream(filename,nbCharacters);
    }

    /**
     * Construct the Memory Map output stream
     *
     * @param filename The path of the file
     * @return the OutputStream of the file
     */
    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new MMapOutputStream(filename,nbCharacters);
    }
}
