package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;


public class MmapInputStream extends BaseInputStream {
    private final int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fc;
    private RandomAccessFile r;

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
        //super.open();
        if(fc == null){
            r = new RandomAccessFile(filename, "r");
            fc = r.getChannel();
        }
    }

    @Override
    public void seek(long pos) throws IOException {
        fc.position(pos);
    }

    @Override
    public boolean end_of_stream() throws IOException {
        return fc.position() >= fc.size();
    }

    @Override
    public String readln() throws IOException {
        String text = "";
        boolean loop = true;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while(!end_of_stream() && loop){
            long n = (fc.position() + nbCharacters <= fc.size()) ? this.nbCharacters : fc.size() - fc.position();
            buffer = fc.map(FileChannel.MapMode.READ_ONLY, fc.position(), n);
            long newPosition = fc.position() ;
            for (int i = 0; i < buffer.limit(); i++) {
                byte character = buffer.get();
                if(character  == '\n' ||  character == '\r') {
                    loop = false;
                    newPosition += 1;
                }
                if(loop){
                    output.write(character);
                    newPosition += 1;
                }
            }
            seek(newPosition);
        }
        text += StandardCharsets.UTF_8.decode(ByteBuffer.wrap(output.toByteArray() )).toString();
        output.close();

        return text;
    }

    @Override
    public void close() throws IOException {
        r.close();
        fc.close();
    }
}
