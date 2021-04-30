package CardGame.Engine;

@FunctionalInterface
public interface Function3<I1, I2, I3, O> { 	// func(I1, I2, I3) -> O
	public O apply(I1 v1, I2 v2, I3 v3);
}
