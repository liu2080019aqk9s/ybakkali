package info.h417.model.stream;


public abstract class Generator {
    public abstract BaseInputStream getInputStream(String filename);
    public abstract BaseOutputStream getOutputStream(String filename);
}
