package PhoneBook.WithProtocol.Resources;

import java.io.Serializable;

public class Response implements Serializable {

    private boolean found;
    private Friend friend;

//    private String notFound;

    public Response(boolean found) {
        this.found = found;
    }

    public Response(boolean found, Friend friend) {
        this.found = found;
        this.friend = friend;
    }
//
//    public Response(boolean found, String notFound) {
//        this.found = found;
//        this.notFound = notFound;
//    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
