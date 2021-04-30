package CardGame.Game;

import CardGame.Engine.Math.*;
import CardGame.Engine.*;
import CardGame.Main;

import java.lang.*;
import java.util.*;

public class PlayerState {
	public enum Action {
		None,
		DrawRegular,
		DrawSpecial,
		DrawRegularAndSpecial,
		Pass,
	}
	
	//----------------------------------------------
	
	public double health_;
	public double healthMax_;
	
	public double baseAtkPower_;
	
	public RegularCardTask faceDownCard_;
	public ArrayList<RegularCardTask> listFaceUpCards_;
	public ArrayList<SpecialCard> listSpecialCards_;
	
	public Action previousTurnAction_;
	public HashSet<SpecialCard.Type> activeStatusEffects_;
	
	public boolean juggernautUsed_;
	
	//----------------------------------------------
	
	public PlayerState() {
		this(1000, 180);
		//Dies in 6 [x1] hits
		//	or 1 [x2] hit and 4 [x1] hits
		//	or 2 [x2] hits and 2 [x1] hits
	}
	public PlayerState(double hp, double atk) {
		health_ = healthMax_ = hp;
		baseAtkPower_ = atk;
		
		faceDownCard_ = null;
		listFaceUpCards_ = new ArrayList<>();
		listSpecialCards_ = new ArrayList<>();
		
		previousTurnAction_ = Action.None;
		activeStatusEffects_ = new HashSet<>();
		
		juggernautUsed_ = false;
	}
	
	public void AttackAnother(PlayerState target) {
		double atk = baseAtkPower_;
		if (activeStatusEffects_.contains(SpecialCard.Type.DoubleAtk))
			atk *= 2;
		if (activeStatusEffects_.contains(SpecialCard.Type.HalfAtk))
			atk *= 0.5;
		target.health_ -= baseAtkPower_;
		
		if (!target.juggernautUsed_ && target.health_ < 1
			&& target.activeStatusEffects_.contains(SpecialCard.Type.Juggernaut))
		{
			//Protects against death
			target.health_ = 1;
			target.juggernautUsed_ = true;
		}
	}
	
	public void RenderCards() throws EngineError {
		if (faceDownCard_ != null) faceDownCard_.Render(0);
		for (var iCard : listFaceUpCards_)
			if (iCard != null) iCard.Render(0);
		//for (var iCard : listSpecialCards_)
		//	iCard.Update();
	}
	public void UpdateCards() throws EngineError {
		if (faceDownCard_ != null) faceDownCard_.Update();
		for (var iCard : listFaceUpCards_)
			if (iCard != null) iCard.Update();
		//for (var iCard : listSpecialCards_)
		//	iCard.Update();
	}
}
