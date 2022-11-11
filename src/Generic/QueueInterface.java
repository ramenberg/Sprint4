package Generic;

public interface QueueInterface<T> {
    public void put(T t);
    public T take();
    public int size();
}
