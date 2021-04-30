package CardGame.Game;

import CardGame.Engine.Math.*;
import CardGame.Engine.*;

import java.lang.*;
import java.util.*;

public class SpecialCard extends CardBase {
	public enum Type {
		DrawCard,		//Draws X from the deck
		DrawTwo,		//Draws two cards
		RemoveSelf,		//Returns own last card to the deck
		RemoveOppo,		//Returns opponent's last card to the deck
		Swap,			//Swaps your last card with the opponent's
		DoubleAtk,		//x2 attack power
		HalfAtk,		//x0.5 attack power
		BloodOffer,		//Loses 20% HP, gets 3 special
		Curse,			//Loses random special, opponent draws highest card
		Juggernaut,		//Protects against death once
		None,
	}
	
	//----------------------------------------------
	
	Type type_;
	
	//----------------------------------------------
	
	public SpecialCard(Type type) {
		type_ = type;
	}
	
	@Override
	public void Dispose() {
	}
	
	@Override
	public void InitializeCard() {
	
	}
	
	public void ApplyEffect() {
	
	}
}
