package info.h417.Model.Stream.Buffered;

import info.h417.Model.Stream.BaseInputStream;

import java.io.*;

public class BufferedInputStream extends BaseInputStream {
    private BufferedReader bufferedReader;

    /**
     * Constructor of an inputStream that use a buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedInputStream(String filename) {
        super(filename);

    }

    @Override
    public String readln() throws IOException {
        return bufferedReader.readLine();
    }


    @Override
    public void open() throws FileNotFoundException {
        super.open();
        if(bufferedReader == null){
            bufferedReader = new BufferedReader(new InputStreamReader( in));
        }
    }


    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public void seek(long pos) throws IOException {
        super.seek(pos);
        bufferedReader = new BufferedReader(new InputStreamReader( in ));
    }
}
