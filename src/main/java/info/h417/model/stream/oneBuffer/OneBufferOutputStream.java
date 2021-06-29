package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class OneBufferOutputStream extends BaseOutputStream {

    private final byte[] buffer;
    private int cursorPosition;

    /**
     * Basic Constructor of an outputStream that write sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferOutputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new byte[sizeBuffer];
        this.cursorPosition = 0;
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        if (cursorPosition > 0) {
            writeIntoFile();
        }
        super.close();
    }

    /**
     * Write a string to the stream and terminate this stream with the newline character.
     *
     * @param line The line to write
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void writeln(String line) throws IOException {

        byte[] lineBytes = line.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i <= lineBytes.length; i++) {
            if (cursorPosition == buffer.length) {
                writeIntoFile();
            }

            byte b = (i < lineBytes.length) ? lineBytes[i] : (byte)'\n';
            this.buffer[cursorPosition] = b;
            cursorPosition++;
        }
    }


    /**
     * Write the B characters in the buffer into the file
     *
     * @throws IOException If some I/O error occurs
     */
    private void writeIntoFile() throws IOException {
        out.write(buffer, 0, cursorPosition);
        this.cursorPosition = 0;
    }
}
