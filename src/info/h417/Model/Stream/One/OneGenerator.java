package info.h417.Model.Stream.One;
import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

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
