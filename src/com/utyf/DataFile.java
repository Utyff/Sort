package com.utyf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import static com.utyf.Main.*;



@SuppressWarnings({"unused", "WeakerAccess"})
class DataFile {
    String  name;
    int     start1=0, start2=0;   // buffers begin
    byte[]  b1 = new byte[ELEMENTS_NUMBER*ELEMENT_SIZE];
    byte[]  b2 = new byte[ELEMENTS_NUMBER*ELEMENT_SIZE];
    RandomAccessFile raf;

    DataFile(String _name) {
        name = _name;
        try {
            raf = new RandomAccessFile(_name, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        readBlock(0, b1);
        readBlock(0, b2);
    }


    void copyFrom1(DataFile src, int idxA, int idxB) {
        src.checkRead1(idxA);

        int firstSRC = (idxA-src.start1) * ELEMENT_SIZE;
        int firstDST = (idxB-    start1) * ELEMENT_SIZE;
        System.arraycopy(src.b1, firstSRC, b1, firstDST, ELEMENT_SIZE);

        checkWrite(idxB);
    }

    void copyFrom2(DataFile src, int idxA, int idxB) {
        src.checkRead2(idxA);

        int firstSRC = (idxA-src.start2) * ELEMENT_SIZE;
        int firstDST = (idxB-    start1) * ELEMENT_SIZE;
        System.arraycopy(src.b2, firstSRC, b1, firstDST, ELEMENT_SIZE);

        checkWrite(idxB);
    }

    boolean compare(int idx1, int idx2) {  //   A[i] <= A[j]

        checkRead1(idx1);
        checkRead1(idx2);

        int first1 = (idx1 - start1)*ELEMENT_SIZE;
        int first2 = (idx2 - start2)*ELEMENT_SIZE;

        for( int i=0; i<ELEMENT_SIZE; i++ ) {
            if (b1[first1 + i] == b2[first2 + i]) continue;
            return b1[first1 + i] < b2[first2 + i];
        }
        return true;
    }

    void checkRead1(int idx) {
        if( idx<start1 || idx>=start1+BUFFER_SIZE ) {
            start1 = idx;
            readBlock(start1, b1);
        }
    }

    void checkRead2(int idx) {
        if( idx<start2 || idx>=start2+BUFFER_SIZE ) {
            start2 = idx;
            readBlock(start2, b2);
        }
    }

    void checkWrite(int idx) {
        if( idx==start1+Math.min(ELEMENTS_NUMBER-start1,BUFFER_SIZE)-1 ) {
            writeBlock();
            start1 = idx+1;
        }
    }

    private void readBlock(int start, byte[] bb) {
        try {
            raf.seek(start*ELEMENT_SIZE);
            raf.read(bb, 0, Math.min(ELEMENTS_NUMBER-start,BUFFER_SIZE)*ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void writeBlock() {
        try {
            raf.seek(start1*ELEMENT_SIZE);
            raf.write(b1, 0, Math.min(ELEMENTS_NUMBER-start1,BUFFER_SIZE)*ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

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
