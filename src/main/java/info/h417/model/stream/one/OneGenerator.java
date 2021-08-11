package info.h417.model.stream.one;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

/**
 * This class instance an object from OneStream
 */
public class OneGenerator extends Generator{


    /**
     * Construct the One input stream of file
     *
     * @param filename The path of the file
     * @return the InputStream of the file
     */
    @Override
    public BaseInputStream getInputStream(String filename) {
        return new OneInputStream(filename);
    }

    /**
     * Construct the One output stream of file
     *
     * @param filename The path of the file
     * @return the OutputStream of the file
     */
    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new OneOutputStream(filename);
    }
}
