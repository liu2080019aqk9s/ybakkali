package info.h417.model.stream.oneBuffer;

import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

public class OneBufferGenerator extends Generator {
    private final int sizeBuffer;


    public OneBufferGenerator(int sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
    }

    @Override
    public BaseInputStream getInputStream(String filename) {
        return new OneBufferInputStream(filename,sizeBuffer);
    }

    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new OneBufferOutputStream(filename,sizeBuffer);
    }
}
