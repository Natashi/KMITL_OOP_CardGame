package CardGame.Engine.Math;

public class Interpolate {
	//Daily rant:
	//	I'm going to have a stroke if I keep doing this.
	//	What the fuck is this stupid language?
	//	All I want is to make a simple generic method, do maths shit,
	//		and spit out the result, and I can't even do something THAT SIMPLE?!
	//	WHICH BRAIN-DEAD MOTHERFUCKER CREATED THIS STUPID FUCKING GARBAGE OF A LANGUAGE!?!?
	
	public static float Linear(float a, float b, float x) {
		return a + (x) * (b - a);
	}
	public static double Linear(double a, double b, double x) {
		return a + (x) * (b - a);
	}
	
	public static float Smooth(float a, float b, float x) {
		return a + (x * x * (3.0f - 2.0f * x)) * (b - a);
	}
	public static double Smooth(double a, double b, double x) {
		return a + (x * x * (3.0 - 2.0 * x)) * (b - a);
	}
	
	public static float Smoother(float a, float b, float x) {
		return a + (x * x * x * (x * (x * 6.0f - 15.0f) + 10.0f)) * (b - a);
	}
	public static double Smoother(double a, double b, double x) {
		return a + (x * x * x * (x * (x * 6.0 - 15.0) + 10.0)) * (b - a);
	}
	
	public static float Accelerate(float a, float b, float x) {
		return a + (x * x) * (b - a);
	}
	public static double Accelerate(double a, double b, double x) {
		return a + (x * x) * (b - a);
	}
	
	public static float Decelerate(float a, float b, float x) {
		float y = 1.0f - x;
		return a + (1.0f - y * y) * (b - a);
	}
	public static double Decelerate(double a, double b, double x) {
		double y = 1.0 - x;
		return a + (1.0 - y * y) * (b - a);
	}
}
