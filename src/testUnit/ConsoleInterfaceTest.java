package testUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wol.ConsoleExecutioner;
import wol.ConsoleInterface;
import wol.Lexicon;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ConsoleInterfaceTest {
    private ConsoleInterface consoleInterface;
    private ByteArrayOutputStream outputStream;
    private Lexicon testLexicon;
    private ConsoleExecutioner mockExecutioner;

    @BeforeEach
    void setUp() {
        // Create test lexicon
        Collection<String> testWords = new ArrayList<>();
        testWords.add("cat");
        testWords.add("dog");
        testWords.add("hello");
        testWords.add("world");
        testWords.add("test");
        testWords.add("zombifying");
        testLexicon = new Lexicon(testWords);
        
        // Create mock executioner
        String testInput = "test\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        consoleInterface = new ConsoleInterface(scanner, printStream);
        
        mockExecutioner = new ConsoleExecutioner(consoleInterface);
    }

    @Test
    void testConstructor() {
        assertNotNull(consoleInterface);
    }

    @Test
    void testAskForMaximumMisses_ValidInput() {
        String input = "5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        int result = testInterface.askForMaximumMisses();
        
        assertEquals(5, result);
        assertTrue(outputStream.toString().contains("Enter the maximum number of incorrect guesses allowed"));
    }

    @Test
    void testAskForMaximumMisses_InvalidInputThenValid() {
        String input = "0\n-1\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        int result = testInterface.askForMaximumMisses();
        
        assertEquals(3, result);
    }

    @Test
    void testAskForWordLength_ValidLength() {
        String input = "3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        int result = testInterface.askForWordLength(testLexicon);
        
        assertEquals(3, result);
        assertTrue(outputStream.toString().contains("Enter the desired word length"));
    }

    @Test
    void testAskForWordLength_InvalidLengthThenValid() {
        String input = "0\n-1\n1\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        int result = testInterface.askForWordLength(testLexicon);
        
        assertEquals(3, result);
        assertTrue(outputStream.toString().contains("Invalid length"));
        assertTrue(outputStream.toString().contains("No words of length 1 exist"));
    }

    @Test
    void testAskForWordLength_NonExistentLength() {
        String input = "99\n3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        int result = testInterface.askForWordLength(testLexicon);
        
        assertEquals(3, result);
        assertTrue(outputStream.toString().contains("No words of length 99 exist"));
    }

    @Test
    void testAskNextGuess_ValidLetter() {
        String input = "a\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        char result = testInterface.askNextGuess(mockExecutioner);
        
        assertEquals('A', result);
        assertTrue(outputStream.toString().contains("Enter your next guess"));
    }

    @Test
    void testAskNextGuess_InvalidInputThenValid() {
        String input = "123\n@\na\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        char result = testInterface.askNextGuess(mockExecutioner);
        
        assertEquals('A', result);
        assertTrue(outputStream.toString().contains("Please enter a valid letter"));
    }

    @Test
    void testAskNextGuess_EmptyInputThenValid() {
        String input = "\na\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        char result = testInterface.askNextGuess(mockExecutioner);
        
        assertEquals('A', result);
        assertTrue(outputStream.toString().contains("Please enter a letter"));
    }

    @Test
    void testAskToDisplayWordCount_Yes() {
        String input = "y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        boolean result = testInterface.askToDisplayWordCount();
        
        assertTrue(result);
        assertTrue(outputStream.toString().contains("Do you want a total of the number of possible words"));
    }

    @Test
    void testAskToDisplayWordCount_No() {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        boolean result = testInterface.askToDisplayWordCount();
        
        assertFalse(result);
    }

    @Test
    void testAskToDisplayWordCount_InvalidInputThenValid() {
        String input = "maybe\nyes\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        boolean result = testInterface.askToDisplayWordCount();
        
        assertFalse(result);
        assertTrue(outputStream.toString().contains("enter 'y' for yes or 'n' for no"));
    }

    @Test
    void testDisplayGameOver_PlayerWins() {
        consoleInterface.displayGameOver("TEST", true);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Congratulations! You won!"));
        assertTrue(output.contains("TEST"));
    }

    @Test
    void testDisplayGameOver_PlayerLoses() {
        consoleInterface.displayGameOver("TEST", false);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Game over! You lost."));
        assertTrue(output.contains("TEST"));
    }

    @Test
    void testDisplayGameState() {
        // This test requires a mock executioner with specific state
        // We'll test the method exists and can be called
        assertDoesNotThrow(() -> {
            consoleInterface.displayGameState(mockExecutioner, true);
        });
    }

    @Test
    void testDisplayResultsOfGuess_GoodGuess() {
        consoleInterface.displayResultsOfGuess('A', 2);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Good guess!"));
        assertTrue(output.contains("'A' appears 2 time(s)"));
    }

    @Test
    void testDisplayResultsOfGuess_BadGuess() {
        consoleInterface.displayResultsOfGuess('X', 0);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Sorry, 'X' is not in the word"));
    }

    @Test
    void testPlayAgain_Yes() {
        String input = "y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        boolean result = testInterface.playAgain();
        
        assertTrue(result);
        assertTrue(outputStream.toString().contains("Would you like to play again"));
    }

    @Test
    void testPlayAgain_No() {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        boolean result = testInterface.playAgain();
        
        assertFalse(result);
    }

    @Test
    void testSelectSecretWord() {
        Collection<String> secretWords = new ArrayList<>();
        secretWords.add("test");
        secretWords.add("best");
        secretWords.add("rest");
        
        String input = "test\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        String result = testInterface.selectSecretWord(secretWords);
        
        assertEquals("test", result);
        assertTrue(outputStream.toString().contains("Available words"));
        assertTrue(outputStream.toString().contains("Enter the word you want to use"));
    }

    @Test
    void testSelectSecretWord_InvalidWordThenValid() {
        Collection<String> secretWords = new ArrayList<>();
        secretWords.add("test");
        secretWords.add("best");
        secretWords.add("rest");
        
        String input = "invalid\ntest\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ConsoleInterface testInterface = new ConsoleInterface(scanner, printStream);
        
        String result = testInterface.selectSecretWord(secretWords);
        
        assertEquals("test", result);
        assertTrue(outputStream.toString().contains("Invalid word"));
    }
}
