package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class OneBufferOutputStream extends BaseOutputStream {

    private final byte[] buffer;

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new byte[sizeBuffer];
    }

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void writeln(String line) throws IOException {
        int i = 0;
        //ByteBuffer bytesText = StandardCharsets.UTF_8.encode(text);
        for(byte character :  line.getBytes(StandardCharsets.UTF_8)){
            buffer[i] =   character;

            if(i == buffer.length -1){
                out.write(buffer);
            }
            i = (i + 1)%buffer.length;
        }

        buffer[i] = '\n';
        out.write(buffer,0,i+1);
    }

}
