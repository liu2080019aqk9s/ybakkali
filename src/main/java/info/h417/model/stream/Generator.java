package info.h417.model.stream;


/**
 * A generator allowing to create an input and output streams.
 */
public abstract class Generator {

    /**
     * Create a new inputStream
     *
     * @param filename The path of the file
     * @return An inputStream
     */
    public abstract BaseInputStream getInputStream(String filename);

    /**
     * Create a new outputStream
     *
     * @param filename The path of the file
     * @return An outputStream
     */
    public abstract BaseOutputStream getOutputStream(String filename);
}
