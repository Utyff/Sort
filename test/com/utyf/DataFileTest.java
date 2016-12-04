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
        assertEquals(3,  Main.BUFFER_SIZE);
        assertEquals(10, Main.ELEMENTS_NUMBER);
        assertEquals(37, Main.ELEMENT_SIZE);
        new File(FILE_NAME).delete();
        df = new DataFile(FILE_NAME);
    }

    private void write10() throws Exception {
/*        df.raf.seek(0);
        for( String str : s )
            df.raf.write( (str+"\n").getBytes(StandardCharsets.US_ASCII) ); */

        for( int i=0; i<Main.ELEMENTS_NUMBER; i++ ) {
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
        for( int i=0; i<Main.ELEMENTS_NUMBER; i++ ) {
            if( i%Main.BUFFER_SIZE==0 )
                df.checkRead1(i);
            assertTrue( compareElements(df.b1, i%Main.BUFFER_SIZE, s[i]) );
        }
    }

    @Test
    public void checkRead2() throws Exception {
        write10();
        df.initRead();
        for( int i=0; i<Main.ELEMENTS_NUMBER; i++ ) {
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

    private int lineCount;
    @Test
    public void createRandom() throws Exception {
        df.createRandom( 10 );
        assertEquals( 370, new File(FILE_NAME).length() );
        df.createRandom( 100 );
        assertEquals( 3700, new File(FILE_NAME).length() );

        lineCount=0;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach(s -> { lineCount++; assertEquals( Main.ELEMENT_SIZE-1, s.length()); } );
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(100, lineCount);
    }

    @Test
    public void close() throws Exception {

    }

}