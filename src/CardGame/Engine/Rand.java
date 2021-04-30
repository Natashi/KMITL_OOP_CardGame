package CardGame.Engine;

import java.util.*;

public class Rand {
	private Random rand_;
	
	//----------------------------------------------
	
	public Rand() {
		rand_ = new Random(System.currentTimeMillis());
	}
	
	//Random double in range [0, 1)
	public double GetReal() {
		return rand_.nextDouble();
	}
	//Random double in range [a, b)
	public double GetReal(double a, double b) {
		return a + this.GetReal() * (b - a);
	}
	
	//Random int
	public int GetInt() {
		return rand_.nextInt();
	}
	//Random double in range [a, b)
	public int GetInt(int a, int b) {
		return (int)this.GetReal(a, b + 0.999);
	}
}
