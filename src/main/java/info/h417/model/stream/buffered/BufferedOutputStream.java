package info.h417.model.stream.buffered;

import info.h417.model.stream.BaseOutputStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferedOutputStream extends BaseOutputStream {
    private BufferedWriter bufferedWriter;

    /**
     * Constructor of an outputStream that uses buffering mechanism
     *
     * @param filename The path of the file
     */
    public BufferedOutputStream(String filename) {
        super(filename);
    }

    /**
     * @throws IOException
     */
    @Override
    public void create() throws IOException {
        super.create();
        if(bufferedWriter == null){
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
        }
    }

    /**
     * @param text
     * @throws IOException
     */
    @Override
    public void writeln(String text) throws IOException {
        bufferedWriter.write(text + "\n");

        bufferedWriter.flush(); // Marche sans si on close le fichier
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        super.close();
        bufferedWriter.close();
    }
}
