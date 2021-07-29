package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MMapInputStream extends BaseInputStream {

    private final int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fileChannel;
    private boolean seek;

    /**
     * Constructor of an inputStream that reads by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename     The path of the file
     * @param nbCharacters The size of the buffer
     */
    public MMapInputStream(String filename, int nbCharacters) {
        super(filename);
        this.nbCharacters = nbCharacters;
        this.seek = false;
    }

    /**
     * Open an existing file for reading with the MappedByteBuffer.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void open() throws IOException {
        this.fileChannel = FileChannel.open(Paths.get(filename), StandardOpenOption.READ);
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.fileChannel.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        this.fileChannel.position(pos);
        this.seek = true;
    }

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return this.fileChannel.size() == this.fileChannel.position() && !this.buffer.hasRemaining();
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
        }

        if (this.buffer == null) {
            getNextElement();
        }

        boolean loop = true;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while(!end_of_stream() && loop){

            if (!this.buffer.hasRemaining() || this.seek) {
                getNextElement();
            }

            byte b = this.buffer.get();
            if (b == '\n') {
                loop = false;
            } else {
                output.write(b);
            }
            output.close();
        }
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray())).toString();
    }

    /**
     * Load the next B characters into the buffer
     *
     * @throws IOException If some I/O error occurs
     */
    private void getNextElement() throws IOException {

        long n = (fileChannel.position() + nbCharacters < fileChannel.size()) ? nbCharacters : fileChannel.size() - fileChannel.position();
        this.buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, fileChannel.position(), n);
        this.fileChannel.position(fileChannel.position() + n);
        this.seek = false;
    }
}
