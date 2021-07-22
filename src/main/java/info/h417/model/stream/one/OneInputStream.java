package info.h417.model.stream.one;

import info.h417.model.stream.BaseInputStream;

import java.io.FileReader;
import java.io.IOException;

public class OneInputStream extends BaseInputStream {

    FileReader fileReader;

    /**
     * Constructor of an inputStream that reads one character at time
     *
     * @param filename The path of the file
     */
    public OneInputStream(String filename) {
        super(filename);
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
    }


    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return !this.fileReader.ready();
    }


    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    @Override
    public String readln() throws IOException {

        if (end_of_stream()) {
            return null;
        } else {

            StringBuilder line = new StringBuilder();

            int character = this.fileReader.read();
            while (character != -1 && character != '\n') {
                line.append((char) character);
                character = this.fileReader.read();
            }
            return line.toString();
        }
    }
}
