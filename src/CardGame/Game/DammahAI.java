package CardGame.Game;

import CardGame.Engine.*;
import CardGame.Engine.Math.*;
import CardGame.Main;

import java.io.*;
import javafx.event.*;

public class DammahAI {
	public static final int MIN_DECISION_TIME = BoardScene.POST_TURN_ACTION_DELAY + 40;
	public static final int MAX_DECISION_TIME = 200;
	
	//----------------------------------------------
	
	private BoardScene parent_;
	private boolean done_;
	public PlayerState.Action action_;
	
	//----------------------------------------------
	
	public DammahAI(BoardScene parent) {
		parent_ = parent;
		done_ = false;
		action_ = PlayerState.Action.None;
	}
	
	public void Begin() {
		var rand = Main.GetGameController().GetRand();
		
		PlayerState playerState = parent_.playerState1_;
		PlayerState dammahState = parent_.playerState2_;
		
		{
			if (dammahState.GetHeldCardCount() >= BoardScene.MAX_HOLDABLE_CARDS ||
				dammahState.GetHeldCardValueTotal() >= 21)
			{
				action_ = PlayerState.Action.Pass;
			}
			else {
				//TODO: Make an AI that's actually decently smart
				double spice = rand.GetReal();
				
				//Dammah doesn't know the value of your first card!
				int plRemTo21 = 21 - playerState.GetHeldCardValueFaceUp();
				int myRemTo21 = 21 - dammahState.GetHeldCardValueTotal();
				PlayerState.Action plPrevAct = playerState.previousTurnAction_;
				
				if (myRemTo21 >= 13) {		//Always draw if the next draw will definitely not overshoot
					action_ = PlayerState.Action.Draw;
				}
				else if (plRemTo21 < 0) {	//Player already exceed 21, just pass
					action_ = PlayerState.Action.Pass;
				}
				else {
					double passScore = (13 - myRemTo21) / 13.0;
					//If the player passed in the last turn, try to guess the player's score
					if (plPrevAct == PlayerState.Action.Pass && plRemTo21 < 8)
						passScore += (21 - plRemTo21) / 21.0 * 0.3;
					//action_ = spice < threshold ? PlayerState.Action.Draw : PlayerState.Action.Pass;
					action_ = passScore + spice * 0.1 > 0.8
						? PlayerState.Action.Pass : PlayerState.Action.Draw;
				}
			}
		}
		
		{
			int decisionTime = rand.GetInt(MIN_DECISION_TIME, MAX_DECISION_TIME);
			new FrameTimer(decisionTime) {
				@Override
				public void Loop(ActionEvent actionEvent) {
					++frame_;
					if (frame_ == decisionTime) {
						done_ = true;
						this.Stop();
					}
				}
			}.Start();
		}
	}
	
	public boolean IsDone() { return done_; }
}
