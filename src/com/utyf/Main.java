package com.utyf;

import java.util.*;


@SuppressWarnings("WeakerAccess")
public class Main {

    static final String fileName = "ForSort.txt"; // name of file for sort
    static final String tempFile = "temp.txt";    // name of temp file (will be deleted)
    static final int ELEMENT_SIZE    = 37;        // size of one element in the file
    static final int BUFFER_SIZE     = 10000;      // number of elements for load into memory. Will create 3 buffers
    private static final int ELEMENTS_NUMBER = 10000000;    // number of elements in file for sort


    public static void main(String[] args) {

        System.out.println( "BUFFER_SIZE = " + BUFFER_SIZE + ", ELEMENTS_NUMBER = "+ELEMENTS_NUMBER+"\n"+new Date() );

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
BUFFER_SIZE = 10000, ELEMENTS_NUMBER = 10000000
Sat Dec 17 12:32:19 MSK 2016
Sat Dec 17 12:32:50 MSK 2016
ForSort.txt
Sat Dec 17 12:33:10 MSK 2016
 */
