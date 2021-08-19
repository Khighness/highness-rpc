package top.parak.extension;

/**
 * @author KHighness
 * @since 2021-08-20
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
