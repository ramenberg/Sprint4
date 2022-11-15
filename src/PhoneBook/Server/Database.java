package PhoneBook.Server;

import java.util.ArrayList;
import java.util.List;

public class Database {

    protected static List<Friend> friendList = new ArrayList<>();

    protected Database(List<Friend> friendList) {
        Database.friendList = friendList;
    }

    protected static List<Friend> getFriendList() {
        return friendList;
    }

    protected void setFriendList(List<Friend> friendList) {
        Database.friendList = friendList;
    }

    protected Database() {

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

        friendList.add(a);
        friendList.add(b);
        friendList.add(c);
        friendList.add(d);
        friendList.add(e);
    }
}
