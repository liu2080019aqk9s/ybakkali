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
    public void Mmap2() throws IOException {
        Generator generator = new MmapGenerator(2);
        testFile(generator);
    }



    @Test
    public void Buffered() throws IOException {
        BufferedGenerator generator = new BufferedGenerator();
        Test(generator);
        Test2(generator);
        testFile(generator);
    }

    @Test
    public void One() throws IOException {
        OneGenerator generator = new OneGenerator();
        Test(generator);
        Test2(generator);
        testFile(generator);
    }

    @Test
    public void OneBuffer() throws IOException {
        int sizeBuffer = 5;
        OneBufferGenerator generator = new OneBufferGenerator(sizeBuffer);
        Test(generator);
        Test2(generator);
        testFile(generator);
    }

    @Test
    public void Mmap() throws IOException {
        int nbCharacter = 5;
        MmapGenerator generator = new MmapGenerator(nbCharacter);
        Test(generator);
        Test2(generator);
        testFile(generator);
    }

    public void Test(Generator generator) throws IOException {
        String filename = "Files/text2.txt";
        //BaseOutputStream outputStream = generator.getOutputStream(filename);
        BaseOutputStream outputStream = new BufferedGenerator().getOutputStream(filename);

        System.out.println("TEST CREATE");
        outputStream.create();
        assertTrue(new File(filename).exists());

        String text =  "Bonjour Le monde";
        System.out.println("TEST WRITE");

        String text2 =  "On se trouve ó la seconde ligne"; //Test fonction
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
    public void Test2(Generator generator) throws IOException {
        String filename = "Files/text3.txt";
        BaseOutputStream outputStream = generator.getOutputStream(filename);
        System.out.println("TEST CREATE");
        outputStream.create();
        assertTrue(new File(filename).exists());

        String text =  "Bonjour Le monde";
        System.out.println("TEST WRITE");

        String text2 =  "On se trouve ó la seconde ligne"; //Test fonction
        String text3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String text4 = "aaaaaaaeeeeeeeeeeaaaaaaaaa";
        outputStream.writeln(text);
        outputStream.writeln(text2);
        outputStream.writeln(text3);
        outputStream.writeln(text4);
        outputStream.writeln(text3);
        outputStream.writeln(text4);
        outputStream.writeln(text3);
        outputStream.writeln(text4);
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

        text = inputStream.readln();
        assertEquals(text3,text);

        text = inputStream.readln();
        assertEquals(text4,text);

        text = inputStream.readln();
        assertEquals(text3,text);
        System.out.println("TEST END OF STREAM EQUAL False");
        assertFalse(inputStream.end_of_stream());
        inputStream.close();

        //new File(filename).delete();
    }

    private void testFile(Generator generator) throws IOException {
        assertNotNull(generator);
        BaseInputStream baseInputStream = generator.getInputStream("Files/imdb/aka_title.csv");
        assertNotNull(baseInputStream);

        baseInputStream.open();

        assertEquals("13117,833595,Malhação - Adolescência: A Passagem da Infância Para a Vida Adulta,,2,2011,M4342,,,,(Brazil) (nineteenth season title),320b4fa8ae74411e55cde509d9883e9c", baseInputStream.readln());
        assertEquals("6017,393076,Dirty Pair,,2,1985,D6316,,,,(USA),f77196370d0815fd72eb57ea2896c0e3", baseInputStream.readln());
        baseInputStream.close();
    }
}