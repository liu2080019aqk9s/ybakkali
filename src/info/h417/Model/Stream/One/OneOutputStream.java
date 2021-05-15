package info.h417.Model.Stream.One;

import info.h417.Model.Stream.BaseOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OneOutputStream extends BaseOutputStream {


    /**
     * Constructor of an outputStream that write one character at time
     *
     * @param filename The path of the file
     */
    public OneOutputStream(String filename) {
        super(filename);
    }


    @Override
    public void writeln(String text) throws IOException {
        for(byte character : text.getBytes(StandardCharsets.UTF_8)){
            out.write(character);
        }
        out.write((byte)'\n');
    }
}
