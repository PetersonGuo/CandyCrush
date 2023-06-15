/**
 * A class that contains 3 generics that are stored as x, y, and z.
 * 
 * @author Peterson Guo
 * @version June 15, 2023
 */
public class Triple<T, U, V> {
    public T x;
    public U y;
    public V z;
    
    /**
     * A constructor that takes 3 generic types and sets them as x, y, and z.
     * 
     * @param x     One generic type
     * @param y     Another generic type
     * @param z     Another genertic type
     */
    public Triple(T x, U y, V z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * A method that returns x, y, and z as a string
     * 
     * @return String   A string that contains x, y, and z
     */
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}