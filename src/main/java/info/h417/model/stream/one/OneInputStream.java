package info.h417.model.stream.one;

import info.h417.model.stream.BaseInputStream;

import java.io.FileReader;
import java.io.IOException;

public class OneInputStream extends BaseInputStream {

    FileReader fileReader;
    private boolean endOfStream;

    /**
     * Constructor of an inputStream that reads one character at time
     *
     * @param filename The path of the file
     */
    public OneInputStream(String filename) {
        super(filename);
        this.endOfStream = false;
    }

    /**
     * Open an existing file for reading with the MappedByteBuffer.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void open() throws IOException {
        //super.open();
        fileReader = new FileReader(filename);
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        //super.close();
        fileReader.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        // @TODO
        endOfStream = false;
    }


    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return endOfStream;
    }


    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    @Override
    public String readln() throws IOException {

        StringBuilder line = new StringBuilder();

        int character =  fileReader.read();
        while(character != '\n' && character != -1){
            line.append((char) character);
            character = fileReader.read();
        }
        endOfStream = (character == -1);
        return line.toString();
    }
}
