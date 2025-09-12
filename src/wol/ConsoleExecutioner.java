package wol;

public class ConsoleExecutioner extends java.lang.Object implements Executioner {
    private int maxGuesses_;
    private int currentGuesses_;
    private char invalidChar_;
    private java.util.Collection<java.lang.String> words_;
    private java.util.Collection<java.lang.Character> lettersGuessed_;
    private String secretWord_;

    ConsoleExecutioner(ConsoleInterface ui){
        secretWord_ = "ui.selectSecretWord();";
    }

    @Override
    public void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar){
        maxGuesses_ = maxIncorrectGuesses;
        invalidChar_ = invalidChar;
        words_ = words;
        currentGuesses_ = 0;
        lettersGuessed_ = new java.util.ArrayList<>();
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
            if (guessedLetters().contains(currentChar)){
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
        currentGuesses_++;
        for (int i = 0; i < secretWord_.length();i++){
            if (secretWord_.charAt(i) == letter){
                nbTimesCharAppears++;
            }
        }

        for (String word : words_) {
            if (!word.contains(String.valueOf(letter))) {
                words_.remove(word);
            }
        }
        return nbTimesCharAppears;
    }

    java.lang.String revealSecretWord(){
        return secretWord_.toUpperCase();
    }

    public boolean isGameOver(){
        return (guessedTheWord() && maxGuesses_ < currentGuesses_);
    }

    private boolean guessedTheWord(){
        boolean bool = true;
        for (int i =0;i < secretWord_.length(); i++){
            if (!lettersGuessed_.contains(secretWord_.charAt(i))){
                bool = false;
            }
        }
        return bool;
    }

}
