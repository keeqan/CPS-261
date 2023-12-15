import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.geometry.Insets;

public class Main extends Application { // Main class for the Game of Pig application
    private Stage primaryStage;
    private Scene mainScene;
    private GameLogic gameLogic;
    private ImageView diceImageView;
    private Label playerScoreLabel;
    private Label opponentScoreLabel; 
    private Label currentRollLabel;
    private Label turnLabel;
    private Button rollButton;
    private Button holdButton;
    private Button btnPlayerVsPlayer;
    private Button btnPlayerVsComputer;

    @Override
    public void start(Stage primaryStage) { //Initializing primary stage and setting up initial UI components

        this.primaryStage = primaryStage;
        gameLogic = new GameLogic();
        btnPlayerVsPlayer = new Button("Player vs Player");
        btnPlayerVsComputer = new Button("Player vs Computer");
        btnPlayerVsPlayer.setOnAction(event -> startGameMode(false));
        btnPlayerVsComputer.setOnAction(event -> startGameMode(true));
        VBox mainLayout = new VBox(10);
        Image backgroundImage = new Image("file:K:\\farm.png");
        BackgroundImage background = new BackgroundImage(
        backgroundImage,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        mainLayout.setBackground(new Background(background));
        mainLayout.setPadding(new Insets(10)); // Add padding to the VBox   
        mainLayout.getChildren().addAll(btnPlayerVsPlayer, btnPlayerVsComputer);
        mainScene = new Scene(mainLayout, 400, 200);
        primaryStage.setTitle("Game of Pig"); // Setting scene and showing primary stage
        primaryStage.setScene(mainScene);
        primaryStage.show();
        Button btnViewHistory = new Button("View History");
        btnViewHistory.setOnAction(event -> viewHistory());
        mainLayout.getChildren().add(btnViewHistory);
    }

    private void startGameMode(boolean isVsComputer) {
        gameLogic.setVsComputer(isVsComputer); // Configures the game mode and initializes a new game
        promptForPlayerNames();
        startNewGame();
    }

    private void startNewGame() { // Resets the game state and initializes UI components for the game
        System.out.println("Starting a new game");
        gameLogic.resetGame();
        playerScoreLabel = new Label("Player Score: 0");
        opponentScoreLabel = new Label("Computer Score: 0");
        currentRollLabel = new Label("Current Turn Sum: 0");
        turnLabel = new Label("Turn: Player");
        diceImageView = new ImageView();
        diceImageView.setFitWidth(50);
        diceImageView.setFitHeight(50);
        rollButton = new Button("Roll Dice");
        holdButton = new Button("Hold");

        rollButton.setOnAction(event -> { // Handle game progress after rolling
            int roll = gameLogic.rollDice();
            updateDiceImage(roll);
            currentRollLabel.setText("Current Turn Sum: " + gameLogic.getCurrentTurnScore());
            updateScores();     // Handle game progress after rolling
            if (!gameLogic.isPlayerTurn() && gameLogic.hasWinner()) {
                handleGameProgress();
            } else if (!gameLogic.isPlayerTurn()) {
                switchTurn(); // Switch turn if it's not the current player's turn anymore
            }
            updateButtonStates();
        });
        
        holdButton.setOnAction(event -> { // Handle game progress after holding
            gameLogic.hold();
            updateScores();
            if (!gameLogic.isPlayerTurn()) {
                switchTurn(); // Switch turn after holding
                handleGameProgress();
            }
            updateButtonStates();
        });

        // Create an HBox to hold the buttons 
        HBox buttonContainer = new HBox(10); 
        buttonContainer.getChildren().addAll(rollButton, holdButton);
        buttonContainer.setAlignment(Pos.CENTER); // Center the buttons 
        VBox gameLayout = new VBox(10);
        gameLayout.getChildren().addAll(playerScoreLabel, opponentScoreLabel, turnLabel, currentRollLabel, diceImageView, buttonContainer);
        updateButtonStates(); // Call this after buttons are initialized
        Scene gameScene = new Scene(gameLayout, 400, 300);
        primaryStage.setScene(gameScene);
    }

    private void updateScores() {
        playerScoreLabel.setText(gameLogic.getPlayerName() + " Score: " + gameLogic.getPlayerScore());
        String opponentLabel = gameLogic.isVsComputer() ? "Computer Score: " : gameLogic.getOpponentName() + " Score: ";
        opponentScoreLabel.setText(opponentLabel + gameLogic.getOpponentScore());
    }

    private void updateDiceImage(int roll) { // Pulls dice images from the dice folder and updates the dice image
        String imagePath = "file:K:/Advanced Java Concepts/MP3_00672293/hockeyStatsBar/src/dice/dice" + roll + ".png";
        Image diceImage = new Image(imagePath);
        diceImageView.setImage(diceImage);
    }

    private void handleGameProgress() { // Handles the game progress after a turn
        if (gameLogic.hasWinner()) {
            String winner = gameLogic.getWinner();
            System.out.println("Game Over. Winner: " + winner);
            saveGameRecord(winner);
            showAlert("Game Over", winner + " has won the game!");
            return;
        }
        if (!gameLogic.isPlayerTurn() && gameLogic.isVsComputer()) {
            handleComputerTurn();
        }
    }

    private void handleComputerTurn() { // Manages the computer's turn with a pause and automatic actions

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            if (!gameLogic.isPlayerTurn()) {
                gameLogic.takeComputerTurn();
                updateDiceImage(gameLogic.getLastRoll());
                currentRollLabel.setText("Current Turn Sum: " + gameLogic.getCurrentTurnScore());
                updateScores();
                if (gameLogic.isPlayerTurn()) {
                    switchTurn();
                }
                handleGameProgress();
                updateButtonStates();
            }
        });
        pause.play();
    }

    private void switchTurn() { // Switches the turn between players and updates UI accordingly
        String currentTurn = gameLogic.isPlayerTurn() ? gameLogic.getPlayerName() : gameLogic.getOpponentName();
        turnLabel.setText("Turn: " + currentTurn);
        updateButtonStates();
    }

    private void updateButtonStates() {
        if (gameLogic.isVsComputer()) {  // In Player vs Computer mode: enable buttons only when it's the player's turn
            boolean isPlayerTurn = gameLogic.isPlayerTurn();
            rollButton.setDisable(!isPlayerTurn);
            holdButton.setDisable(!isPlayerTurn);
        } else {
            rollButton.setDisable(false); // In Player vs Player mode: always enable buttons
            holdButton.setDisable(false);
        }
    }

    private void showAlert(String title, String message) {  // Shows an alert dialog
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            primaryStage.setScene(mainScene);
        });
    }

    private void promptForPlayerNames() { // Use JavaFX Dialogs or TextInputDialog to get player names
    TextInputDialog dialog = new TextInputDialog("Player 1");
    dialog.setTitle("Player Name");
    dialog.setHeaderText("Enter Player 1's Name:");
    Optional<String> result = dialog.showAndWait();
    result.ifPresent(name -> gameLogic.setPlayerName(name));

    if (gameLogic.isVsComputer()) {
        gameLogic.setOpponentName("Computer");
    } else {
        dialog.setHeaderText("Enter Player 2's Name:");
        result = dialog.showAndWait();
        result.ifPresent(name -> gameLogic.setOpponentName(name));
    }
}
 
    private void saveGameRecord(String winner) { // Saves the game record to the file
        try {
            GameRecord record = new GameRecord();
            record.setName(winner);
            record.setDate(LocalDateTime.now());
            record.setScore(winner.equals("Player") ? gameLogic.getPlayerScore() : gameLogic.getOpponentScore());
            record.setResult("Win");
            FileUtility.saveRecord(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewHistory() { // Views the history of past games from saved records
    TableView<GameRecord> historyTable = new TableView<>();
    setupHistoryTable(historyTable);
    VBox totalWinsLayout = new VBox(10); // Layout for displaying total wins

    try {
        List<GameRecord> records = FileUtility.loadRecordsFromFile();
        historyTable.getItems().addAll(records);
        Map<String, Long> totalWins = records.stream() // Calculate and display the total wins for each player
                                             .filter(r -> "Win".equals(r.getResult()))
                                             .collect(Collectors.groupingBy(GameRecord::getName, Collectors.counting()));
        totalWins.forEach((playerName, wins) -> {
            Label winLabel = new Label(playerName + " has won " + wins + " times.");
            totalWinsLayout.getChildren().add(winLabel); // Add the label to the layout
        });
    } catch (IOException e) {
        e.printStackTrace(); // Or handle the error as appropriate
    }

    VBox historyLayout = new VBox(10);
    historyLayout.getChildren().addAll(historyTable, totalWinsLayout);
    Scene historyScene = new Scene(historyLayout, 600, 450);
    primaryStage.setScene(historyScene);
}
    private void setupHistoryTable(TableView<GameRecord> table) { // Configures the table view for displaying game history
        TableColumn<GameRecord, String> nameColumn = new TableColumn<>("Player Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<GameRecord, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<GameRecord, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<GameRecord, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        table.getColumns().addAll(List.of(nameColumn, dateColumn, scoreColumn, resultColumn));
    }

    public static void main(String[] args) {
        launch(args);
    }
}