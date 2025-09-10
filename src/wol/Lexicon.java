/*
This class represents a collection of words.
It can create a filtered collection that only includes words of a specified length.
 */
package wol;

import java.util.ArrayList;
import java.util.Collection;

public class Lexicon {
    private Collection<String> words;
    
    //Constructs a lexicon from the given collection of words.
    public Lexicon(java.util.Collection<java.lang.String> words){
        this.words = new ArrayList<>(words);
    }

    //This method returns a Collection of words of the given length.
    public java.util.Collection<java.lang.String> wordsOfLength(int length){
        Collection<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == length) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }
}
