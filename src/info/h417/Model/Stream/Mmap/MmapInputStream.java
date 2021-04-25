package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class MmapInputStream extends BaseInputStream {
    private int nbCharacters;
    private MappedByteBuffer buffer;

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
    }

    @Override
    public String readln() throws IOException {
        if (! end_of_stream()) {
            String text = "";
            boolean loop = true;
            while(!end_of_stream() && loop){
                long n = (in.getChannel().position() + nbCharacters <= in.getChannel().size()) ? this.nbCharacters : in.getChannel().size() - in.getChannel().position();
                buffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, in.getChannel().position(), n);
                long newPosition = in.getChannel().position();
                for (int i = 0; i < buffer.limit(); i++) {
                    char character = (char) (buffer.get() & 0xFF);
                    if(character  == '\n' ||  character == '\r') {
                        loop = false;
                        newPosition += 1;
                        break;
                    }
                    else{
                        text += character;
                        newPosition += 1;
                    }
                }
                seek(newPosition);
            }
            return text;
        }
        return "EOS";
    }

}
