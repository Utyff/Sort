package com.utyf;

import java.util.*;


@SuppressWarnings("WeakerAccess")
public class Main {

    static final String fileName = "ForSort.txt"; // name of file for sort
    static final String tempFile = "temp.txt";    // name of temp file (will be deleted)
    static final int ELEMENT_SIZE    = 37;        // size of one element in the file
    static final int BUFFER_SIZE     = 1000;      // number of elements for load into memory. Will create 3 buffers
    private static final int ELEMENTS_NUMBER = 1000000;    // number of elements in file for sort


    public static void main(String[] args) {
        System.out.println( new Date() );

        DataFile df = new DataFile(fileName);
        df.createRandom(ELEMENTS_NUMBER);
        df.close();
        System.out.println( new Date() );

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
