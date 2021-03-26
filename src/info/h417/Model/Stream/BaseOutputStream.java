package info.h417.Model.Stream;

public abstract class BaseOutputStream extends BaseStream{

    /**
     * Basic Constructor of an outputStream
     *
     * @param filename The path of the file
     */
    public BaseOutputStream(String filename) {
        super(filename);
    }

    /**
     * create a new file
     */
    public abstract void create();

    /**
     * close the stream
     */
    public abstract void close();

    /**
     *  write  a  string  to  the  stream  and  terminate  this  stream  with  the  newline character
     * @param text
     */
    public abstract void writeln(String text);

}
