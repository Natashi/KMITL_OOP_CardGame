package CardGame.Engine;

public class EngineError extends java.lang.Exception {
	public EngineError(String msg) {
		super(msg);
	}
	public EngineError(String msg, Throwable prev) {
		super(msg, prev);
	}
}
