package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseOutputStream;

import java.io.FileWriter;
import java.io.IOException;


public class OneBufferOutputStream extends BaseOutputStream {

    private FileWriter fileWriter;
    private final char[] buffer;
    private int cursorPosition;

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new char[sizeBuffer];
        this.cursorPosition = 0;
    }

    @Override
    public void create() throws IOException {
        this.fileWriter = new FileWriter(filename);
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        if (this.cursorPosition > 0) {
            writeIntoFile();
        }
        this.fileWriter.close();
    }

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void writeln(String line) throws IOException {

        for (int i = 0; i <= line.length(); i++) {
            if (this.cursorPosition == this.buffer.length) {
                writeIntoFile();
            }

            char b = (i < line.length()) ? line.charAt(i) : '\n';
            this.buffer[cursorPosition] = b;
            this.cursorPosition++;
        }
    }


    /**
     * Write the B characters in the buffer into the file
     *
     * @throws IOException If some I/O error occurs
     */
    private void writeIntoFile() throws IOException {
        this.fileWriter.write(buffer, 0, cursorPosition);
        this.fileWriter.flush();
        this.cursorPosition = 0;
    }
}
