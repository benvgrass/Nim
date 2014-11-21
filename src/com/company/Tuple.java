/**
 * Ben Grass
 * Date: 4/12/13
 * Time: 10:32 AM
 * This Program: Represents a Tuple
 * Note: Originally made for Use in
 * @see Nim
 * and
 * @see NimPlayer
 */
import java.util.Arrays; // For String toString with the array input
public class Tuple<X, Y> {
    private final X x;  // represents the first variable in the tuple
    private final Y y; // represents the second variable in the tuple
    public Tuple(X x, Y y) {  // constructor, initializes the Tuple
        this.x = x;
        this.y = y;
    }

    public Tuple clone(){   // clones the Tuple
        return new Tuple(this.x,this.y);
    }

    public X getX() {   // returns variable X
        return x;
    }

    public Y getY() {   // returns variable Y
        return y;
    }

    public String toString() {  // represents the String value of a tuple

        return ("(" + this.x + ", " + this.y + ")"); // returns (x,y)
    }

    public static String toString(Tuple<int[],Boolean> a) { // gets the String value of a tuple with specific inputs (For Nim.java)
        /**
         * @param a - is a Tuple of type int[] and Boolean, meant for use in Nim.java, so that Hashmap will map according to
         *          the String, which is about the only thing the same between two different Tuples of the same
         *          game-state and player. Ex) They may have different reference, but they will always have the same
         *          String, as long as that string is formatted, which is the point of Arrays.toString.
         *
         */
        return ("(" + Arrays.toString(a.x) + ", " + a.y + ")");  // returns (String version of x,y)
    }


    public boolean equals(Tuple<X,Y> o) {  // represents the equality of two Tuple(s), was intended for Nim.java but never used.
        /**
         * @param o - another tuple
         */
        return ((this.x.equals(o.getX()))&& (this.y.equals(o.getY()))); // returns boolean of whether they are equal
    }




}