package info.h417.Model.Stream.OneBuffer;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

public class OneBufferGenerator extends Generator {
    private int sizeBuffer;


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
