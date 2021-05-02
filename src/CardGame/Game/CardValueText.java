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

public class CardValueText extends UserTaskBase {
	private PlayerState playerState_;
	
	private Text text_;
	
	private float alpha_;
	public boolean bEnd_;
	
	//----------------------------------------------
	
	public CardValueText(UserScene parent, PlayerState target, boolean hideFirst,
						 Vector2f pos)
	{
		super(parent);
		
		playerState_ = target;
		
		text_ = new Text();
		text_.setX(pos.x);
		text_.setY(pos.y);
		text_.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text_.setFill(Color.WHITE);
		
		Main.GetRoot().getChildren().add(text_);
		
		alpha_ = 0;
		text_.setOpacity(0);
		
		ChangeAlpha(255, 40);
		UpdateText(hideFirst, true);
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
		if (bEnd_ && frameEnd_ == Integer.MAX_VALUE) {
			frameEnd_ = frame_ + 30;
			ChangeAlpha(0, 30);
		}
		++frame_;
	}
	
	public void UpdateText(boolean bHideFirst, boolean bAllowGreen) {
		StringBuilder str = new StringBuilder();
		
		int value = 0;
		
		if (bHideFirst) {
			value = playerState_.GetHeldCardValueFaceUp();
			str.append("? + ");
		}
		else {
			value = playerState_.GetHeldCardValueTotal();
		}
		str.append(value + " / 21");
		
		text_.setText(str.toString());
		
		if (bAllowGreen && value == 21)
			text_.setFill(Color.rgb(53, 255, 53));
		else if (value > 21)
			text_.setFill(Color.RED);
		else
			text_.setFill(Color.WHITE);
	}
	
	public void ChangeAlpha(int to, int time) {
		int prev = (int)(alpha_ * 255);
		new FrameTimer(time) {
			@Override
			public void Loop(ActionEvent actionEvent) {
				float tmp = frame_ / (float)(time - 1);
				alpha_ = Interpolate.Linear(prev, to, tmp) / 255.0f;
				++frame_;
			}
		}.Start();
	}
}
