package info.h417.model.stream.one;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class OneInputStream extends BaseInputStream {

    /**
     * Constructor of an inputStream that reads one character at time
     *
     * @param filename The path of the file
     */
    public OneInputStream(String filename) {
        super(filename);
    }

    @Override
    public String readln() throws IOException {
        String temp = "";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        int character =  in.read();
        while( character != '\n' && !end_of_stream()){
            output.write(character);
            character = in.read();
        }
        temp += StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray() )).toString();
        output.close();

        return temp;
    }



}
