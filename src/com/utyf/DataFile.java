package com.utyf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;



class DataFile {
    static final int ELEMENT_SIZE = 37;
    String name;
//    Path   path;
    private RandomAccessFile raf;

    DataFile(String _name) {
        name = _name;
        try {
            raf = new RandomAccessFile(_name, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    String seek(int index) {
        try {
            raf.seek( index*ELEMENT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    String get(int index) {
        try {
            raf.seek( index*ELEMENT_SIZE);
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
            raf.seek( index*ELEMENT_SIZE);
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
}
