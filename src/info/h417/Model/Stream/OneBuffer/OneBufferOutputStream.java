package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseOutputStream;

import java.io.IOException;

public class OneBufferOutputStream extends BaseOutputStream {

    private byte[] buffer;

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new byte[sizeBuffer];
    }

    @Override
    public void writeln(String text) throws IOException {
        int i = 0;
        for(byte character : text.getBytes()){
            buffer[i] = character;
            if(i == buffer.length -1){
                out.write(buffer);
            }
            i = (i + 1)%buffer.length;
        }
        buffer[i] = '\n';
        out.write(buffer,0,i+1);
    }

}
