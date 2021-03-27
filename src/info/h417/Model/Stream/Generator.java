package info.h417.Model.Stream;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;

public abstract class Generator {
    public abstract BaseInputStream getInputStream(String filename);
    public abstract BaseOutputStream getOutputStream(String filename);
}
