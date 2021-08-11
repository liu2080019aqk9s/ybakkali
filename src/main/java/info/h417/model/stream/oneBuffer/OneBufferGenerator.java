package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

/**
 * This class instance an object from OnebufferStream
 */
public class OneBufferGenerator extends Generator {
    private final int sizeBuffer;


    public OneBufferGenerator(int sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
    }

    /**
     * Construct the Buffer with size sizebuffer input stream of file
     *
     * @param filename The path of the file
     * @return the InputStream of the file
     */
    @Override
    public BaseInputStream getInputStream(String filename) {
        return new OneBufferInputStream(filename,sizeBuffer);
    }

    /**
     * Construct the Buffer with size sizebuffer output stream of file
     *
     * @param filename The path of the file
     * @return the OutputStream of the file
     */
    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new OneBufferOutputStream(filename,sizeBuffer);
    }
}
