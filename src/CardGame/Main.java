package CardGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.stage.*;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import javafx.animation.*;

public class Main extends Application {
    private static Group rootNode_;
    private static Scene primaryScene_;
    
    private static GraphicsContext mainRenderContext_;
    private static Canvas mainCanvas_;
    
    private static GameController gameController_;
    
    //----------------------------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        //rootNode_ = FXMLLoader.load(getClass().getResource("CardGame.fxml"));
        rootNode_ = new Group();
        primaryScene_ = new Scene(rootNode_, 800, 600);
    
        mainCanvas_ = new Canvas( 800, 600);
        rootNode_.getChildren().add(mainCanvas_);
    
        mainRenderContext_ = mainCanvas_.getGraphicsContext2D();
    
        gameController_ = new GameController();
        
        primaryStage.setTitle("The Black Moon Howls from Beyond the End of Time");
        primaryStage.setScene(primaryScene_);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Group GetRoot() { return rootNode_; }
    public static Scene GetPrimaryScene() { return primaryScene_; }
    public static GraphicsContext GetGC() { return mainRenderContext_; }
    public static Canvas GetCanvas() { return mainCanvas_; }
    public static GameController GetGameController() { return gameController_; }
}
