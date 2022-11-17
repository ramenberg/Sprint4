package PhoneBook.MultiUserChat.Server;

import PhoneBook.MultiUserChat.Resources.Friend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DAO implements Serializable {
    Friend a = new Friend("Jenny", "Larsson", "Vendelvägen 14",
            "0700123456", "1979-05-12");
    Friend b = new Friend("Filip", "Ljung", "Rosgatan 54",
            "0701234567", "1984-12-19");
    Friend c = new Friend("Holly", "Ring", "Solgatan 7",
            "0708235689", "1987-08-09");
    Friend d = new Friend("Alvin", "Rönn", "Vintergatan 1",
            "0704541278", "1989-01-29");
    Friend e = new Friend("Sven", "Ljungberg", "Videvägen 31",
            "0700123123", "1969-05-30");

    public static List<Friend> allPersons = new ArrayList<>();
    public DAO() {
        allPersons.add(a);
        allPersons.add(b);
        allPersons.add(c);
        allPersons.add(d);
        allPersons.add(e);
    }

    public static List<Friend> getAllPersons() {
        return allPersons;
    }

    public Friend getPersonByName(String s) {
        for (Friend f : allPersons) {
            if ((s.equalsIgnoreCase(f.getFirstName()) ||
                    s.equalsIgnoreCase(f.getLastName()))) {
                System.out.println("Friend Found");
                return f;
            }
        }
        return null;
    }
}
