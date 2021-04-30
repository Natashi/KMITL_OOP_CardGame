package CardGame.Game;

import CardGame.Engine.Math.*;
import CardGame.Engine.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;

import CardGame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class RegularCard extends CardBase {
	public enum Value {
		Ace,
		N2,
		N3,
		N4,
		N5,
		N6,
		N7,
		N8,
		N9,
		N10,
		Jack,
		Queen,
		King,
	}
	public static final Value[] __Value_array = Value.values();
	public enum Face {
		Spade,
		Heart,
		Club,
		Diamond,
	}
	public static final Face[] __Face_array = Face.values();
	
	public static final Map<Value, Integer> MAP_CARD_VALUES = Map.ofEntries(
		Map.entry(Value.Ace, 1),
		Map.entry(Value.N2, 2),
		Map.entry(Value.N3, 3),
		Map.entry(Value.N4, 4),
		Map.entry(Value.N5, 5),
		Map.entry(Value.N6, 6),
		Map.entry(Value.N7, 7),
		Map.entry(Value.N8, 8),
		Map.entry(Value.N9, 9),
		Map.entry(Value.N10, 10),
		Map.entry(Value.Jack, 11),
		Map.entry(Value.Queen, 12),
		Map.entry(Value.King, 13)
	);
	
	public static final Map<Value, String> MAP_PATH_VALUE = Map.ofEntries(
		Map.entry(Value.Ace, "A"),
		Map.entry(Value.N2, "2"),
		Map.entry(Value.N3, "3"),
		Map.entry(Value.N4, "4"),
		Map.entry(Value.N5, "5"),
		Map.entry(Value.N6, "6"),
		Map.entry(Value.N7, "7"),
		Map.entry(Value.N8, "8"),
		Map.entry(Value.N9, "9"),
		Map.entry(Value.N10, "10"),
		Map.entry(Value.Jack, "J"),
		Map.entry(Value.Queen, "Q"),
		Map.entry(Value.King, "K")
	);
	public static final Map<Face, String> MAP_PATH_FACE = Map.ofEntries(
		Map.entry(Face.Spade, "S"),
		Map.entry(Face.Heart, "H"),
		Map.entry(Face.Club, "C"),
		Map.entry(Face.Diamond, "D")
	);
	
	//----------------------------------------------
	
	private Value value_;
	private Face type_;
	public Image imgCardFront_;
	public Image imgCardBack_;
	
	//----------------------------------------------
	
	public RegularCard(Face type, Value value) {
		value_ = value;
		type_ = type;
		
		ResourceManager resourceManager = ResourceManager.GetBase();
		
		{
			String valueStr = RegularCard.MAP_PATH_VALUE.get(value);
			String faceStr = RegularCard.MAP_PATH_FACE.get(type);
			imgCardFront_ = (Image)resourceManager.GetResource("CARD_" + valueStr + faceStr);
		}
		imgCardBack_ = (Image)resourceManager.GetResource("CARD_BACK");
	}
	
	@Override
	public void Dispose() {
	}
	
	@Override
	public void InitializeCard() {
	
	}
	
	public Value GetValue() { return value_; }
	public int GetValueNumeric() { return MAP_CARD_VALUES.get(value_); }
	
	public static RegularCard.Face RandFace() {
		int r = Main.GetGameController().GetRand().GetInt(0, __Face_array.length - 1);
		return __Face_array[r];
	}
	public static RegularCard.Value RandValue() {
		int r = Main.GetGameController().GetRand().GetInt(0, __Value_array.length - 1);
		return __Value_array[r];
	}
}
