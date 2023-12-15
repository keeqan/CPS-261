import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class GameLogic { // Game logic class
    private int playerScore;
    private int lastRoll; // Field to store the last dice roll
    private int opponentScore;
    private int currentTurnScore;
    private boolean playerTurn; // true if it's the player's turn, false for computer's turn
    private Random random;
    private boolean vsComputer;
    private String playerName;
    private String opponentName; // This can be either another player or the computer

    

    public GameLogic() { // Constructor
        this.playerScore = 0;
        this.opponentScore = 0;
        this.currentTurnScore = 0;
        this.playerTurn = true;
        this.random = new Random();
        this.playerName = "Player 1";
        this.opponentName = "Player 2"; // Default name
    }

    public int rollDice() { // Rolling the dice
        lastRoll = random.nextInt(6) + 1;
        String currentPlayer = playerTurn ? playerName : opponentName;
        if (lastRoll > 1) {
            currentTurnScore += lastRoll;
        } else {
            currentTurnScore = 0;
            endTurn();
            if (!playerTurn && vsComputer) {
                takeComputerTurn(); // Initiating computer's turn if it's now the computer's turn
            }
        }
        return lastRoll;
    }

    public void hold() { // Holding the turn
        if (playerTurn) {
            playerScore += currentTurnScore;
        } else {
            opponentScore += currentTurnScore;
        }
        endTurn();
    }

    void endTurn() { // Ending the turn
        playerTurn = !playerTurn; // Switch turn
        currentTurnScore = 0;
    }

    public void prepareComputerTurn() {
        // This method is called to prepare for the computer's turn.
    }

    public int takeComputerTurn() { // Computer's turn
        int roll;
        do {
            roll = rollDice();
            if (roll == 1) {
                break;
            }
            if (currentTurnScore >= 20) {
                hold();
                break;
            }
        } while (currentTurnScore < 20);
        return roll;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }
    
    public String getOpponentName() {
        return opponentName;
    }
    
    public int getOpponentScore() {
        return opponentScore;
    }

    public int getCurrentTurnScore() {
        return currentTurnScore;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public void setOpponentName(String name) {
        this.opponentName = name;
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public boolean hasWinner() {
        return playerScore >= 100 || opponentScore >= 100;
    }

    public String getWinner() {
        if (playerScore >= 100) {
            return playerName;
        } else if (opponentScore >= 100) {
            return opponentName;
        }
        return "None"; // No winner yet
    }

    public void resetGame() { // Resetting the game
        playerScore = 0;
        opponentScore = 0;
        currentTurnScore = 0;
        playerTurn = true;
    }
    public void saveGameState() {// Saving the game state
    try {
        GameRecord currentState = new GameRecord();
        currentState.setName("CurrentState"); // Use a constant name to indicate this is a game state, not a final record
        currentState.setDate(LocalDateTime.now());
        currentState.setScore(playerScore);
        currentState.setResult(playerTurn ? "PlayerTurn" : "ComputerTurn");
        FileUtility.saveRecord(currentState);
    } catch (IOException e) {
        e.printStackTrace(); // Or handle it more gracefully
    }
}


}