package com.utyf;

import java.util.*;


@SuppressWarnings("WeakerAccess")
public class Main {

    static final int ELEMENTS_NUMBER = 10;    // number of elements in file for sort
    static final int BUFFER_SIZE     = 3;     // number of elements for load into memory. Will create 3 buffers
    static final int ELEMENT_SIZE    = 37;
    static final String fileName = "ForSort.txt"; // name of file for sort
    static final String tempFile = "temp.txt";    // name of temp file (will be deleted)


    public static void main(String[] args) {
        System.out.println( new Date() );

        DataFile df = new DataFile(fileName);
        df.createRandom(ELEMENTS_NUMBER);
        df.close();

        FileSort.doSort(fileName, tempFile);

        //String[] arr = MergeSort.getRandomArray(ELEMENTS_NUMBER);
        //MergeSort.doSort(arr);
        //for( String s : arr ) System.out.println(s);

        System.out.println( new Date() );
    }
}

/*
Sun Nov 27 16:56:05 MSK 2016
temp.txt 100000
Sun Nov 27 16:57:54 MSK 2016
 */
