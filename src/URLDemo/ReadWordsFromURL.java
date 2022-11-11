package URLDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadWordsFromURL {

    URL myURL = new URL("https://github.com/dwyl/english-words/blob/master/words.txt?raw=true");

    public ReadWordsFromURL() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(myURL.openStream()));

        String read;
        long count = 0;

        while ((r.readLine()) != null) {
            read = r.readLine();
            String[] words = read.split("\\s+\\t+");
            count += words.length;
        }
        System.out.println("Antal ord i URL:en är " + count);
        r.close();
    }

    public static void main(String[] args) throws IOException {
        new ReadWordsFromURL();
    }
}
