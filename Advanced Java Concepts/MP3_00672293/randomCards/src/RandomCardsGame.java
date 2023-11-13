import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Collections;
import java.util.ArrayList;

// This class defines a JavaFX application for a random cards game.
public class RandomCardsGame extends Application {

    private static final int NUM_CARDS = 52; // Constant for the number of cards.
    private ArrayList<Image> cardImages; // List to store images of the cards.
    private Image cardBackImage; // Image for the back side of the card.
    private HBox cardBox; // Horizontal box to display cards.

    @Override
    public void start(Stage primaryStage) {
        loadCardImages(); // Load images of the cards.
        cardBackImage = new Image("file:G:/heyee/card/backCard.png"); // Load back of the card image.
        cardBox = new HBox(10); // Horizontal box with spacing of 10 pixels.

        Button refreshButton = new Button("Refresh"); // Button to refresh cards.
        refreshButton.setOnAction(e -> dealCards()); // Set action to deal cards on button click.

        HBox rootPane = new HBox(20, cardBox, refreshButton); // Root layout with horizontal box.

        Scene scene = new Scene(rootPane); // Create scene with root layout.
        primaryStage.setScene(scene); // Set scene on the primary stage.
        primaryStage.setTitle("Random Cards Game"); // Set title of the window.
        primaryStage.show(); // Display the stage.

        dealCards(); // Deal cards initially.
    }

    // Method to load card images into the list.
    private void loadCardImages() {
        cardImages = new ArrayList<>();
        for (int i = 1; i <= NUM_CARDS; i++) {
            Image image = new Image("file:G:/heyee/card/" + i + ".png"); // Load each card image.
            cardImages.add(image); // Add to card images list.
        }
    }

    // Method to shuffle and display cards.
    private void dealCards() {
        Collections.shuffle(cardImages); // Shuffle the card images.
        cardBox.getChildren().clear(); // Clear existing cards from the box.

        for (int i = 0; i < 4; i++) { // Display first 4 cards.
            ImageView cardView = new ImageView(cardBackImage); // Display with card back image.
            cardView.setFitHeight(100); // Set height of the card.
            cardView.setFitWidth(70); // Set width of the card.
            cardView.setPreserveRatio(true); // Preserve aspect ratio.
            final int cardIndex = i;
            cardView.setOnMouseClicked(e -> flipCard(cardView, cardIndex)); // Set action to flip card on click.
            cardBox.getChildren().add(cardView); // Add card to the display box.
        }
    }

    // Method to flip the card to show its front side.
    private void flipCard(ImageView cardView, int cardIndex) {
        cardView.setImage(cardImages.get(cardIndex)); // Set image to the actual card image.
    }

    // Main method to launch the application.
    public static void main(String[] args) {
        launch(args);
    }
}