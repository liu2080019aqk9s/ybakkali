package info.h417.model.stream.mmap;

import info.h417.model.stream.BaseOutputStream;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MmapOutputStream extends BaseOutputStream {
    private final int nbCharacters;
    private FileChannel fc;
    private RandomAccessFile rw;

    /**
     * Constructor of an outputStream that write by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename The path of the file
     * @param nbCharacters
     */
    public MmapOutputStream(String filename,int nbCharacters) {
        super(filename);
        this.nbCharacters = nbCharacters;
    }


    @Override
    public void create() throws IOException {
        super.create();
        super.close();
        rw = new RandomAccessFile(filename, "rw");
        fc = rw.getChannel();
    }

    @Override
    public void writeln(String text) throws IOException {
        int i = 0;
        boolean loop = true;
        byte character;
        long size = nbCharacters;
        byte[] bText = text.getBytes(StandardCharsets.UTF_8);
        while(loop){

            if(bText.length - i < nbCharacters +1){
                size = bText.length - i + 1;
                loop = false;
            }

            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), size);
            long newPosition = fc.position();
            for( int j = 0; j < size; j++){
                if(i < bText.length){
                    character = bText[i];
                }
                else{
                    character = '\n';
                }
                buffer.put( character);
                i++;
                newPosition++;
            }

            fc.position(newPosition);
        }
    }

    @Override
    public void close() throws IOException {
        rw.close();
        fc.close();
    }
}
