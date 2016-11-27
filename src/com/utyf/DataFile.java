package com.utyf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;



class DataFile {
//    private String name;
//    Path   path;
    private RandomAccessFile raf;

    DataFile(String _name) {
//        name = _name;
//        path = Paths.get(name);
        try {
            raf = new RandomAccessFile(_name, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        }
    }
}
