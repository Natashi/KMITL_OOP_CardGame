package CardGame.Game;

import CardGame.Engine.Math.*;
import CardGame.Engine.*;
import CardGame.Main;

import javafx.animation.*;
import javafx.event.ActionEvent;

import java.lang.*;
import java.util.*;
import java.util.function.*;

public class RegularCardTask extends UserTaskBase {
	public enum StartMode {
		FaceUp,
		FaceDown,
		FaceDownToUp,
	};
	
	public static final int CARD_IMG_W = 207;
	public static final int CARD_IMG_H = 317;
	public static final int CARD_HT = 140;
	
	//----------------------------------------------
	
	private RenderState renderState_;
	public RegularCard card_;
	
	public StartMode startMode_;
	public float scale_;
	public float faceScale_;	//[-1, 0) -> Face down, ([)0, 1] -> Face up
	public boolean bEnd_;
	
	//----------------------------------------------
	
	public RegularCardTask(UserScene parent, RegularCard card, StartMode start,
						   Vector2f iniPos, Vector2f endPos)
	{
		super(parent);
		
		renderState_ = new RenderState();
		card_ = card;
		
		startMode_ = start;
		scale_ = 1;
		faceScale_ = (start == StartMode.FaceUp) ? 1 : -1;
		bEnd_ = false;
		
		renderState_.position_ = iniPos.clone();
		renderState_.origin_ = new Vector2f(CARD_IMG_W / 2.0f, CARD_IMG_H / 2.0f);
		
		if (!iniPos.equals(endPos))
			MoveTo(endPos.x, endPos.y, 60, Interpolate::Decelerate);
	}
	@Override
	public void Dispose() {}
	
	public RenderState GetRenderer() { return renderState_; }
	public void SetPosition(float x, float y) {
		renderState_.position_.Set(x, y);
	}
	public void SetPosition(Vector2f pos) {
		renderState_.position_.Set(pos);
	}
	
	@Override
	public void Render(int layer) throws EngineError {
		//if (layer != 2) return;
		
		var gc = Main.GetGC();
		
		renderState_.LoadMatAndApply(gc);
		renderState_.Render(gc, faceScale_ >= 0 ? card_.imgCardFront_ : card_.imgCardBack_);
		//Main.GetGC().drawImage(card_.imgCardFront_, 0, 0);
	}
	@Override
	public void Update() throws EngineError {
		if (!bEnd_) {
			if (frame_ < 40) {
				float tmp = frame_ / 39.0f;
				
				scale_ = Interpolate.Smooth(1.5f, 1, tmp) * (CARD_HT / (float)CARD_IMG_H);
				renderState_.alpha_ = (int)Interpolate.Linear(0, 255, tmp);
			}
			else if ((startMode_ == StartMode.FaceDownToUp) && frame_ == 40 + 30) {
				this.FlipToScale(1, 16);
			}
			renderState_.scale_.Set(Math.abs(scale_ * faceScale_), scale_);
		}
		else {
			if (frameEnd_ == Integer.MAX_VALUE) {
				this.MoveTo(GetRenderer().position_.x, GetRenderer().position_.y - 120,
					40, Interpolate::Accelerate);
				this.AlphaTo(0, 40);
				frameEnd_ = frame_ + 40;
			}
		}
		
		++frame_;
	}
	
	public void FlipToScale(float sc, int time) {
		RegularCardTask own = this;
		new FrameTimer(time) {
			private RegularCardTask task_;
			private float orgScale_;
			
			@Override
			public void Start() {
				task_ = own;
				orgScale_ = own.faceScale_;
			}
			@Override
			public void Loop(ActionEvent actionEvent) {
				float tmp = frame_ / (float)(duration_ - 1);
				own.faceScale_ = Interpolate.Smooth(orgScale_, sc, tmp);
				++frame_;
			}
		}.Start();
	}
	public void MoveTo(float x, float y, int time, Function3<Float, Float, Float, Float> lerpFunc) {
		Vector2f orgPos = GetRenderer().position_.clone();
		
		RegularCardTask own = this;
		new FrameTimer(time) {
			private RegularCardTask task_;
			
			@Override
			public void Start() {
				task_ = own;
			}
			@Override
			public void Loop(ActionEvent actionEvent) {
				float tmp = frame_ / (float)(duration_ - 1);
				var pPos = own.GetRenderer().position_;
				pPos.x = lerpFunc.apply(orgPos.x, x, tmp);
				pPos.y = lerpFunc.apply(orgPos.y, y, tmp);
				++frame_;
			}
		}.Start();
	}
	public void AlphaTo(int alpha, int time) {
		int orgAlpha = GetRenderer().alpha_;
		
		RegularCardTask own = this;
		new FrameTimer(time) {
			@Override
			public void Loop(ActionEvent actionEvent) {
				float tmp = frame_ / (float)(duration_ - 1);
				GetRenderer().alpha_ = (int)Interpolate.Smooth(
					(float)orgAlpha, (float)alpha, tmp);
				++frame_;
			}
		}.Start();
	}
}
