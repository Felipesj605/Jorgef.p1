package wol;

public class CheatingExeecutioner extends java.lang.Object implements Executioner {
    private int maxGuesses_;
    private int currentGuesses_;
    private char invalidChar_;
    private java.util.Collection<java.lang.String> words_;
    private java.util.Collection<java.lang.Character> lettersGuessed_;
    private java.util.TreeMap<String, Integer> Map_;
    private String secretWord_;

    // Default contructor
    public CheatingExeecutioner(){
    }

    //This is the only method of ConsoleExecutioner that takes user input.
    public void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar){
        maxGuesses_ = maxIncorrectGuesses;
        invalidChar_ = invalidChar;
        words_ = words;
        currentGuesses_ = 0;
        lettersGuessed_ = new java.util.ArrayList<>();
        
        // Don't pick a secret word yet - wait until only one word remains
        secretWord_ = null;
    }

    //Returns the number of incorrect letter guesses the condemned can make before losing.
    public int incorrectGuessesRemaining(){
        return maxGuesses_ - currentGuesses_;
    }

    public java.util.Collection<java.lang.Character> guessedLetters(){
        return lettersGuessed_;
    }

    //Return a specially formatted version of the secret word.
    public String formattedSecretWord(){

        StringBuilder formatedWord = new StringBuilder();
        for (int i = 0; i < secretWord_.length(); i++){
            char currentChar = secretWord_.charAt(i);
            if (guessedLetters().contains(Character.toUpperCase(currentChar))){
                formatedWord.append(Character.toUpperCase(currentChar));
            } else {
                formatedWord.append(invalidChar_);
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
        lettersGuessed_.add(letter);
        
        java.util.Map<String, java.util.List<String>> wordFamilies = createWordFamilies(letter);
        
        String largestFamilyPattern = selectLargestWordFamily(wordFamilies);
        java.util.List<String> largestFamily = wordFamilies.get(largestFamilyPattern);
        
        words_.clear();
        words_.addAll(largestFamily);
        
        // If only one word left, pick it as the secret word
        if (words_.size() == 1 && secretWord_ == null) {
            secretWord_ = words_.iterator().next();
        }

        int nbTimesCharAppears = countLetterOccurrences(largestFamilyPattern, letter);
        
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
        String largestPattern = null;
        int largestSize = 0;
        
        // Find the largest family
        for (java.util.Map.Entry<String, java.util.List<String>> entry : families.entrySet()) {
            int familySize = entry.getValue().size();
            if (familySize > largestSize) {
                largestSize = familySize;
                largestPattern = entry.getKey();
            }
        }
        
        return largestPattern;
    }

    private int countLetterOccurrences(String pattern, char letter) {
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
        return secretWord_.toUpperCase();
    }

    //Returns true if the game is over.
    public boolean isGameOver(){
        return (guessedTheWord() || currentGuesses_ >= maxGuesses_);
    }

    //Returns a collection of the letters the condemned has already guessed.
    private boolean guessedTheWord(){
        boolean bool = true;
        for (int i =0;i < secretWord_.length(); i++){
            if (!lettersGuessed_.contains(Character.toUpperCase(secretWord_.charAt(i)))){
                bool = false;
            }
        }
        return bool;
    }
}
