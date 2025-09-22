/*
Provides an interface to an executioner for the wheel of lies game.
Made by @Rene Bourdeth and @Felipe Jorge
 */
package wol;

public interface Executioner {
    //Prepares the executioner for a new game with the given collection of potential secret words.
    void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar);

    //Returns the number of incorrect letter guesses the condemned can make before losing.
    int incorrectGuessesRemaining();

    //Returns a collection of the letters the condemned has already guessed.
    java.util.Collection<java.lang.Character> guessedLetters();

    //Registers the condemned next guess.
    int registerAGuess(char letter);

    //Returns true if the game is over.
    boolean isGameOver();

    //Return the secret word. The word should be in upper case.
    String revealSecretWord();

    //Return a specially formatted version of the secret word.
    String formattedSecretWord();

    //Returns the count of words that could still be the secret word.
    int countOfPossibleWords();

    //Returns true if the given letter has already been guessed by the condemned.
    boolean letterAlreadyGuessed(char letter);
}
