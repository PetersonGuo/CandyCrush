/**
 * A class that contains 2 generics that are stored as x and y.
 * 
 * @author Peterson Guo
 * @version June 15, 2023
 */
public class Pair<T, U> {
    public T x;
    public U y;
    /**
     * A constructor that takes 2 generic types and sets them as x and y.
     * 
     * @param x     One generic type (generally in integer)
     * @param y     Another generic type (generally an integer)
     */
    public Pair(T x, U y) {
        this.x = x;
        this.y = y;
    }
}
