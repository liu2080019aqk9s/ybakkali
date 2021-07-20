package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OneBufferInputStream extends BaseInputStream {
    InputStreamReader inputStreamReader;
    char[] buffer;
    private int cursorPosition;
    private int storedBytes;
    private boolean seek;
    private boolean endOfStream;
    private int limit;

    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new char[sizeBuffer];
        this.cursorPosition = sizeBuffer;
        this.limit = sizeBuffer;
        this.seek = false;
    }

    /**
     * Open an existing file for reading with the MappedByteBuffer.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void open() throws IOException {
        super.open();
        if(in != null){
            inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
        }
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        super.close();
        inputStreamReader.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        super.seek(pos);
        this.seek = true;
        this.limit = buffer.length;
    }

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return endOfStream && cursorPosition == limit;
    }

    @Override
    public String readln() throws IOException {


        StringBuilder line = new StringBuilder();
        boolean loop = true;

        while (!end_of_stream() && loop) {

            if (cursorPosition == buffer.length || seek) {
                getNextElement();
            }
            int b = this.buffer[cursorPosition];
            if (b == '\n') {
                loop = false;
            } else {
                line.append((char) b);
            }
            cursorPosition++;
        }
        return line.toString();
    }

    /**
     * Load the next B characters into the buffer
     *
     * @throws IOException If some I/O error occurs
     */
    private void getNextElement() throws IOException {
        storedBytes = inputStreamReader.read(this.buffer);
        this.endOfStream = (storedBytes == -1 || storedBytes < buffer.length);
        this.limit = Math.min(storedBytes, buffer.length);
        this.cursorPosition = 0;
        this.seek = false;
    }
}
