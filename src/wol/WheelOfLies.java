package wol;

public class WheelOfLies extends java.lang.Object {
    private Lexicon lexicon_;
    private ConsoleInterface consoleInterface_;
    private Executioner executioner_;
    private char invalidChar_;

    WheelOfLies(Lexicon lexicon, ConsoleInterface ui, Executioner executioner, char invalidCharacter){
        lexicon_ = lexicon;
        consoleInterface_ = ui;
        executioner_ = executioner;
        invalidChar_ = invalidCharacter;
    }

    //Game loop
    public void playGame(){
        boolean playAgain = true;
        
        while (playAgain) {
            int wordLength = consoleInterface_.askForWordLength(lexicon_);
            int maxMisses = consoleInterface_.askForMaximumMisses();
            boolean displayWordCount = consoleInterface_.askToDisplayWordCount();
            java.util.Collection<String> wordsOfLength = lexicon_.wordsOfLength(wordLength);
            executioner_.newGame(wordsOfLength, maxMisses, invalidChar_);

            boolean gameOver = false;
            boolean playerWins = false;
            
            while (!gameOver) {
                consoleInterface_.displayGameState(executioner_, displayWordCount);
                char guess = consoleInterface_.askNextGuess(executioner_);
                int occurrences = ((ConsoleExecutioner) executioner_).registerAGuess(guess);
                consoleInterface_.displayResultsOfGuess(guess, occurrences);
                gameOver = ((ConsoleExecutioner) executioner_).isGameOver();

                if (gameOver) {
                    String secretWord = ((ConsoleExecutioner) executioner_).revealSecretWord();
                    String formattedWord = ((ConsoleExecutioner) executioner_).formattedSecretWord();
                    playerWins = formattedWord.equals(secretWord);
                }
            }
            String secretWord = ((ConsoleExecutioner) executioner_).revealSecretWord();
            consoleInterface_.displayGameOver(secretWord, playerWins);
            playAgain = consoleInterface_.playAgain();
        }
    }

}
