package CardGame.Game;

import CardGame.Engine.Math.*;
import CardGame.Engine.*;
import CardGame.Main;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.lang.*;
import java.util.*;

public class Scoreboard extends UserTaskBase {
	private Text text_;
	
	public int countWin_;
	public int countDraw_;
	public int countLose_;
	
	//----------------------------------------------
	
	public Scoreboard(UserScene parent) {
		super(parent);
		
		text_ = new Text();
		text_.setX(18);
		text_.setY(26);
		text_.setFont(Font.font("arial", FontWeight.NORMAL, FontPosture.REGULAR, 16));
		text_.setFill(Color.POWDERBLUE);
		text_.setOpacity(0);
		
		Main.GetRoot().getChildren().add(text_);
	}
	@Override
	public void Dispose() { }
	
	@Override
	public void Render(int layer) throws EngineError {
	
	}
	@Override
	public void Update() throws EngineError {
		if (frame_ < 30) {
			float tmp = frame_ / 29.0f;
			text_.setOpacity(tmp);
		}
		
		text_.setText(String.format("Wins/Draws/Losses: %d/%d/%d",
			countWin_, countDraw_, countLose_));
		
		++frame_;
	}
}
