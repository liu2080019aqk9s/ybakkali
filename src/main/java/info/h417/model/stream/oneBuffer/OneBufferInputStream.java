package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class OneBufferInputStream extends BaseInputStream {
    private final byte[] buffer;
    private int cursorPosition;
    private int storedBytes;

    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = new byte[sizeBuffer];
        this.cursorPosition = sizeBuffer;
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
        getNextElement();
    }

    /**
     * Get the end of stream state.
     *
     * @return True if end of stream has been reached otherwise false
     * @throws IOException If some I/O error occurs
     */
    @Override
    public boolean end_of_stream() throws IOException {
        return super.end_of_stream() && storedBytes == 0;
    }

    @Override
    public String readln() throws IOException {


        ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean loop = true;

        while (!end_of_stream() && loop) {

            if (cursorPosition == buffer.length) {
                getNextElement();
            }
            int b = this.buffer[cursorPosition];
            if (b == '\n' || b == '\r') {
                loop = false;
            } else {
                output.write(b);
            }
            cursorPosition++;
            storedBytes--;
        }
        output.close();
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray())).toString();
    }

    /**
     * Load the next B characters into the buffer
     *
     * @throws IOException If some I/O error occurs
     */
    private void getNextElement() throws IOException {
        storedBytes = in.read(this.buffer);
        this.cursorPosition = 0;
    }
}
