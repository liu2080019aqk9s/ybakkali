package info.h417;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Buffered.BufferedGenerator;
import info.h417.Model.Stream.Generator;
import info.h417.Model.Stream.Mmap.MmapGenerator;
import info.h417.Model.Stream.One.OneGenerator;
import info.h417.Model.Stream.OneBuffer.OneBufferGenerator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Generator generator = new OneBufferGenerator(9);
//
//        BaseInputStream baseInputStream = generator.getInputStream("File1");
//        baseInputStream.open();
//        baseInputStream.seek(8);
//        System.out.println(baseInputStream.readln());
//        System.out.println(baseInputStream.readln());
//        baseInputStream.close();
//
//        BaseOutputStream outputStream = generator.getOutputStream("Test2");
//        outputStream.create();
//        outputStream.writeln("Bonjour tout vas bien ");
//        outputStream.writeln("Bonjour tout vas bien ");
//        outputStream.writeln("Bonjour tout vas bien ");
//        outputStream.writeln("Bonjourn ");
//        outputStream.close();

        Generator generator = new MmapGenerator(2);

        BaseInputStream baseInputStream = generator.getInputStream("src/info/h417/file.txt");
        baseInputStream.open();
        //baseInputStream.seek(0);
        while (!baseInputStream.readln().equals("EOS")) {
            System.out.println(baseInputStream.readln());
        }
        baseInputStream.close();
    }
}
