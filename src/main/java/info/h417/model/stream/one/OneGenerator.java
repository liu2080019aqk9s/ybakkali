package info.h417.model.stream.one;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

public class OneGenerator extends Generator{

    @Override
    public BaseInputStream getInputStream(String filename) {
        return new OneInputStream(filename);
    }

    @Override
    public BaseOutputStream getOutputStream(String filename) {
        return new OneOutputStream(filename);
    }
}
