package PhoneBook.MultiUserChat.Server;

import PhoneBook.MultiUserChat.Resources.*;

public class PhoneBookProtocol {

    private static final int WAITING = 0;
    private static final int SENTWELCOME = 1;
    private static final int SENTANSWER = 2;

    String welcomeMessage = "Ange namn på personen du söker: ";

    private int state = WAITING;

    public String inputFromUser(String userInput) {
        String output = null;
        DAO dao = new DAO();

        if (state == WAITING) {
            output = welcomeMessage;
            state = SENTWELCOME;
        }
        else if (state == SENTANSWER) {
            if (userInput.equalsIgnoreCase("n")) {
                output = "n";
            } else {
                output = welcomeMessage;
                state = SENTWELCOME;
            }
        }
        else if (state == SENTWELCOME) {
            Friend outputFriendToClient;
            System.out.println("Client: " + userInput);
            outputFriendToClient = dao.getPersonByName((userInput));
            if (outputFriendToClient != null) {
                System.out.println("To client: " + outputFriendToClient);
                output = (outputFriendToClient + " Söka igen? (j/n) ");
            } else {
                System.out.println("To client: Not found, " + userInput);
                output = ("Personen du sökte hittades ej. Namn: " + userInput +
                        " Söka igen? (j/n) ");
            }
            state = SENTANSWER;
        }
        return output;
    }


}
