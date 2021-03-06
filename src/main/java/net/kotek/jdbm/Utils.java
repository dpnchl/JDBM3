package net.kotek.jdbm;

import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Various utilities used in JDBM
 */
class Utils {

    /**
     * empty string is used as dummy value to represent null values in HashSet and TreeSet
     */
    static final String EMPTY_STRING = "";



    public static byte[] encrypt(Cipher cipherIn, ByteBuffer b) {
        if(cipherIn==null && b.hasArray())
            return b.array();
        byte[] bb = new byte[Storage.BLOCK_SIZE];
        b.rewind();
        b.get(bb,0,Storage.BLOCK_SIZE);
        return encrypt(cipherIn,bb);
    }
    
    public static byte[] encrypt(Cipher cipherIn, byte[] b) {
        if (cipherIn == null)
            return b;

        try {
            return cipherIn.doFinal(b);
        } catch (Exception e) {
            throw new IOError(e);
        }

    }


    /**
     * Compares comparables. Default comparator for most of java types
     */
    static final Comparator COMPARABLE_COMPARATOR = new Comparator<Comparable>() {
        public int compare(Comparable o1, Comparable o2) {
            return o1.compareTo(o2);
        }
    };


    static String formatSpaceUsage(long size) {
        if (size < 1e4)
            return size + "B";
        else if (size < 1e7)
            return "" + Math.round(1D * size / 1024D) + "KB";
        else if (size < 1e10)
            return "" + Math.round(1D * size / 1e6) + "MB";
        else
            return "" + Math.round(1D * size / 1e9) + "GB";
    }


    static boolean allZeros(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0) return false;
        }
        return true;
    }


//    public void write(byte[] data, ) throws IOException {
//        try {
//            s.write(pageNumber,cipherIn.doFinal(data));
//        } catch (IllegalBlockSizeException e) {
//            throw new IOError(e);
//        } catch (BadPaddingException e) {
//            throw new IOError(e);
//        }
//    }
//
//    public boolean read(long pageNumber, byte[] data) throws IOException {
//
//           byte[] buf = new byte[BLOCK_SIZE];
//           if(s.read(pageNumber,buf)){
//               try {
//                    cipherOut.doFinal(buf, 0, BLOCK_SIZE, data);
//               } catch (IllegalBlockSizeException e) {
//                   throw new IOError(e);
//               } catch (BadPaddingException e) {
//                   throw new IOError(e);
//               } catch (ShortBufferException e) {
//                   throw new IOError(e);
//               }
//
//               return true;
//           }else{
//              //page does not exist in underlying store
//              System.arraycopy(RecordFile.CLEAN_DATA,0,data,0,BLOCK_SIZE);
//              return false;
//           }
//
//    }


    /** quick sort which also sorts elements in second array*/
    static void quickSort(final long array[], final int array2[], final int low, final int n){
        long temp;
        int temp2;
        int lo = low;
        int hi = n;
        if (lo >= n) {
            return;
        }
        long mid = array[(lo + hi) / 2];
        while (lo < hi) {
            while (lo<hi && array[lo] < mid) {
                lo++;
            }
            while (lo<hi && array[hi] > mid) {
                hi--;
            }
            if (lo < hi) {
                temp = array[lo];
                array[lo] = array[hi];
                array[hi] = temp;
                temp2 = array2[lo];
                array2[lo] = array2[hi];
                array2[hi] = temp2;
            }
        }
        if (hi < lo) {
            temp2 = hi;
            hi = lo;
            lo = temp2;
        }
        quickSort(array, array2, low, lo);
        quickSort(array, array2, lo == low ? lo+1 : lo, n);
    }
    
    
    static <E> E max(E e1, E e2, Comparator comp){
        if(e1 == null) return e2;
        if(e2 == null) return e1;

        if(comp == null)
            comp = COMPARABLE_COMPARATOR;
        return comp.compare(e1,e2)<0 ? e2:e1;
    }

    static <E> E min(E e1, E e2, Comparator comp){
        if(e1 == null) return e2;
        if(e2 == null) return e1;

        if(comp == null)
            comp = COMPARABLE_COMPARATOR;

        return comp.compare(e1,e2)>0 ? e2:e1;
    }

}
