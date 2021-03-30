package info.h417.Model.Stream.One;

import info.h417.Model.Stream.BaseInputStream;

import java.io.*;

public class OneInputStream extends BaseInputStream {

    /**
     * Constructor of an inputStream that reads one character at time
     *
     * @param filename The path of the file
     */
    public OneInputStream(String filename) {
        super(filename);
    }

    @Override
    public String readln() throws IOException {
        String temp = "";
        char character =  (char) in.read();
        while( character != '\n' && !end_of_stream()){
            temp += character;
            character = (char) in.read();
        }
        return temp;
    }



}
