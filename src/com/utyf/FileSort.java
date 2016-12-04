package com.utyf;

import java.io.File;


class FileSort {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void doSort(String _src, String _tmp) {
        int n = Main.ELEMENTS_NUMBER;
        DataFile src = new DataFile(_src);
        DataFile tmp = new DataFile(_tmp);
        DataFile A = src;
        DataFile B = tmp;

        // Make successively longer sorted runs of length 2, 4, 8, 16... until whole array is sorted.
        for (int width = 1; width < n; width += width) {
            A.initRead();
            B.initWrite();
            for (int i = 0; i < n; i = i + 2 * width)
                // Merge two runs: A[i:i+width-1] and A[i+width:i+2*width-1] to B[]
                // or copy A[i:n-1] to B[] ( if(i+width >= n) )
                merge(A, i, Math.min(i+width, n), Math.min(i+2*width, n), B);

            // Now work array B is full of runs of length 2*width.
            // Swap array B and array A for next iteration.
            DataFile x = A; // swap files
            A = B; B = x;
        }

        //
        A.close();
        B.close();
        System.out.println( A.name );
        if( A==src ) { // rename temp to main if need
            new File(_tmp).delete();
        } else {
            new File(_src).delete();
            new File(_tmp).renameTo(new File(_src));
        }
    }

    //  Left run is A[iLeft :iRight-1].
    // Right run is A[iRight:iEnd-1  ].
    private static void merge(DataFile A, int iLeft, int iRight, int iEnd, DataFile B) {

        int i = iLeft;
        int j = iRight;

        // While there are elements in the left or right runs...
        for (int k = iLeft; k < iEnd; k++)
            // If left run head exists and is <= existing right run head.
            if (i < iRight && (j >= iEnd || A.isFirstLower(i,j)) )  // A[i] <= A[j]
                B.copyFrom1(A, i++, k);
            else
                B.copyFrom2(A, j++, k);

//        B.writeBlock(); // write last block
    }

}
