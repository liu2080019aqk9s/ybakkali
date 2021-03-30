package info.h417.Model.Stream;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;

import java.io.FileNotFoundException;

public abstract class Generator {
    public abstract BaseInputStream getInputStream(String filename) throws FileNotFoundException;
    public abstract BaseOutputStream getOutputStream(String filename);
}
