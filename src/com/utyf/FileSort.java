package com.utyf;



public class FileSort {

    // array A[] has the items to sort; array B[] is a work array
    static DataFile doSort(DataFile src, DataFile tmp)
    {
        int n = Main.ELEMENTS_NUMBER;
        DataFile A = src;
        DataFile B = tmp;

        // Each 1-element run in A is already "sorted".
        // Make successively longer sorted runs of length 2, 4, 8, 16... until whole array is sorted.
        for (int width = 1; width < n; width = 2 * width)
        {
            // Array A is full of runs of length width.
            for (int i = 0; i < n; i = i + 2 * width)
            {
                // Merge two runs: A[i:i+width-1] and A[i+width:i+2*width-1] to B[]
                // or copy A[i:n-1] to B[] ( if(i+width >= n) )
                merge(A, i, Math.min(i+width, n), Math.min(i+2*width, n), B);
            }
            // Now work array B is full of runs of length 2*width.
            // Copy array B to array A for next iteration.
            // A more efficient implementation would swap the roles of A and B.
            //CopyArray(B, A, n);
//            System.arraycopy(B, 0, A, 0, A.length);
            DataFile x = A; // swap files
            A = B; B = x;
            // Now array A is full of runs of length 2*width.
        }
        return A;
    }

    //  Left run is A[iLeft :iRight-1].
    // Right run is A[iRight:iEnd-1  ].
    private static void merge(DataFile A, int iLeft, int iRight, int iEnd, DataFile B)
    {
        int i = iLeft;
        int j = iRight;
        String AI = A.get(i);
        String AJ = A.get(j);
        B.seek(iLeft);
        // While there are elements in the left or right runs...
        for (int k = iLeft; k < iEnd; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < iRight && (j >= iEnd || AI.compareTo( AJ)<0 )) { // A[i] <= A[j]
                B.putNext(AI);
                AI = A.get(++i);
            } else {
                B.putNext(AJ);
                AJ = A.get(++j);
            }
        }
    }

}
