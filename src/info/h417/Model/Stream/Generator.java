package info.h417.Model.Stream;


public abstract class Generator {
    public abstract BaseInputStream getInputStream(String filename);
    public abstract BaseOutputStream getOutputStream(String filename);
}
