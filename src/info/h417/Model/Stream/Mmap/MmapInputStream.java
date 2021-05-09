package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class MmapInputStream extends BaseInputStream {
    private int nbCharacters;
    private MappedByteBuffer buffer;
    private CharBuffer charBuffer = null;
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
        //super.open();
        if(fc == null){
            fc = new RandomAccessFile(filename, "r").getChannel();
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
        while(!end_of_stream() && loop){
            long n = (fc.position() + nbCharacters <= fc.size()) ? this.nbCharacters : fc.size() - fc.position();
            buffer = fc.map(FileChannel.MapMode.READ_ONLY, fc.position(), n);
            charBuffer = Charset.forName("UTF-8").decode(buffer);
            long newPosition = fc.position() ;
            for (int i = 0; i < charBuffer.limit(); i++) {
                char character = charBuffer.get();
                if(character  == '\n' ||  character == '\r') {
                    loop = false;
                    newPosition += 1;
                }
                if(loop){
                    text += character;
                    newPosition += 1;
                }
            }
            seek(newPosition);
        }
        return text;
    }

    @Override
    public void close() throws IOException {
        fc.close();
    }
}
