package com.utyf;

import java.util.*;


public class Main {

    static final int ELEMENTS_NUMBER = 100000;
    static final String fileName = "ForSort.txt";
    static final String tempFile = "temp.txt";


    public static void main(String[] args) {
        String s0 = "00000000-0000-0000-0000-000000000000";
        String s1 = "11111111-1111-1111-1111-111111111111";
        String s2 = "22222222-2222-2222-2222-222222222222";
        String s3 = "33333333-3333-3333-3333-333333333333";

        System.out.println( new Date() );

//        Scanner s = new Scanner(System.in);
//        String str;
//        str = s.nextLine(); System.out.println(str);

        DataFile df = new DataFile(fileName);
        df.createRandom(ELEMENTS_NUMBER);
//        df.put(0, s0);
        df.put(1, s1);
        df.put(11, s1);
        df.put(2, s2);
        df.put(3, s3);
        df.put(33, s3);

        DataFile tmp = new DataFile(tempFile);
        DataFile out = FileSort.doSort(df, tmp);
        System.out.println( out.name );

//        String[] arr = MergeSort.getRandomArray(20);
        //System.out.println(arr.length);

//        MergeSort.doSort(arr);
//        for (String anArr : arr) System.out.println(anArr);

        // str = s.nextLine(); System.out.println(str);
        System.out.println( new Date() );
    }
}

