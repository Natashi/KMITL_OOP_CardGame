package CardGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CardGame.fxml"));
        
        Scene primaryScene = new Scene(root, 1280, 720);
        
        primaryStage.setTitle("The Black Moon Howls from Beyond the End of Time");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
