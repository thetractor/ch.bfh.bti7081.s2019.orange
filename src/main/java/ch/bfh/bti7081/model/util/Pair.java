package ch.bfh.bti7081.model.util;

/**
 * Implementation of a customized tuple.
 * @param <S> First generic tuple type
 * @param <T> Second generic tuple type
 *
 * @author yannisvalentin.schmutz@students.bfh.ch
 */
public class Pair<S, T> {
    private S first;
    private T second;

    /**
     * Optional constructor to create a null-tuple
     */
    public Pair() {
        first = null;
        second = null;
    }

    /**
     * Overloaded constructor to initialize a tuple with two values
     * @param first First generic tuple type
     * @param second Second generic tuple type
     */
    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first tuple type
     * @return First generic tuple type
     */
    public S getFirst() {
        return first;
    }

    /**
     * Returns the second tuple type
     * @return Second generic tuple type
     */
    public T getSecond() {
        return second;
    }

    /**
     * Sets the first tuple type
     * @param newValue First generic tuple type
     */
    public void setFirst(S newValue) {
        first = newValue;
    }

    /**
     *
     *
     * @param newValue Second generic tuple type
     */
    public void setSecond(T newValue) {
        second = newValue;
    }
}
