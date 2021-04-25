package CardGame.Engine;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.*;

public class InputManager {
	private static InputManager base_ = null;
	
	private static Scene scene_;
	private static HashSet<KeyCode> keyMap_;
	
	//----------------------------------------------
	
	public InputManager() {
		scene_ = null;
	}
	
	public void Initialize(Scene scene) throws EngineError {
		if (base_ != null) throw new EngineError("InputManager already initialized.");
		base_ = this;
		
		scene_ = scene;
		keyMap_ = new HashSet<>();
	}
	
	public static InputManager GetBase() {
		return base_;
	}
	
	public boolean IsKeyDown(KeyCode keyCode) {
		return keyMap_.contains(keyCode);
	}
	private void ResetState() {
		keyMap_.clear();
	}
	
	private void ResetCallbacks() {
		if (scene_ != null) {
			scene_.setOnKeyPressed(null);
			scene_.setOnKeyReleased(null);
		}
	}
	private void SetCallbacks() {
		scene_.setOnKeyPressed(keyEvent -> { keyMap_.add(keyEvent.getCode()); });
		scene_.setOnKeyReleased(keyEvent -> { keyMap_.remove(keyEvent.getCode()); });
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Scene={").append(scene_).append("},Keys={");
		for (KeyCode code : keyMap_)
			str.append(code.getName()).append(',');
		return str.append('}').toString();
	}
}
