package wol;

public class ConsoleExecutioner extends java.lang.Object implements Executioner {
    private int maxGuesses_;
    private int currentGuesses_;
    private char invalidChar_;
    private java.util.Collection<java.lang.String> words_;
    private java.util.Collection<java.lang.Character> lettersGuessed_;
    private String secretWord_;
    private ConsoleInterface ui_;

    public ConsoleExecutioner(ConsoleInterface ui){
        ui_ = ui;
    }

    @Override
    public void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar){
        maxGuesses_ = maxIncorrectGuesses;
        invalidChar_ = invalidChar;
        words_ = words;
        currentGuesses_ = 0;
        lettersGuessed_ = new java.util.ArrayList<>();
        secretWord_ = ui_.selectSecretWord(words_);
    }

    @Override
    public int incorrectGuessesRemaining(){
        return maxGuesses_ - currentGuesses_;
    }

    @Override
    public java.util.Collection<java.lang.Character> guessedLetters(){
        return lettersGuessed_;
    }

    public String formattedSecretWord(){

        StringBuilder formatedWord = new StringBuilder();
        for (int i = 0; i < secretWord_.length(); i++){
            char currentChar = secretWord_.charAt(i);
            if (guessedLetters().contains(Character.toUpperCase(currentChar))){
                formatedWord.append(Character.toUpperCase(currentChar));
            } else {
                formatedWord.append(secretWord_.charAt(i));
            }
        }
        return formatedWord.toString();
    }

    public int countOfPossibleWords(){
        return words_.size();
    }

    public boolean letterAlreadyGuessed(char letter){
        return lettersGuessed_.contains(letter);
    }

    public int registerAGuess(char letter){
        int nbTimesCharAppears = 0;
        lettersGuessed_.add(letter);
        
        // Count how many times the letter appears in the secret word (case-insensitive)
        for (int i = 0; i < secretWord_.length();i++){
            if (Character.toLowerCase(secretWord_.charAt(i)) == Character.toLowerCase(letter)){
                nbTimesCharAppears++;
            }
        }
        
        // Only increment currentGuesses if the letter is NOT in the secret word
        if (nbTimesCharAppears == 0) {
            currentGuesses_++;
        }

        java.util.Iterator<String> iterator = words_.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            boolean shouldRemove = false;
            
            if (nbTimesCharAppears > 0) {
                // Letter is in secret word - remove words that don't have the letter in the same positions
                for (int i = 0; i < secretWord_.length(); i++) {
                    if (Character.toLowerCase(secretWord_.charAt(i)) == Character.toLowerCase(letter) && 
                        Character.toLowerCase(word.charAt(i)) != Character.toLowerCase(letter)) {
                        shouldRemove = true;
                        break;
                    }
                }
            } else {
                // Letter is not in secret word - remove words that contain this letter (case-insensitive)
                if (word.toLowerCase().contains(String.valueOf(Character.toLowerCase(letter)))) {
                    shouldRemove = true;
                }
            }
            
            if (shouldRemove) {
                iterator.remove();
            }
        }
        return nbTimesCharAppears;
    }

    public java.lang.String revealSecretWord(){
        return secretWord_.toUpperCase();
    }

    public boolean isGameOver(){
        return (guessedTheWord() || currentGuesses_ >= maxGuesses_);
    }

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
