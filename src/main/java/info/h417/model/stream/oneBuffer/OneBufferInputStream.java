package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class OneBufferInputStream extends BaseInputStream {
    private final ByteBuffer buffer;
    private FileChannel fc;

    /**
     * Basic Constructor of an inputStream that reads sizeBuffer character in a buffer
     *
     * @param filename The path of the file
     * @param sizeBuffer The size of the buffer
     */
    public OneBufferInputStream(String filename,int sizeBuffer) {
        super(filename);
        this.buffer = ByteBuffer.allocate(sizeBuffer);
    }

    @Override
    public void open() throws IOException {
        super.open();
        if(in != null){
            this.fc = in.getChannel();
        }
        getNextElement();
    }

    @Override
    public void seek(long pos) throws IOException {
        this.fc.position(pos);
        getNextElement();
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return fc.position() >= fc.size() && !buffer.hasRemaining();
    }

    @Override
    public String readln() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean loop = true;

        while (!end_of_stream() && loop) {

            if (!buffer.hasRemaining()) {
                getNextElement();
            }
            byte b = this.buffer.get();
            if (b == '\n') {
                loop = false;
            } else {
                output.write(b);
            }
        }
        output.close();
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray())).toString();
    }

    private void getNextElement() throws IOException {
        this.buffer.clear();
        fc.read(this.buffer);
        this.buffer.flip();
    }
}
