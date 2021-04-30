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

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class GameController {
	private UserSceneManager sceneManager_;
	private ResourceManager resourceManager_;
	private InputManager inputManager_;
	
	private Rand rand_;

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
			resourceManager_ = new ResourceManager();
			resourceManager_.Initialize();
			inputManager_ = new InputManager();
			inputManager_.Initialize(Main.GetPrimaryScene());
			
			var mainGameScene = new BoardScene(sceneManager_);
			sceneManager_.AddScene(mainGameScene, UserScene.Type.Stage.ordinal());
		} catch (Exception e) {
			StringWriter str = new StringWriter();
			e.printStackTrace(new PrintWriter(str));
			GameController.ExitError(e.getMessage() + "\n\n" + str.toString());
		}
		
		rand_ = new Rand();
		
		_GameLoop(60);
	}
	
	public UserSceneManager GetSceneManager() { return sceneManager_; }
	public InputManager GetInputManager() { return inputManager_; }
	public Rand GetRand() { return rand_; }
	
	private void _GameLoop(int fps) {
		new FrameTimer() {
			@Override
			public void Loop(ActionEvent actionEvent) {
				try {
					Main.GetGC().clearRect(0, 0, 800, 600);
					sceneManager_.Update();
					sceneManager_.Render();
				} catch (Exception e) {
					StringWriter str = new StringWriter();
					e.printStackTrace(new PrintWriter(str));
					GameController.ExitError(e.getMessage() + "\n\n" + str.toString());
					
					Stop();
				}
			}
		};
	}
}
