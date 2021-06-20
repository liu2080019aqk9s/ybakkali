package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MmapInputStream extends BaseInputStream {
    private int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fc;

    /**
     * Constructor of an inputStream that reads by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename     The path of the file
     * @param nbCharacters
     */
    public MmapInputStream(String filename, int nbCharacters) {
        super(filename);
        this.nbCharacters = nbCharacters;
    }

    @Override
    public void open() throws IOException {
        super.open();
        if(in != null){
            fc = in.getChannel();
            getNextElement();
        }
    }

    @Override
    public void seek(long pos) throws IOException {
        fc.position(pos);
        getNextElement();
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return fc.position() >= fc.size() && !buffer.hasRemaining();
    }

    @Override
    public String readln() throws IOException {

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

    @Override
    public void close() throws IOException {
        super.close();
        fc.close();
    }

    private void getNextElement() throws IOException {

        long n = (fc.position() + nbCharacters < fc.size()) ? nbCharacters : fc.size() - fc.position();
        this.buffer = fc.map(FileChannel.MapMode.READ_ONLY, fc.position(), n);
        fc.position(fc.position() + n);
    }
}
