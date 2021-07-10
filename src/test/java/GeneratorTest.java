import info.h417.model.algo.ExtSort;
import info.h417.model.algo.Length;
import info.h417.model.algo.RRMerge;
import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;
import info.h417.model.stream.buffered.BufferedGenerator;
import info.h417.model.stream.mmap.MMapGenerator;
import info.h417.model.stream.one.OneGenerator;
import info.h417.model.stream.one.OneInputStream;
import info.h417.model.stream.oneBuffer.OneBufferGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    String baseResourcesPath = "src/test/resources/";

    @Test
    public void length() throws IOException {

        Generator generator1 = new OneGenerator();
        Length length1 = new Length(generator1);
        assertEquals(6 * 5, length1.begin(baseResourcesPath + "file.txt"));

        Generator generator2 = new BufferedGenerator();
        Length length2 = new Length(generator2);
        assertEquals(6 * 5, length2.begin(baseResourcesPath + "file.txt"));

        Generator generator3 = new OneBufferGenerator(100);
        Length length3 = new Length(generator3);
        assertEquals(6 * 5, length3.begin(baseResourcesPath + "file.txt"));

        Generator generator4 = new MMapGenerator(100);
        Length length4 = new Length(generator4);
        assertEquals(6 * 5, length4.begin(baseResourcesPath + "file.txt"));

    }

    @Test
    public void newOneBuffer() throws IOException {
        Generator generator = new OneBufferGenerator(1000);
        BaseInputStream baseInputStream = generator.getInputStream(baseResourcesPath + "file.txt");

        assertNotNull(baseInputStream);
        baseInputStream.open();

        assertEquals("aaaaaa",baseInputStream.readln());
        assertEquals("bbbbbb",baseInputStream.readln());
        assertEquals("cccccc",baseInputStream.readln());
        assertEquals("dddddd",baseInputStream.readln());
        assertEquals("óóóóóó",baseInputStream.readln());

        assertTrue(baseInputStream.end_of_stream());

        baseInputStream.seek(0);
        assertEquals("aaaaaa",baseInputStream.readln());
        baseInputStream.seek(21);
        assertEquals("dddddd",baseInputStream.readln());


    }

    @Test
    public void MMap2() throws IOException {
        Generator generator = new MMapGenerator(2000);
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
    public void MMap() throws IOException {
        int nbCharacter = 5;
        MMapGenerator generator = new MMapGenerator(nbCharacter);
        Test(generator);
        Test2(generator);
        testFile(generator);
    }

    @Test
    public void Pair() throws IOException {
        int nbCharacter = 5;
        int sizeBuffer = 5;
        ArrayList<Generator> t =  new ArrayList<>();
        t.add(new MMapGenerator(nbCharacter));
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
        Generator generatorReader = new OneGenerator();
        Generator generatorWriter = new OneGenerator();
        RRMerge rrMerge = new RRMerge(generatorReader, generatorWriter);
        String partsPath = this.baseResourcesPath + "RRMergeExamplePart";
        rrMerge.begin(partsPath + "1.csv", partsPath + "2.csv", partsPath + "3.csv", partsPath + "4.csv", partsPath + "5.csv");


        BaseInputStream actualInputStream = new OneInputStream("RRMergeOutput.csv");
        actualInputStream.open();
        BaseInputStream expectedInputStream = new OneInputStream(this.baseResourcesPath + "RRMergeExpectedOutput.csv");
        expectedInputStream.open();

        while (!expectedInputStream.end_of_stream()) {
            assertEquals(expectedInputStream.readln(), actualInputStream.readln());
        }
        assertTrue(actualInputStream.end_of_stream());

    }

    @Test
    public void ExtSort() throws IOException {
        Generator generatorReader = new OneBufferGenerator(42);
        Generator generatorWriter = new OneBufferGenerator(42);
        ExtSort extSort = new ExtSort(generatorReader, generatorWriter);
        extSort.begin(this.baseResourcesPath + "ExtSortExample.csv", 3, 10, 2);

        BaseInputStream actualInputStream = new OneInputStream("ExtSortOutput.csv");
        actualInputStream.open();
        BaseInputStream expectedInputStream = new OneInputStream(this.baseResourcesPath + "ExtSortExpectedOutput.csv");

        expectedInputStream.open();

        while (!expectedInputStream.end_of_stream()) {
            assertEquals(expectedInputStream.readln(), actualInputStream.readln());
        }
        assertTrue(actualInputStream.end_of_stream());
    }


    public void Test(Generator generator) throws IOException {
        String filename = baseResourcesPath + "text2.txt";
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
        String filename = baseResourcesPath + name + ".txt";
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
        String filename = baseResourcesPath + "text3.txt";
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
        BaseInputStream baseInputStream = generator.getInputStream("database/imdb/aka_title.csv");
        assertNotNull(baseInputStream);

        baseInputStream.open();

        assertEquals("13117,833595,Malhação - Adolescência: A Passagem da Infância Para a Vida Adulta,,2,2011,M4342,,,,(Brazil) (nineteenth season title),320b4fa8ae74411e55cde509d9883e9c", baseInputStream.readln());
        assertEquals("6017,393076,Dirty Pair,,2,1985,D6316,,,,(USA),f77196370d0815fd72eb57ea2896c0e3", baseInputStream.readln());
        baseInputStream.close();
    }
}