package com.utyf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;



@SuppressWarnings({"unused", "WeakerAccess"})
class DataFile {
    String name;
    private RandomAccessFile raf;
    int    i1, i2;           // absolute current read position in buffer
    int    start1, start2;   // buffers begin
    int    size1, size2;     // buffers parameters
    byte[] b1 = new byte[Main.ELEMENTS_NUMBER*Main.ELEMENT_SIZE];
    byte[] b2 = new byte[Main.ELEMENTS_NUMBER*Main.ELEMENT_SIZE];

    DataFile(String _name) {
        name = _name;
        try {
            raf = new RandomAccessFile(_name, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    void setBlock1(int start, int _sz) {
        start1 = start;
        i1 = start;
        size1 = _sz;
    }

    void readBlock1(int start, int _sz) {
        start1 = start;
        i1 = start;
        size1 = _sz;
        readBlock(start, _sz, b1);
    }

    void readBlock2(int start, int _sz) {
        start2 = start;
        i2 = start;
        size2 = _sz;
        readBlock(start, _sz, b2);
    }

    private void readBlock(int start, int _sz, byte[] bb) {
        try {
            raf.seek(start*Main.ELEMENT_SIZE);
            raf.read(bb, 0, Math.min(_sz,Main.BUFFER_SIZE)*Main.ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void step1(boolean read) {
        i1++;
        step(start1, i1, size1, b1, read);
    }

    void step2() {
        i2++;
        step(start2, i2, size2, b2, true);
    }

    void step(int start, int ii, int _size, byte[] bb, boolean read) {
        if( _size>=ii-start || ii%Main.BUFFER_SIZE!=0 )  // need to load next block?
            return;

        try {
            if( read ) {
                // load next block
                raf.seek(ii * Main.ELEMENT_SIZE);
                raf.read(bb,
                        0,
                        Math.min(start + _size - ii, Main.BUFFER_SIZE) * Main.ELEMENT_SIZE);
            } else {
                // save current block
                raf.seek((ii - Main.BUFFER_SIZE) * Main.ELEMENT_SIZE);
                raf.write(bb,
                        0,
                        Main.BUFFER_SIZE * Main.ELEMENT_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void copyFrom(DataFile src, int blockNum) {

        int first1 = (i1 - start1)*Main.ELEMENT_SIZE;
        if( blockNum==1 ) {
            int first2 = (src.i1 - src.start1) * Main.ELEMENT_SIZE;
            System.arraycopy(src.b1, first2, b1, first1, Main.ELEMENT_SIZE);
        } else {
            int first2 = (src.i2 - src.start2) * Main.ELEMENT_SIZE;
            System.arraycopy(src.b2, first2, b1, first1, Main.ELEMENT_SIZE);
        }

        step1(false);
    }

    boolean compare() {  //   A[i] <= A[j]
        int first1 = (i1 - start1)*Main.ELEMENT_SIZE;
        int first2 = (i2 - start2)*Main.ELEMENT_SIZE;

        for( int i=0; i<Main.ELEMENT_SIZE; i++ )
            if( b1[first1+i]>b2[first2+i] ) return false;
        return true;
    }

    void flushBuffer() {
        try {
            int sz;
            if( size1<Main.BUFFER_SIZE )
                sz = size1;                   // all buffer
            else
                sz = size1%Main.BUFFER_SIZE;  // only last part

            raf.seek((start1+size1-sz)*Main.ELEMENT_SIZE);
            raf.write(b1, 0, sz*Main.ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

/*    String seek(int index) {
        try {
            raf.seek( index*Main.ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    String get(int index) {
        try {
            raf.seek( index*Main.ELEMENT_SIZE);
            return raf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    String getNext() {
        try {
            return raf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    void put(int index, String str) {
        try {
            raf.seek( index*Main.ELEMENT_SIZE);
            raf.writeBytes( str+"\n" );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void putNext(String str) {
        try {
            raf.writeBytes( str+"\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    } //*/

    @SuppressWarnings("SameParameterValue")
    void createRandom(int size) {
        String str;
        byte[] bytes;

        try {
            raf.seek(0);

            for( int i=0; i<size; i++ ) {
                str = UUID.randomUUID().toString()+"\n";
                bytes = str.getBytes(StandardCharsets.US_ASCII);
                raf.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void close() {
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
