package info.h417.Model.Stream.Buffered;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

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
