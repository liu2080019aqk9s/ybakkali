package info.h417.Model.Stream;

import info.h417.Model.Stream.Buffered.BufferedGenerator;
import info.h417.Model.Stream.Mmap.MmapGenerator;
import info.h417.Model.Stream.One.OneGenerator;
import info.h417.Model.Stream.OneBuffer.OneBufferGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {


    @Test
    public void Buffered() throws IOException {
        Test(new BufferedGenerator());
    }

    @Test
    public void One() throws IOException {
        Test(new OneGenerator());
    }

    @Test
    public void OneBuffer() throws IOException {
        int sizeBuffer = 5;
        Test(new OneBufferGenerator(sizeBuffer));
    }

    @Test
    public void Mmap() throws IOException {
        int nbCharacter = 5;
        Test(new MmapGenerator(nbCharacter));
    }

    public void Test(Generator generator) throws IOException {
        String filename = "text2.txt";
        BaseOutputStream outputStream = generator.getOutputStream(filename);

        System.out.println("TEST CREATE");
        outputStream.create();
        assertTrue(new File(filename).exists());

        String text =  "Bonjour Le monde";
        System.out.println("TEST WRITE");
        outputStream.writeln(text);
        outputStream.close();

        BaseInputStream inputStream = generator.getInputStream(filename);
        inputStream.open();

        System.out.println("TEST END OF STREAM EQUAL FALSE");
        assertFalse(inputStream.end_of_stream());
        System.out.println("Test seek with 8 of decal");
        inputStream.seek(8);

        System.out.println("Test WRITE And READ");
        String exceptedText = "Le monde";
        text = inputStream.readln();
        assertEquals(exceptedText,text);

        System.out.println("TEST END OF STREAM EQUAL TRUE");
        assertTrue(inputStream.end_of_stream());
        inputStream.close();

        new File(filename).delete();
    }
}