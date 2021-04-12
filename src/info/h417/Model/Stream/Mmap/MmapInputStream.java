package info.h417.Model.Stream.Mmap;

import info.h417.Model.Stream.BaseInputStream;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class MmapInputStream extends BaseInputStream {
    private int nbCharacters;
    private long pos = 0;
    private FileChannel fileChannel;
    private MappedByteBuffer buffer;

    /**
     * Constructor of an inputStream that reads by mapping and unmapping
     * nbCharacters characters of the file into internal memory through memory mapping.
     *
     * @param filename The path of the file
     * @param nbCharacters
     */
    public MmapInputStream(String filename,int nbCharacters) {
        super(filename);
        this.nbCharacters = nbCharacters;
        try {
            fileChannel = FileChannel.open(Paths.get(filename), StandardOpenOption.READ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seek(long pos) {
        this.pos = pos;
    }

    @Override
    public String readln() throws IOException {
        if (this.pos <= fileChannel.size()) {
            StringBuilder line = new StringBuilder();
            long n = (this.pos + nbCharacters <= fileChannel.size()) ? this.nbCharacters : fileChannel.size() - this.pos;

            try {
                buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, this.pos, n);
                System.out.println("-----------");


            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < buffer.limit(); i++) {
                line.append((char) (buffer.get() & 0xFF));
            }

            pos += nbCharacters;
            return line.toString();
        }
        return "EOS";
    }

}
