package CardGame;

import CardGame.Engine.*;
import CardGame.Game.*;

import javafx.application.Platform;
import javafx.fxml.*;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import javafx.animation.*;
import javafx.util.Duration;

import javafx.event.*;

import java.net.*;
import java.util.*;

import javax.swing.*;

public class GameController {
	private UserSceneManager sceneManager_;
	private InputManager inputManager_;
	
	private Random rand_;

	//----------------------------------------------

	static final void ExitError(String s) {
		JOptionPane.showMessageDialog(null, s,
				"Engine Error", JOptionPane.ERROR_MESSAGE);

		Platform.exit();
		System.exit(-1);
	}

	public GameController() {
		try {
			sceneManager_ = new UserSceneManager();
			sceneManager_.Initialize();
			inputManager_ = new InputManager();
			inputManager_.Initialize(Main.GetPrimaryScene());
			
			var mainGameScene = new BoardScene(sceneManager_);
			sceneManager_.AddScene(mainGameScene, UserScene.Type.Stage.ordinal());
		} catch (Exception e) {
			GameController.ExitError(e.getMessage());
			e.printStackTrace();
		}
		
		rand_ = new Random(System.currentTimeMillis());
		
		_GameLoop(60);
	}
	
	public UserSceneManager GetSceneManager() { return sceneManager_; }
	public InputManager GetInputManager() { return inputManager_; }
	public Random GetRand() { return rand_; }
	
	private void _GameLoop(int fps) {
		var loopEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				try {
					Main.GetGC().clearRect(0, 0, 800, 600);
					sceneManager_.Update();
					sceneManager_.Render();
				} catch (Exception e) {
					GameController.ExitError(e.getMessage());
					e.printStackTrace();
				}
			}
		};
		
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		final long startNs = System.nanoTime();
		KeyFrame kf = new KeyFrame(Duration.seconds(1.0 / fps), loopEvent);
		
		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();
	}
}
