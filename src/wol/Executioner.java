package wol;

public interface Executioner {
    void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar);
    int incorrectGuessesRemaining();
    java.util.Collection<java.lang.Character> guessedLetters();
}
