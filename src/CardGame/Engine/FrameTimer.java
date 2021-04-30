package CardGame.Engine;

import CardGame.GameController;
import CardGame.Main;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class FrameTimer {
	protected Timeline timeline_;
	public int frame_;
	public int duration_;
	
	public FrameTimer() {
		this(Timeline.INDEFINITE);		//Infinite loop
	}
	public FrameTimer(int count) {
		frame_ = 0;
		duration_ = count;
		
		timeline_ = new Timeline();
		timeline_.setCycleCount(count);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1.0 / 60), this::Loop);
		
		timeline_.getKeyFrames().add(kf);
		timeline_.play();
	}
	
	public void Start() { }
	public void Loop(ActionEvent actionEvent) { }
	public void Stop() {
		timeline_.stop();
	}
}
