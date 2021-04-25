package CardGame.Game;

import java.io.*;

import CardGame.Engine.Math.Vector2f;
import CardGame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

import CardGame.Engine.*;

public class BoardScene extends UserScene implements IDisposable {
	private final GraphicsContext context_ = Main.GetGC();
	
	private Image backBoard_;
	
	//----------------------------------------------
	
	public BoardScene(UserSceneManager manager) throws Exception {
		super(manager);
		bAutoDelete_ = false;
		
		backBoard_ = new Image(new FileInputStream("assets/img/board.png"));
	}
	@Override
	public void Dispose() {}
	
	public void Render() throws EngineError {
		RenderState rs = new RenderState(new Vector2f(0, 0),
			new Vector2f(800 / 626.0f, 600 / 365.0f));
		rs.LoadMatAndApply(context_);
		context_.drawImage(backBoard_, 0, 0);
		super.Render();
	}
	public void Update() throws EngineError {
		super.Update();
	}
}
