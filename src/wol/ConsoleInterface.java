package wol;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

public class ConsoleInterface {
    private Scanner scanner;
    private PrintStream out;
    
    public ConsoleInterface(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    //Prompt the user for the number of misses the player is allowed in the game.
    public int askForMaximumMisses() {
        int input = 0;
        while(input <= 0){
            out.print("Enter the maximum number of incorrect guesses allowed: ");
            input = scanner.nextInt();
        }
        return input;
    }

    //Asks the user to input a word length for the game.
    public int askForWordLength(Lexicon lexicon) {
        int length = 0;
        boolean validLength = false;

        while (!validLength) {
            out.print("Enter the desired word length: ");
            length = scanner.nextInt();
            scanner.nextLine();

            if (length <= 0) {
                out.println("Invalid length. Please enter a positive number.");
                continue;
            }

            // Check if words of this length exist in the lexicon
            Collection<String> wordsOfLength = lexicon.wordsOfLength(length);
            if (wordsOfLength.isEmpty()) {
                out.println("No words of length " + length + " exist in the lexicon. Please try a different length.");
                continue;
            }

            validLength = true;
        }

        return length;
    }

    //Prompt the user to guess a letter. Reprompt for invalid input or letters that have already been guessed.
    public char askNextGuess(Executioner executioner) {
        char guess = ' ';
        boolean validGuess = false;
        
        while (!validGuess) {
            out.print("Enter your next guess: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                out.println("Please enter a letter.");
                continue;
            }
            guess = Character.toUpperCase(input.charAt(0));
            if (!Character.isLetter(guess)) {
                out.println("Please enter a valid letter (a-z or A-Z).");
                continue;
            }
            if (executioner.guessedLetters().contains(guess)) {
                out.println("You have already guessed the letter '" + guess + "'. Please try a different letter.");
                continue;
            }
            
            validGuess = true;
        }
        
        return guess;
    }

    //Prompt the user to see if they want a running total of the number of possible words to be displayed during play.
    public boolean askToDisplayWordCount(){
        out.print("Do you want a total of the number of possible words? (y/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    public void displayGameOver(java.lang.String secretWord, boolean playerWins){
        if (playerWins) {
            out.println("Congratulations! You won! The secret word was: " + secretWord);
        } else {
            out.println("Game over! You lost. The secret word was: " + secretWord);
        }
    }

    public void displayGameState(Executioner executioner, boolean displayWordCount){
        out.println("Misses remaining: " + executioner.incorrectGuessesRemaining());

        out.print("Guessed letters: ");
        Collection<Character> guessedLetters = executioner.guessedLetters();
        if (guessedLetters.isEmpty()) {
            out.println("(none)");
        } else {
            for (Character letter : guessedLetters) {
                out.print(letter + " ");
            }
            out.println();
        }
        
        // Display current state of secret word
        ConsoleExecutioner consoleExecutioner = (ConsoleExecutioner) executioner;
        out.println("Secret word: " + consoleExecutioner.formattedSecretWord());
        
        // Display word count if requested
        if (displayWordCount) {
            out.println("Number of possible words: " + consoleExecutioner.countOfPossibleWords());
        }
    }

    public void displayResultsOfGuess(char guess, int occurrences){
        if (occurrences > 0) {
            out.println("Good guess! '" + guess + "' appears " + occurrences + " time(s) in the word.");
        } else {
            out.println("Sorry, '" + guess + "' is not in the word.");
        }
    }

    //ASk if the player would like another game
    public boolean playAgain(){
        out.print("Would you like to play again? (y/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    public java.lang.String selectSecretWord(java.util.Collection<java.lang.String> secretWords){
        String selectedWord = " ";
        boolean validWord = false;
        
        // First time: print all possible words
        out.println("Available words:");
        for (String word : secretWords) {
            out.println(word);
        }
        
        while (!validWord) {
            out.print("Enter the word you want to use: ");
            selectedWord = scanner.nextLine().trim();
            
            // Check if the entered word is in the collection
            if (secretWords.contains(selectedWord)) {
                validWord = true;
            } else {
                out.println("Invalid word. Please enter one of the words from the list.");
            }
        }
        
        return selectedWord;
    }

}
