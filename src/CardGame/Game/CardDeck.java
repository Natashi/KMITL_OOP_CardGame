package CardGame.Game;

import CardGame.Engine.*;
import CardGame.Engine.Math.*;
import CardGame.Main;

import java.util.*;

public class CardDeck {
	private Stack<RegularCard> listAvailCards_;
	
	//----------------------------------------------
	
	public CardDeck() {
		ResetAndShuffle();
	}
	
	public void ResetAndShuffle() {
		ArrayList<RegularCard.Value> listVal = new ArrayList<>();
		for (var i : RegularCard.__Value_array)
			listVal.add(i);
		ArrayList<RegularCard.Face> listFace = new ArrayList<>();
		for (var i : RegularCard.__Face_array)
			listFace.add(i);
		
		ArrayList<RegularCard> listCard = new ArrayList<>();
		for (var iValue : RegularCard.__Value_array) {
			for (var iFace : RegularCard.__Face_array) {
				var card = new RegularCard(iFace, iValue);
				listCard.add(card);
			}
		}
		Collections.shuffle(listCard);
		
		listAvailCards_ = new Stack<>();
		for (var iCard : listCard)
			listAvailCards_.push(iCard);
	}
	
	public boolean Empty() {
		return listAvailCards_.size() > 0;
	}
	public RegularCard GetCard() {
		return listAvailCards_.pop();
	}
}
