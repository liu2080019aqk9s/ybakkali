package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseInputStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OneBufferInputStream extends BaseInputStream {
    private char[] buffer;
    private BufferedReader bufferedReader;

    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new char[sizeBuffer];
    }

    @Override
    public String readln() throws IOException {
        long current = in.getChannel().position();
        long i = 0;
        String text = "";
        while(i != -1 && !end_of_stream()){
            bufferedReader.read(buffer);
            for(int j = 0; j < buffer.length; j++){
                i++;
                if(buffer[j] == '\n' || buffer[j] == '\r'){
                    seek(current + i +1 );
                    i = -1;
                    break;
                }
                else{
                    text +=  buffer[j];
                }
            }

        }

        return text;
    }

    @Override
    public void open() throws IOException {
        super.open();
        if(bufferedReader == null){
            bufferedReader = new BufferedReader(new InputStreamReader(in));
        }
    }

    @Override
    public void seek(long pos) throws IOException {
        super.seek(pos);
        bufferedReader = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }


    @Override
    public boolean end_of_stream() throws IOException {
        return !bufferedReader.ready();
    }
}
