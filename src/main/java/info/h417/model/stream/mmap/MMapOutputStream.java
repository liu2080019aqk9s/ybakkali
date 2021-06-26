package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseOutputStream;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MMapOutputStream extends BaseOutputStream {
    private final int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fc;

    /**
     * Constructor of an outputStream that write by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename The path of the file
     * @param nbCharacters The size of the buffer
     */
    public MMapOutputStream(String filename, int nbCharacters) {
        super(filename);
        this.nbCharacters = nbCharacters;
    }

    /**
     * Create a new file.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void create() throws IOException {
        super.create();
        fc = FileChannel.open(Paths.get(filename), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE, StandardOpenOption.READ);
        getNextElement();
    }

    /**
     * Close the stream.
     *
     * @throws IOException If some I/O error occurs
     */
    @Override
    public void close() throws IOException {
        super.close();
        fc.truncate(fc.position() - (nbCharacters - buffer.position()));
        fc.close();
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
            if (!buffer.hasRemaining()) {
                getNextElement();
            }

            if (i < lineBytes.length) {
                buffer.put(lineBytes[i]);
            } else {
                buffer.put((byte) '\n');
            }
        }
    }

    /**
     * Map the next B characters region in the memory to write in
     *
     * @throws IOException If some I/O error occurs
     */
    private void getNextElement() throws IOException {
        this.buffer = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), nbCharacters);
        fc.position(fc.position() + nbCharacters);
    }
}
