package CardGame.Game;

import CardGame.Engine.*;
import CardGame.Engine.Math.*;
import CardGame.Main;

import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.io.*;
import javafx.event.*;

public class GameWinLoseText extends UserTaskBase {
	private Text text_;
	private double alpha_;
	
	//----------------------------------------------
	
	public GameWinLoseText(UserScene parent, int result)
	{
		super(parent);
		
		frameEnd_ = 20 + 180 + 40;
		
		text_ = new Text();
		text_.setX(270);
		text_.setY(300);
		text_.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 48));
		if (result > 0) {
			text_.setText("You've won!");
			text_.setFill(Color.LAWNGREEN);
		}
		else if (result < 0) {
			text_.setText("You've lost!");
			text_.setFill(Color.RED);
		}
		else {
			text_.setText("It's a draw!");
			text_.setFill(Color.SILVER);
		}
		
		Main.GetRoot().getChildren().add(text_);
		
		alpha_ = 0;
		text_.setOpacity(0);
	}
	@Override
	public void Dispose() {
		Main.GetRoot().getChildren().remove(text_);
	}
	
	@Override
	public void Render(int layer) throws EngineError {
		text_.setOpacity(alpha_);
	}
	@Override
	public void Update() throws EngineError {
		if (frame_ < 20) {
			double tmp = frame_ / 19.0;
			text_.setY(300 + Interpolate.Smooth(48.0, 0, tmp));
			alpha_ = tmp;
		}
		else if (frame_ >= 20 + 180) {
			double tmp = (frame_ - (20 + 180)) / 39.0;
			text_.setY(300 + Interpolate.Smooth(0, -32.0, tmp));
			alpha_ = 1 - tmp;
		}
		++frame_;
	}
}
