package ch.bfh.bti7081.model.util;

public class Pair<S, T> {
    private S first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(S newValue) {
        first = newValue;
    }
    public void setSecond(T newValue) {
        second = newValue;
    }
}
