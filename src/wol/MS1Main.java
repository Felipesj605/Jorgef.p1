/*
This abstract class provides the main method for milestone 1.
Made by @Rene Bourdeth and @Felipe Jorge
 */
package wol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class MS1Main {
    //This method sets up and starts a console-based game of wheel of lies.
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            PrintStream out = System.out;
            Collection<String> words = loadWordsFromFile();
            Lexicon lexicon = new Lexicon(words);
            ConsoleInterface ui = new ConsoleInterface(scanner, out);
            ConsoleExecutioner executioner = new ConsoleExecutioner(ui);
            char invalidCharacter = '*';

            WheelOfLies wheelOfLies = new WheelOfLies(lexicon, ui, executioner, invalidCharacter);
            wheelOfLies.playGame();
            
        } catch (FileNotFoundException e) {
            System.err.println("error:" + e.getMessage());
        } catch (Exception e) {
            System.err.println( "error: " + e.getMessage());
        }
    }

    // reads and stores words from txt file
    private static Collection<String> loadWordsFromFile() throws FileNotFoundException {
        Collection<String> words = new ArrayList<>();
        File file = new File("resources/lexicon.txt");
        Scanner fileScanner = new Scanner(file);
        
        while (fileScanner.hasNextLine()) {
            String word = fileScanner.nextLine().trim();
            if (!word.isEmpty()) {
                words.add(word.toLowerCase());
            }
        }
        
        fileScanner.close();
        return words;
    }
}