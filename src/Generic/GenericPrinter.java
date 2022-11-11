package Generic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GenericPrinter<T> {
    private final T t;

    public GenericPrinter(T t) {
        this.t = t;
    }


    public void print() {
        System.out.println(t);
    }
    private static <T> void shout(T t) {
        System.out.println(t + "!!!");
    }
    private static void printList(List<?> lista) {
        for (Object t : lista) {
            System.out.println(t);
        }
    }

    public static void main(String[] args) {
//        // uses print
//        GenericPrinter<Integer> intNr = new GenericPrinter<>(14);
//        intNr.print();
//
//        GenericPrinter<Double> dNr = new GenericPrinter<>(2.54);
//        dNr.print();
//
//        GenericPrinter<String> strPr = new GenericPrinter<>("Hejsan");
//        strPr.print();
//
//        // uses shout
//        shout("Hej");
//        shout(23);
//        shout(2.5);

        // list
        List<Integer> listan = new ArrayList<>();
        listan.add(4);
        listan.add(8);
        listan.add(14);

        printList(listan);
    }
}
