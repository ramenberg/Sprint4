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

        while ((read = r.readLine()) != null) {
            String[] words = read.trim().split("\\s+");
            count += words.length;
        }
        System.out.println("Antal ord är: " + count);
        r.close();
    }

    public static void main(String[] args) throws IOException {
        new ReadWordsFromURL();
    }
}
