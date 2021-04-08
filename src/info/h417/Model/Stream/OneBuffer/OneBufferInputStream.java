package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseInputStream;

import java.io.IOException;

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
        String text = "";
        while(i != -1){
            in.read(buffer);
            for(int j = 0; j < buffer.length; j++){
                i++;
                if(buffer[j] == '\n'){
                    seek(current + i);
                    i = -1;
                    break;
                }
                else{
                    text += (char) (buffer[j ] & 0xff);
                }
            }
        }

        return text;
    }
}
