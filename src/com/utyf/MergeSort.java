package com.utyf;

import java.util.*;

/**
 * Created by Utyf on 25.11.2016.
 *
 */


class MergeSort {

    // array A[] has the items to sort; array B[] is a work array
    static void doSort(String[] A)
    {
        int n = A.length;
        String[] B = new String[A.length];

        // Each 1-element run in A is already "sorted".
        // Make successively longer sorted runs of length 2, 4, 8, 16... until whole array is sorted.
        for (int width = 1; width < n; width = 2 * width) {
            // Array A is full of runs of length width.
            for (int i = 0; i < n; i = i + 2 * width)
                // Merge two runs: A[i:i+width-1] and A[i+width:i+2*width-1] to B[]
                // or copy A[i:n-1] to B[] ( if(i+width >= n) )
                merge(A, i, Math.min(i+width, n), Math.min(i+2*width, n), B);

            // Now work array B is full of runs of length 2*width.
            // Copy array B to array A for next iteration.
            System.arraycopy(B, 0, A, 0, A.length);
        }
    }

    //  Left run is A[iLeft :iRight-1].
    // Right run is A[iRight:iEnd-1  ].
    private static void merge(String[] A, int iLeft, int iRight, int iEnd, String[] B)
    {
        int i = iLeft;
        int j = iRight;
        // While there are elements in the left or right runs...
        for (int k = iLeft; k < iEnd; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < iRight && (j >= iEnd || A[i].compareTo( A[j])<0 )) { // A[i] <= A[j]
                B[k] = A[i];
                i = i + 1;
            } else {
                B[k] = A[j];
                j = j + 1;
            }
        }
    }

    static String[] getRandomArray(int size) {
        String[] res = new String[size];

        for( int i=0; i<size; i++ )
            res[i] = UUID.randomUUID().toString();

        return res;
    } //*/
}
