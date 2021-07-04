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

    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    @Override
    public String readln() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        int character =  in.read();
        while(character != '\n' && character != '\r' && character != -1){
            output.write(character);
            character = in.read();
        }

        output.close();

        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray())).toString();
    }
}
