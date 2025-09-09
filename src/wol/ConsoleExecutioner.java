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
}
