package Generic;

import java.util.LinkedList;
import java.util.List;

public class NumberQueue<T extends Number> implements QueueInterface<T>{

    private List<T> list = new LinkedList<>();

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
    public Number addition(List<Number> l) {
        Number sum = 0;
        for (Number n : l) {
            sum = sum.intValue() + n.intValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        new NumberQueue();
    }
}
