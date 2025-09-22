/*
This class encapsulates the AI that cheats at hangman.
It also encapsulates the game state for Wheel of Lies.
Made by @Rene Bourdeth and @Felipe Jorge
 */
package wol;

public class CheatingExecutioner extends java.lang.Object implements Executioner {
    private static int maxGuesses_;
    private int currentGuesses_;
    private char invalidChar_;
    private java.util.Collection<java.lang.String> words_;
    private java.util.Collection<java.lang.Character> lettersGuessed_;
    private java.util.TreeMap<String, Integer> Map_;
    private String secretWord_;

    // Default contructor
    public CheatingExecutioner(){
    }

    //This is the only method of ConsoleExecutioner that takes user input.
    public void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar){
        maxGuesses_ = maxIncorrectGuesses;
        invalidChar_ = invalidChar;
        words_ = words;
        currentGuesses_ = 0;
        lettersGuessed_ = new java.util.TreeSet<>();
        secretWord_ = null;
    }

    //Returns the number of incorrect letter guesses the condemned can make before losing.
    public int incorrectGuessesRemaining(){
        return maxGuesses_ - currentGuesses_;
    }

    //Returns a collection of the letters the condemned has already guessed.
    public java.util.Collection<java.lang.Character> guessedLetters(){
        return lettersGuessed_;
    }
    
    // Get word length from the word collection
    private int getWordLength() {
        return words_.isEmpty() ? 0 : words_.iterator().next().length();
    }

    //Return a specially formatted version of the secret word.
    public String formattedSecretWord(){

        StringBuilder formatedWord = new StringBuilder();
        if (secretWord_ != null){
            for (int i = 0; i < secretWord_.length(); i++){
                char currentChar = secretWord_.charAt(i);
                if (lettersGuessed_.contains(Character.toUpperCase(currentChar))){
                    formatedWord.append(Character.toUpperCase(currentChar));
                } else {
                    formatedWord.append(invalidChar_);
                }
            }
        }
        else {
            for (int i = 0; i < getWordLength(); i++){
                formatedWord.append('*');
            }
        }

        return formatedWord.toString();
    }

    //Returns the count of words that could still be the secret word.
    public int countOfPossibleWords(){
        return words_.size();
    }

    //Returns true if the given letter has already been guessed by the condemned.
    public boolean letterAlreadyGuessed(char letter){
        return lettersGuessed_.contains(letter);
    }

    //Registers the condemned next guess.
    public int registerAGuess(char letter){
        
        if (!lettersGuessed_.contains(letter)) {
            lettersGuessed_.add(letter);
        }
        
        java.util.Map<String, java.util.List<String>> wordFamilies = createWordFamilies(letter);
        
        String largestFamilyPattern = selectLargestWordFamily(wordFamilies);
        java.util.List<String> largestFamily = wordFamilies.get(largestFamilyPattern);
        
        words_.clear();
        words_.addAll(largestFamily);
        
        // If only one word left, pick it as the secret word
        if (words_.size() == 1 && secretWord_ == null) {
            secretWord_ = words_.iterator().next();
        }

        int nbTimesCharAppears = countTimesCharAppears(largestFamilyPattern, letter);
        
        if (nbTimesCharAppears == 0) {
            currentGuesses_++;
        }
        
        return nbTimesCharAppears;
    }
    
    // creates word families based on pattern
    private java.util.Map<String, java.util.List<String>> createWordFamilies(char letter) {
        java.util.Map<String, java.util.List<String>> families = new java.util.TreeMap<>();
        char lowerLetter = Character.toLowerCase(letter);
        
        for (String word : words_) {
            String pattern = createWordPattern(word, lowerLetter);
            families.computeIfAbsent(pattern, String -> new java.util.ArrayList<>()).add(word);
        }
        
        return families;
    }
    
    // creates a pattern for a word based on string
    private String createWordPattern(String word, char letter) {
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char currentChar = Character.toLowerCase(word.charAt(i));
            if (currentChar == letter) {
                pattern.append(Character.toUpperCase(letter));
            } else {
                pattern.append('_');
            }
        }
        return pattern.toString();
    }
    
    private String selectLargestWordFamily(java.util.Map<String, java.util.List<String>> families) {
        String bestPattern = null;
        int largestSize = 0;
        int minOccurrences = Integer.MAX_VALUE;
        
        // Single pass optimization: find largest family with fewest occurrences
        for (java.util.Map.Entry<String, java.util.List<String>> entry : families.entrySet()) {
            int familySize = entry.getValue().size();
            String pattern = entry.getKey();
            
            // Count letter occurrences in this pattern
            int occurrences = 0;
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '_') {
                    occurrences++;
                }
            }

            boolean shouldSelect = (familySize > largestSize) || 
                                 (familySize == largestSize && occurrences < minOccurrences);
            
            if (shouldSelect) {
                largestSize = familySize;
                minOccurrences = occurrences;
                bestPattern = pattern;
            }
        }
        
        return bestPattern;
    }

    private int countTimesCharAppears(String pattern, char letter) {
        int count = 0;
        char upperLetter = Character.toUpperCase(letter);
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == upperLetter) {
                count++;
            }
        }
        return count;
    }

    //Return the secret word.
    public java.lang.String revealSecretWord(){
        String result = "unknown";
        
        if (secretWord_ != null) {
            result = secretWord_.toUpperCase();
        } else if (!words_.isEmpty()) {
            result = words_.iterator().next().toUpperCase();
        }
        
        return result;
    }

    //Returns true if the game is over.
    public boolean isGameOver(){
        return (guessedTheWord() || currentGuesses_ >= maxGuesses_);
    }

    //Returns a collection of the letters the condemned has already guessed.
    private boolean guessedTheWord(){
        boolean allLettersGuessed = false;
        
        if (secretWord_ != null) {
            allLettersGuessed = true;
            // Check if ALL letters in the secret word have been guessed
            for (int i = 0; i < secretWord_.length() && allLettersGuessed; i++) {
                if (!lettersGuessed_.contains(Character.toUpperCase(secretWord_.charAt(i)))) {
                    allLettersGuessed = false;
                }
            }
        }
        
        return allLettersGuessed;
    }
}
