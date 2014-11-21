/**
 * Ben Grass
 * Date: 4/12/13
 * Time: 8:03 PM
 * This Program: Tools for use with boolean/boolean arrays
 * Note: Used in
 * @see Nim
 */
public class Booleans {
    public static boolean any(boolean[] a){ // returns the or of all of elements in a boolean array
        /**
         * @param a - array of booleans to find the or of
         */
        boolean total = false; // the total initialized to false because that will change with any true
        for(boolean b: a)  // for all elements in a
            total |= b;  //  total equals total or b, which is an element of a
        return total; // return total
    }
    public static boolean all(boolean[] a){  // returns the and of all the elements in a boolean array
        /**
         * @param a - array of booleans to find the and of
         */
        boolean total = true;  // the total initialized to true because it will change with any false
        for(boolean b: a) // for all elements in a
            total &= b;  //  total equals total and b, which is an element of a
        return total;  // return total
    }

}
