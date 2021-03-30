package info.h417.Model.Stream.Buffered;

import info.h417.Model.Stream.BaseOutputStream;

import java.io.*;

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

    @Override
    public void create() throws IOException {
        super.create();
        if(bufferedWriter == null){
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
        }
    }

    @Override
    public void writeln(String text) throws IOException {
        bufferedWriter.write(text);
        bufferedWriter.newLine();

        bufferedWriter.flush(); // Marche sans si on close le fichier
    }

    @Override
    public void close() throws IOException {
        bufferedWriter.close();
    }
}
