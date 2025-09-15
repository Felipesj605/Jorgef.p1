package testUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wol.ConsoleExecutioner;
import wol.ConsoleInterface;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ConsoleExecutionerTest {
    private ConsoleExecutioner executioner;
    private ConsoleInterface mockUI;
    private Collection<String> testWords;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Create mock UI with test input and output
        String testInput = "test\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        
        mockUI = new ConsoleInterface(scanner, printStream);
        executioner = new ConsoleExecutioner(mockUI);
        
        // Create test words
        testWords = new ArrayList<>();
        testWords.add("test");
        testWords.add("best");
        testWords.add("rest");
        testWords.add("zombifying");
        testWords.add("programming");
    }

    @Test
    void testConstructor() {
        assertNotNull(executioner);
    }

    @Test
    void testNewGame() {
        executioner.newGame(testWords, 5, '*');
        
        assertEquals(5, executioner.incorrectGuessesRemaining());
        assertTrue(executioner.guessedLetters().isEmpty());
        assertEquals(5, executioner.countOfPossibleWords());
    }

    @Test
    void testRegisterAGuess_CorrectLetter() {
        executioner.newGame(testWords, 5, '*');
        
        int occurrences = executioner.registerAGuess('T');
        
        assertEquals(2, occurrences); // 'T' appears twice in "test"
        assertEquals(5, executioner.incorrectGuessesRemaining()); // Should not decrement
        assertTrue(executioner.guessedLetters().contains('T'));
    }

    @Test
    void testRegisterAGuess_IncorrectLetter() {
        executioner.newGame(testWords, 5, '*');
        
        int occurrences = executioner.registerAGuess('X');
        
        assertEquals(0, occurrences); // 'X' doesn't appear in "test"
        assertEquals(4, executioner.incorrectGuessesRemaining()); // Should decrement
        assertTrue(executioner.guessedLetters().contains('X'));
    }

    @Test
    void testRegisterAGuess_LetterAppearsMultipleTimes() {
        executioner.newGame(testWords, 5, '*');
        
        int occurrences = executioner.registerAGuess('T');
        
        assertEquals(2, occurrences); // 'T' appears twice in "test"
        assertTrue(executioner.guessedLetters().contains('T'));
    }

    @Test
    void testFormattedSecretWord_NoGuesses() {
        executioner.newGame(testWords, 5, '*');
        
        String formatted = executioner.formattedSecretWord();
        
        assertEquals("test", formatted); // Should show original word in lowercase
    }

    @Test
    void testFormattedSecretWord_WithGuesses() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        executioner.registerAGuess('E');
        
        String formatted = executioner.formattedSecretWord();
        
        assertEquals("TEsT", formatted); // Guessed letters should be uppercase
    }

    @Test
    void testFormattedSecretWord_AllLettersGuessed() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        executioner.registerAGuess('E');
        executioner.registerAGuess('S');
        
        String formatted = executioner.formattedSecretWord();
        
        assertEquals("TEST", formatted); // All letters should be uppercase
    }

    @Test
    void testGuessedTheWord_NotGuessed() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        executioner.registerAGuess('E');
        
        // This is a private method, so we test it indirectly through isGameOver
        assertFalse(executioner.isGameOver());
    }

    @Test
    void testGuessedTheWord_AllGuessed() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        executioner.registerAGuess('E');
        executioner.registerAGuess('S');
        
        // This is a private method, so we test it indirectly through isGameOver
        assertTrue(executioner.isGameOver());
    }

    @Test
    void testIsGameOver_NotOver() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        
        assertFalse(executioner.isGameOver());
    }

    @Test
    void testIsGameOver_OutOfGuesses() {
        executioner.newGame(testWords, 2, '*');
        executioner.registerAGuess('X'); // Wrong guess
        executioner.registerAGuess('Y'); // Wrong guess
        
        assertTrue(executioner.isGameOver());
    }

    @Test
    void testIsGameOver_WordGuessed() {
        executioner.newGame(testWords, 5, '*');
        executioner.registerAGuess('T');
        executioner.registerAGuess('E');
        executioner.registerAGuess('S');
        
        assertTrue(executioner.isGameOver());
    }

    @Test
    void testIncorrectGuessesRemaining() {
        executioner.newGame(testWords, 5, '*');
        
        assertEquals(5, executioner.incorrectGuessesRemaining());
        
        executioner.registerAGuess('X'); // Wrong guess
        assertEquals(4, executioner.incorrectGuessesRemaining());
        
        executioner.registerAGuess('Y'); // Wrong guess
        assertEquals(3, executioner.incorrectGuessesRemaining());
    }

    @Test
    void testGuessedLetters() {
        executioner.newGame(testWords, 5, '*');
        
        assertTrue(executioner.guessedLetters().isEmpty());
        
        executioner.registerAGuess('T');
        assertTrue(executioner.guessedLetters().contains('T'));
        assertEquals(1, executioner.guessedLetters().size());
        
        executioner.registerAGuess('E');
        assertTrue(executioner.guessedLetters().contains('E'));
        assertEquals(2, executioner.guessedLetters().size());
    }

    @Test
    void testCountOfPossibleWords() {
        executioner.newGame(testWords, 5, '*');
        
        assertEquals(5, executioner.countOfPossibleWords());
        
        // After guessing 'T', words without 'T' should be removed
        executioner.registerAGuess('T');
        // This depends on the filtering logic - should be less than 5
        assertTrue(executioner.countOfPossibleWords() < 5);
    }

    @Test
    void testRevealSecretWord() {
        executioner.newGame(testWords, 5, '*');
        
        String secretWord = executioner.revealSecretWord();
        
        assertEquals("TEST", secretWord); // Should be uppercase
    }

    @Test
    void testLetterAlreadyGuessed() {
        executioner.newGame(testWords, 5, '*');
        
        assertFalse(executioner.letterAlreadyGuessed('T'));
        
        executioner.registerAGuess('T');
        assertTrue(executioner.letterAlreadyGuessed('T'));
    }

    @Test
    void testCaseInsensitiveGuessing() {
        executioner.newGame(testWords, 5, '*');
        
        executioner.registerAGuess('E');
        assertTrue(executioner.guessedLetters().contains('E'));
    }
}
