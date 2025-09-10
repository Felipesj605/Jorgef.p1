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

    //WIP
    public char askNextGuess(Executioner executioner) {
        out.print("Enter your next guess: ");
        String input = scanner.nextLine().trim();
        if (input.length() > 0) {
            return input.charAt(0);
        }
        return ' ';
    }

    //Prompt the user to see if they want a running total of the number of possible words to be displayed during play.
    public boolean askToDisplayWordCount(){
        out.print("Do you want a total of the number of possible words? (y/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    //ASk if the player would like another game
    public boolean playAgain(){
        out.print("Would you like to play again? (y/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }




}
