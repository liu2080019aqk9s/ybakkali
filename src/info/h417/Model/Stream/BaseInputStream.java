package info.h417.Model.Stream;

public abstract class BaseInputStream extends BaseStream {

    /**
     * Basic Constructor of an inputStream
     *
     * @param filename The path of the file
     */
    public BaseInputStream(String filename) {
        super(filename);
    }

    /**
     * read the next line from the stream
     * @return the line
     */
    public abstract String readln();

    /**
     * open an existing file for reading
     */
    public abstract void open();

    /**
     * close the stream
     */
    public abstract void close();

    /**
     * move the file cursor to pos so that a subsequent readln reads from position pos to the next end of line
     * @param pos the position in file
     */
    public abstract void seek(long pos);

    /**
     * a boolean operation that returns true if the end of stream has been reached.
     */
    public abstract boolean end_of_stream();
}
