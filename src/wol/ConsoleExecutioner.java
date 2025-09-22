/*
This class lets a human select the secret word.
Made by @Rene Bourdeth and @Felipe Jorge
 */
package wol;

public class ConsoleExecutioner extends java.lang.Object implements Executioner {
    private static int maxGuesses_;
    private int currentGuesses_;
    private char invalidChar_;
    private java.util.Collection<java.lang.String> words_;
    private java.util.Collection<java.lang.Character> lettersGuessed_;
    private String secretWord_;
    private ConsoleInterface ui_;


    //Constructor that takes in a ConsoleInterface to ask the user for a secret word.
    public ConsoleExecutioner(ConsoleInterface ui){
        ui_ = ui;
    }

    //This is the only method of ConsoleExecutioner that takes user input.
    public void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar){
        maxGuesses_ = maxIncorrectGuesses;
        invalidChar_ = invalidChar;
        words_ = words;
        currentGuesses_ = 0;
        lettersGuessed_ = new java.util.TreeSet<>();
        secretWord_ = ui_.selectSecretWord(words_);
    }

    //Returns the number of incorrect letter guesses the condemned can make before losing.
    public int incorrectGuessesRemaining(){
        return maxGuesses_ - currentGuesses_;
    }

    //Returns a collection of the letters the condemned has already guessed.
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
                int i = 0;
                boolean stop = false;
                while (i < secretWord_.length() && !stop) {
                    char secretCh = Character.toLowerCase(secretWord_.charAt(i));
                    char wordCh = Character.toLowerCase(word.charAt(i));
                    char guessCh = Character.toLowerCase(letter);

                    if (secretCh == guessCh && wordCh != guessCh) {
                        shouldRemove = true;
                        stop = true;
                    } else if (secretCh != guessCh && wordCh == guessCh) {
                        shouldRemove = true;
                        stop = true;
                    } else {
                        // keep checking
                    }
                    i++;
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
