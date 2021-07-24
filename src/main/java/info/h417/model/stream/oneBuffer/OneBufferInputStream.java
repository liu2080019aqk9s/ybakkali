package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;

import java.io.FileReader;
import java.io.IOException;

public class OneBufferInputStream extends BaseInputStream {

    private FileReader fileReader;
    char[] buffer;
    private int cursorPosition;
    private boolean seek;
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
        this.fileReader = new FileReader(filename);
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.fileReader.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        this.fileReader.close();
        this.fileReader = new FileReader(filename);
        this.fileReader.skip(pos);
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
        return !this.fileReader.ready() && cursorPosition == limit;
    }

    @Override
    public String readln() throws IOException {

        if (end_of_stream()) {
            return null;
        } else {

            StringBuilder line = new StringBuilder();
            boolean loop = true;

            while (!end_of_stream() && loop) {

                if (this.cursorPosition == this.buffer.length || this.seek) {
                    getNextElement();
                }
                int b = this.buffer[this.cursorPosition];
                if (b == '\n') {
                    loop = false;
                } else {
                    line.append((char) b);
                }
                this.cursorPosition++;
            }
            return line.toString();
        }
    }

    /**
     * Load the next B characters into the buffer
     *
     * @throws IOException If some I/O error occurs
     */
    private void getNextElement() throws IOException {

        this.limit = Math.min(fileReader.read(this.buffer), buffer.length);
        this.cursorPosition = 0;
        this.seek = false;
    }
}
