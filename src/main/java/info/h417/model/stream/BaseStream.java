package info.h417.model.stream;


import java.io.IOException;

public abstract class BaseStream {
    protected final String filename;

    public BaseStream(String filename) {
        this.filename = filename;
    }

    public abstract void close() throws IOException;
}
