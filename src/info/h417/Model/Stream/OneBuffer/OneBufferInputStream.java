package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class OneBufferInputStream extends BaseInputStream {
    private byte[] buffer;

    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new byte[sizeBuffer];
    }

    @Override
    public String readln() throws IOException {
        long current = in.getChannel().position();
        long i = 0;
        int size = 0;
        String text = "";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while(i != -1 && !end_of_stream()){
            in.read(buffer);
            size = 0;
            for(int j = 0; j < buffer.length; j++){
                if(buffer[j] == '\n' ) {
                    seek(current + i + 1);
                    i = -1;
                    break;
                }
                size++;
                i++;
            }
            output.write(buffer,0,size);
        }
        text += StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray() )).toString();
        output.close();

        return text;
    }
}
