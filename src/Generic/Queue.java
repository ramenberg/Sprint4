package Generic;

import java.util.LinkedList;
import java.util.List;

public class Queue <T> implements QueueInterface<T>{

    private List<T> list = new LinkedList<>();

    public Queue() {}

    @Override
    public void put(T t) {
        list.add(t);
    }

    @Override
    public T take() {
        T temp = list.get(0);
        list.remove(0);
        return temp;
    }

    @Override
    public int size() {
        return list.size();
    }
}
