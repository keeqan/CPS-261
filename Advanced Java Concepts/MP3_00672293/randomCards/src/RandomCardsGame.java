import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Collections;
import java.util.ArrayList;

public class RandomCardsGame extends Application {

    private static final int NUM_CARDS = 52; 
    private ArrayList<Image> cardImages; //  store images of the cards
    private Image cardBackImage; // Image back side of the card
    private HBox cardBox; // display cards.

    @Override
    public void start(Stage primaryStage) {
        loadCardImages(); 
        cardBackImage = new Image("file:G:/heyee/card/backCard.png"); 
        cardBox = new HBox(10);

        // Button to refresh cards
        Button refreshButton = new Button("Refresh"); 
        
        refreshButton.setOnAction(e -> dealCards()); // Set action to deal cards on button click.

        HBox rootPane = new HBox(20, cardBox, refreshButton); // Root layout with horizontal box.

        Scene scene = new Scene(rootPane); // Create scene 
        primaryStage.setScene(scene); // Set scene 
        primaryStage.show();  //Set show

        dealCards(); // Deal cards initially.
    }

    // Method to load card images 
    private void loadCardImages() {
        cardImages = new ArrayList<>();
        for (int i = 1; i <= NUM_CARDS; i++) {
            Image image = new Image("file:G:/heyee/card/" + i + ".png"); // Load each card image
            cardImages.add(image); // Add to list
        }
    }

    // shuffle and display cards.
    private void dealCards() {
        Collections.shuffle(cardImages); // Shuffle
        cardBox.getChildren().clear(); // Clear 

        for (int i = 0; i < 4; i++) { // Display first 4 cards.
            ImageView cardView = new ImageView(cardBackImage); // Display with card back image.
            cardView.setFitHeight(100); // height 
            cardView.setFitWidth(70); // width 
            cardView.setPreserveRatio(true); 
            final int cardIndex = i;
            cardView.setOnMouseClicked(e -> flipCard(cardView, cardIndex)); //  flip card on click.
            cardBox.getChildren().add(cardView); // Add card 
        }
    }

    // Method to flip the card 
    private void flipCard(ImageView cardView, int cardIndex) {
        cardView.setImage(cardImages.get(cardIndex)); 
    }

   
    public static void main(String[] args) {
        launch(args);
    }
}