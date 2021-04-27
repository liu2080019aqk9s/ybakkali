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
    public void Mmap() throws IOException {
        int nbCharacter = 5;
        Test(new MmapGenerator(nbCharacter));
    }

    @Test
    public void Mmap2() throws IOException {
        Generator generator = new MmapGenerator(2);
        assertNotNull(generator);
        BaseInputStream baseInputStream = generator.getInputStream("Files/file.txt");
        assertNotNull(baseInputStream);

        baseInputStream.open();

        assertEquals("aaaaaa", baseInputStream.readln());
        assertEquals("bbbbbb", baseInputStream.readln());
        assertEquals("cccccc", baseInputStream.readln());
        assertEquals("dddddd", baseInputStream.readln());
        assertEquals("éééééé", baseInputStream.readln());

    }

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


    public void Test(Generator generator) throws IOException {
        String filename = "Files/text2.txt";
        BaseOutputStream outputStream = generator.getOutputStream(filename);
        //BaseOutputStream outputStream = new BufferedGenerator().getOutputStream(filename);
        System.out.println("TEST CREATE");
        outputStream.create();
        assertTrue(new File(filename).exists());

        String text =  "Bonjour Le monde";
        System.out.println("TEST WRITE");

        String text2 =  "On se trouve à la seconde ligne"; //Test fonction
        outputStream.writeln(text);
        outputStream.writeln(text2);
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

        text = inputStream.readln();

        assertEquals(text2,text);
        System.out.println("TEST END OF STREAM EQUAL TRUE");
        assertTrue(inputStream.end_of_stream());
        inputStream.close();

        //new File(filename).delete();
    }
}