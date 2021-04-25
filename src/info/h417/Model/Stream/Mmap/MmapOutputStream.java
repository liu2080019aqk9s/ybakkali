package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseOutputStream;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MmapOutputStream extends BaseOutputStream {
    private int nbCharacters;
    private MappedByteBuffer buffer;
    private FileChannel fc;

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
        fc = new RandomAccessFile(filename, "rw").getChannel();
    }

    @Override
    public void writeln(String text) throws IOException {
        int i = 0;
        boolean loop = true;
        char character;
        long size = nbCharacters;
        while(loop){
            if(text.length() - i < nbCharacters +1){
                size = text.length() - i + 1;
                loop = false;
            }
            buffer = fc.map(FileChannel.MapMode.READ_WRITE, out.getChannel().position(),  size  );
            long newPosition = out.getChannel().position();
            for( int j = 0; j < size; j++){
                if(i < text.length()){
                    character = text.charAt(i);
                }
                else{
                    character = '\n';
                }
                buffer.put((byte) ( character) );
                i++;
                newPosition++;
            }
            out.getChannel().position(newPosition);
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        fc.close();
    }
}
