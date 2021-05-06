package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class OneBufferOutputStream extends BaseOutputStream {

    private char[] buffer;
    private OutputStreamWriter fw;

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new char[sizeBuffer];
    }

    @Override
    public void create() throws IOException {
        super.create();
        fw = new OutputStreamWriter(out);
    }

    @Override
    public void writeln(String text) throws IOException {
        int i = 0;
        for(char character : text.toCharArray()){
            buffer[i] = character ;

            if(i == buffer.length -1){
                fw.write(buffer);
            }
            i = (i + 1)%buffer.length;
        }
        buffer[i] = '\n';
        fw.write(buffer,0,i+1);
    }

    @Override
    public void close() throws IOException {
        fw.close();
    }
}
