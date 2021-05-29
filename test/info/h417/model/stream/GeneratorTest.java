package info.h417.model.stream;

import info.h417.model.algo.RRMerge;
import info.h417.model.stream.buffered.BufferedGenerator;
import info.h417.model.stream.mmap.MmapGenerator;
import info.h417.model.stream.one.OneGenerator;
import info.h417.model.stream.oneBuffer.OneBufferGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    @Test
    public void Pair() throws IOException {
        int nbCharacter = 5;
        int sizeBuffer = 5;
        ArrayList<Generator> t =  new ArrayList<>();
        t.add(new MmapGenerator(nbCharacter));
        t.add(new OneBufferGenerator(sizeBuffer));
        t.add( new OneGenerator());
        t.add( new BufferedGenerator());

        for(int i = 0; i < t.size() ; i++){
            for(int j = 0; j < i ; j++){
                Test4(t.get(i),t.get(j) , i + "-" + j);
                Test4(t.get(j),t.get(i) , j + "-" + i);
            }
        }
    }

    @Test
    public void RRMerge() throws IOException {
        Generator generatorReader = new OneBufferGenerator(42);
        Generator generatorWriter = new OneBufferGenerator(42);
        RRMerge rrMerge = new RRMerge(generatorReader, generatorWriter);
        rrMerge.begin("Files/file1.txt", "Files/file2.txt", "Files/file3.txt", "Files/file4.txt", "Files/file5.txt");

        Generator generatorFile = new OneGenerator();
        BaseInputStream inputStream = generatorFile.getInputStream("RRMergeOutput.csv");
        inputStream.open();

        String outputFile = "aaaaaa\n" +
                            "bbbbbb\n" +
                            "cccccc\n" +
                            "dddddd\n" +
                            "eeeeee\n" +
                            "ffffff\n" +
                            "gggggg\n" +
                            "hhhhhh\n" +
                            "iiiiii\n" +
                            "jjjjjj\n" +
                            "kkkkkk\n" +
                            "llllll\n" +
                            "mmmmmm\n" +
                            "nnnnnn\n" +
                            "oooooo\n" +
                            "pppppp\n" +
                            "qqqqqq\n" +
                            "rrrrrr\n" +
                            "ssssss\n" +
                            "tttttt\n" +
                            "uuuuuu\n" +
                            "vvvvvv\n" +
                            "wwwwww\n" +
                            "xxxxxx\n" +
                            "yyyyyy\n" +
                            "zzzzzz";
        String[] outputFileLines = outputFile.split("\n");

        for (String line : outputFileLines) {
            assertEquals(line, inputStream.readln());
        }
        assertTrue(inputStream.end_of_stream());
    }


    public void Test(Generator generator) throws IOException {
        String filename = "Files/text2.txt";
        BaseOutputStream outputStream = generator.getOutputStream(filename);

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

    public void Test4(Generator generator,Generator writeGenerator,String name) throws IOException {
        String filename = "Files/"+ name + ".txt";
        BaseOutputStream outputStream = writeGenerator.getOutputStream(filename);
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

        new File(filename).delete();
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