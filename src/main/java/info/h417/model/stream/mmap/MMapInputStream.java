package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MMapInputStream extends BaseInputStream {
    private final int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fc;

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
            fc = in.getChannel();
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
        fc.close();
    }

    /**
     * Move the file cursor to the new position and load the next B characters into the buffer.
     *
     * @param pos The new position in file
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void seek(long pos) throws IOException {
        fc.position(pos);
        if (this.buffer != null) {
            this.buffer.position(nbCharacters);
        }
    }

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return fc.position() >= fc.size() && !buffer.hasRemaining();
    }

    /**
     * Read the next line from the stream
     *
     * @return A line of the file text
     * @throws IOException If some I/O error occurs
     */
    @Override
    public String readln() throws IOException {

        if (this.buffer == null) {
            getNextElement();
        }

        boolean loop = true;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while(!end_of_stream() && loop){

            if (!buffer.hasRemaining()) {
                getNextElement();
            }

            byte b = buffer.get();
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

        long n = (fc.position() + nbCharacters < fc.size()) ? nbCharacters : fc.size() - fc.position();
        this.buffer = fc.map(FileChannel.MapMode.READ_ONLY, fc.position(), n);
        fc.position(fc.position() + n);
    }
}
