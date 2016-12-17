package com.utyf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static com.utyf.Main.*;
import static org.junit.Assert.*;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class DataFileTest {

    private final static String FILE_NAME = "test.txt";
    private DataFile df;
    private String[] s = {
            "00000000-0000-0000-0000-000000000000",
            "11111111-1111-1111-1111-111111111111",
            "22222222-2222-2222-2222-222222222222",
            "33333333-3333-3333-3333-333333333333",
            "44444444-4444-4444-4444-444444444444",
            "55555555-5555-5555-5555-555555555555",
            "66666666-6666-6666-6666-666666666666",
            "77777777-7777-7777-7777-777777777777",
            "88888888-8888-8888-8888-888888888888",
            "99999999-9999-9999-9999-999999999999"};

    @After
    public void tearDown() throws Exception {
        if( df!=null )  df.close();
        new File(FILE_NAME).delete();
    }

    @Before
    public void setUp() throws Exception {
        new File(FILE_NAME).delete();
        df = new DataFile(FILE_NAME);
    }

    private void write10() throws Exception {

        FileSort.ELEMENTS_NUMBER = s.length;

        for( int i=0; i<s.length; i++ ) {
            String str = s[i];
            setElement(i%Main.BUFFER_SIZE, str);
            df.checkWrite(i);
        }

        df.close();
        df = new DataFile(FILE_NAME);
    }

    private void setElement(int bufferIdx, String str) {
        byte[] bb = (str+"\n").getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bb, 0, df.b1, bufferIdx*Main.ELEMENT_SIZE, Main.ELEMENT_SIZE);
    }

    private boolean compareElements( byte[] bb, int bufferIndex, String str ) {
        byte[] bbs = str.getBytes(StandardCharsets.US_ASCII);
        int start = bufferIndex*ELEMENT_SIZE;
        for (int i=0; i<ELEMENT_SIZE-1; i++)
            if( bb[start+i]!=bbs[i] ) return false;
        return true;
    }

    @Test
    public void copyFrom1() throws Exception {

    }

    @Test
    public void copyFrom2() throws Exception {

    }

    @Test
    public void isFirstLower() throws Exception {

    }

    @Test
    public void checkRead1() throws Exception {
        write10();
        df.initRead();
        for( int i=0; i<s.length; i++ ) {
            if( i%Main.BUFFER_SIZE==0 )
                df.checkRead1(i);
            assertTrue( compareElements(df.b1, i%Main.BUFFER_SIZE, s[i]) );
        }
    }

    @Test
    public void checkRead2() throws Exception {
        write10();
        df.initRead();
        for( int i=0; i<s.length; i++ ) {
            if( i%Main.BUFFER_SIZE==0 )
                df.checkRead2(i);
            assertTrue( compareElements(df.b2, i%Main.BUFFER_SIZE, s[i]) );
        }
    }

    @Test
    public void checkWrite() throws Exception {

    }

    @Test
    public void writeBlock() throws Exception {

    }

    private int lineCount; // for lambda
    @Test
    public void createRandom() throws Exception {
        final int testSize1 = 10;
        df.createRandom( testSize1 );
        assertEquals( testSize1*ELEMENT_SIZE, new File(FILE_NAME).length() );

        final int testSize2 = 100;
        df.createRandom( 100 );
        assertEquals( testSize2*ELEMENT_SIZE, new File(FILE_NAME).length() );

        lineCount=0;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach(s -> { lineCount++; assertEquals( Main.ELEMENT_SIZE-1, s.length()); } );
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(testSize2, lineCount);
    }

    @Test
    public void close() throws Exception {

    }
}