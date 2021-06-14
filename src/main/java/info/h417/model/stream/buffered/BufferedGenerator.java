package info.h417.model.stream.buffered;

import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

public class BufferedGenerator extends Generator {

    @Override
    public BaseInputStream getInputStream(String filename) {
        return new BufferedInputStream(filename);
    }

    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new BufferedOutputStream(filename);
    }
}
