package com.utyf;

import java.util.*;

/**
 * Created by Utyf on 25.11.2016.
 *
 */


public class MergeSort {

    static void doSort(String [] arr) {
        List<String> lst = Arrays.asList(arr);

        Collections.sort(lst);

        // lst.forEach(System.out::println);

    }

    static String[] getRandomArray(int size) {
        String[] res = new String[size];

        for( int i=0; i<size; i++ ) {
            res[i] = UUID.randomUUID().toString();
        }
        return res;
    }
}
