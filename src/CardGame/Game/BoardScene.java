package CardGame.Game;

import java.io.*;

import CardGame.Engine.Math.*;
import CardGame.Main;

import javafx.event.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.*;

import CardGame.Engine.*;

public class BoardScene extends UserScene implements IDisposable {
	private final GraphicsContext context_ = Main.GetGC();
	
	private Image backBoard_;
	
	private Button buttonDraw_;
	private Button buttonPass_;
	
	public PlayerState playerState1_;	//The player
	public PlayerState playerState2_;	//Dammah
	
	private int buttonDelay_;
	
	//----------------------------------------------
	
	public BoardScene(UserSceneManager manager) throws Exception {
		super(manager);
		bAutoDelete_ = false;
		
		ResourceManager resourceManager = ResourceManager.GetBase();
		
		backBoard_ = (Image)resourceManager.AddResource(
			"assets/img/board.png",
			new Image(new FileInputStream("assets/img/board.png")));
		{
			final String pathBase = "assets/img/cards/";
			try {
				for (var iFace : RegularCard.__Face_array) {
					for (var iVal : RegularCard.__Value_array) {
						String valueStr = RegularCard.MAP_PATH_VALUE.get(iVal);
						String faceStr = RegularCard.MAP_PATH_FACE.get(iFace);
						
						String nameCard = "CARD_" + valueStr + faceStr;
						String pathCard = pathBase + valueStr + faceStr + ".png";
						resourceManager.AddResource(
							nameCard, new Image(new FileInputStream(pathCard)));
					}
				}
				
				String pathCardBack = pathBase + "gray_back.png";
				resourceManager.AddResource(
					"CARD_BACK", new Image(new FileInputStream(pathCardBack)));
			} catch(FileNotFoundException e) {
			
			}
		}
		
		{
			buttonDraw_ = new Button();
			buttonDraw_.setText("Draw");
			buttonDraw_.setPrefSize(80, 25);
			buttonDraw_.setLayoutX(30);
			buttonDraw_.setLayoutY(540);
			
			buttonPass_ = new Button();
			buttonPass_.setText("Pass");
			buttonPass_.setPrefSize(80, 25);
			buttonPass_.setLayoutX(130);
			buttonPass_.setLayoutY(540);
			
			buttonDraw_.setOnAction(this::_ButtonPressEvent);
			buttonPass_.setOnAction(this::_ButtonPressEvent);
			
			Main.GetRoot().getChildren().add(buttonDraw_);
			Main.GetRoot().getChildren().add(buttonPass_);
		}
		
		{
			playerState1_ = new PlayerState();
			playerState2_ = new PlayerState();
		}
		
		buttonDelay_ = 180;
	}
	@Override
	public void Dispose() {}
	
	public void Render() throws EngineError {
		new RenderState(new Vector2f(0, 0),
			new Vector2f(800 / 626.0f, 600 / 365.0f)).LoadMatAndApply(context_);
		context_.drawImage(backBoard_, 0, 0);
		
		playerState1_.RenderCards();
		playerState2_.RenderCards();
		super.Render();
	}
	public void Update() throws EngineError {
		{
			boolean bButtonEnable = buttonDelay_ <= 0;
			buttonDraw_.setDisable(!bButtonEnable);
			buttonPass_.setDisable(!bButtonEnable);
			--buttonDelay_;
		}
		
		if (frame_ == 60) {
			{
				var card = new RegularCard(RegularCard.RandFace(), RegularCard.RandValue());
				var task = new RegularCardTask(this, card, RegularCardTask.StartMode.FaceDownToUp,
					new Vector2f(700, -100), new Vector2f(-80, -100));
				playerState1_.faceDownCard_ = task;
			}
			{
				var card = new RegularCard(RegularCard.RandFace(), RegularCard.RandValue());
				var task = new RegularCardTask(this, card, RegularCardTask.StartMode.FaceDown,
					new Vector2f(700, -410), new Vector2f(-80, -410));
				playerState2_.faceDownCard_ = task;
			}
		}
		
		playerState1_.UpdateCards();
		playerState2_.UpdateCards();
		super.Update();
	}
	
	//----------------------------------------------
	
	private void _ButtonPressEvent(ActionEvent event) {
		Object src = event.getSource();
		if (src == buttonDraw_) {
		
		}
		else if (src == buttonPass_) {
		
		}
		buttonDelay_ = 40;
	}
	
	//----------------------------------------------
}
