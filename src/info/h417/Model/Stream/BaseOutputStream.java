package info.h417.Model.Stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class BaseOutputStream extends BaseStream{

    protected FileOutputStream out;

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
    public void create() throws IOException {
        if(out == null){
            out = new FileOutputStream(filename);
        }
    }

    /**
     * close the stream
     */
    public void close() throws IOException {
        out.close();
    }

    /**
     *  write  a  string  to  the  stream  and  terminate  this  stream  with  the  newline character
     * @param text
     */
    public abstract void writeln(String text) throws IOException;

}
