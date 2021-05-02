package CardGame.Game;

import CardGame.Engine.*;
import CardGame.Engine.Math.*;
import CardGame.Main;

import java.io.*;
import javafx.event.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.*;
import javafx.scene.text.*;

public class BoardScene extends UserScene implements IDisposable {
	public static final int MAX_HOLDABLE_CARDS = 14;
	public static final int MAX_CARD_PER_ROW = 5;
	public static final int CARD_OFF_X = 100;
	public static final int CARD_OFF_Y = 32;
	public static final int POST_TURN_ACTION_DELAY = 40;
	
	//----------------------------------------------
	
	private final GraphicsContext context_ = Main.GetGC();
	
	private Image backBoard_;
	private Image backBoardGlow_;
	private RenderState backBoardGlowRS_;
	
	private Button buttonDraw_;
	private Button buttonPass_;
	
	private Scoreboard scoreboard_;
	
	private CardValueText player1CardValue_;
	private CardValueText player2CardValue_;
	
	public PlayerState playerState1_;	//The player
	public PlayerState playerState2_;	//Dammah
	
	private CardDeck deck_;
	
	private int buttonDelay_;
	
	private int currentTurn_;
	
	//----------------------------------------------
	
	public BoardScene(UserSceneManager manager) throws Exception {
		super(manager);
		bAutoDelete_ = false;
		
		ResourceManager resourceManager = ResourceManager.GetBase();
		
		backBoard_ = (Image)resourceManager.AddResource(
			"assets/img/board.png",
			new Image(new FileInputStream("assets/img/board.png")));
		backBoardGlow_ = (Image)resourceManager.AddResource(
			"assets/img/BG05b_a.png",
			new Image(new FileInputStream("assets/img/BG05b_a.png")));
		{
			backBoardGlowRS_ = new RenderState();
			backBoardGlowRS_.origin_ = new Vector2f(256, 256);
			backBoardGlowRS_.position_ = new Vector2f(400, 300);
			backBoardGlowRS_.scale_ = new Vector2f(3.4);
			backBoardGlowRS_.alpha_ = 64;
			backBoardGlowRS_.blendMode_ = BlendMode.ADD;
		}
		
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
			scoreboard_ = new Scoreboard(this);
			this.AddTask(scoreboard_);
		}
		
		_InitializeGame();
	}
	@Override
	public void Dispose() {}
	
	public void Render() throws EngineError {
		new RenderState(new Vector2f(0, 0),
			new Vector2f(800 / 626.0f, 600 / 365.0f)).LoadMatAndApply(context_);
		context_.drawImage(backBoard_, 0, 0);
		
		backBoardGlowRS_.LoadMatAndApply(context_);
		backBoardGlowRS_.Render(context_, backBoardGlow_);
		
		playerState1_.RenderCards();
		playerState2_.RenderCards();
		super.Render();
	}
	public void Update() throws EngineError {
		{
			backBoardGlowRS_.angle_.z += 0.087;
		}
		
		if (currentTurn_ == 1) {	//Player 1 button delay
			boolean bButtonEnable = buttonDelay_ <= 0;
			buttonDraw_.setDisable(!bButtonEnable
				|| (playerState1_.GetHeldCardCount() >= MAX_HOLDABLE_CARDS));
			buttonPass_.setDisable(!bButtonEnable);
			--buttonDelay_;
		}
		else {
			buttonDraw_.setDisable(true);
			buttonPass_.setDisable(true);
		}
		
		if (playerState1_ != null) playerState1_.UpdateCards();
		if (playerState1_ != null) playerState2_.UpdateCards();
		super.Update();
	}
	
	//----------------------------------------------
	
	private void _ButtonPressEvent(ActionEvent event) {
		if (currentTurn_ != 1) return;
		
		int pl2Delay = 90;
		
		Object src = event.getSource();
		if (src == buttonDraw_) {
			Vector2f newPos = _GetNextCardPos(playerState1_, 260, 440);
			RegularCardTask task = _CreateCardTask(newPos.x, newPos.y,
				RegularCardTask.StartMode.FaceDownToUp);
			playerState1_.listFaceUpCards_.add(task);
			
			playerState1_.previousTurnAction_ = PlayerState.Action.Draw;
			
			_UpdateText(player1CardValue_, false, true,80);
		}
		else if (src == buttonPass_) {
			playerState1_.previousTurnAction_ = PlayerState.Action.Pass;
			pl2Delay = 40;
		}
		else return;
		
		currentTurn_ = 2;	//Player 2's turn
		buttonDelay_ = POST_TURN_ACTION_DELAY;
		
		if (!_CheckRoundEnd())
			_ActivatePlayer2Decision(pl2Delay);
	}
	
	//----------------------------------------------
	
	private void _InitializeGame() {
		playerState1_ = new PlayerState();
		playerState2_ = new PlayerState();
	
		deck_ = new CardDeck();
		
		currentTurn_ = 1;
		buttonDelay_ = 120;
		
		BoardScene own = this;
		new FrameTimer() {
			@Override
			public void Loop(ActionEvent actionEvent) {
				if (frame_ == 60) {
					{
						//Add face-down card to player 1
						RegularCardTask task = _CreateCardTask(260, 440,
							RegularCardTask.StartMode.FaceDownToUp);
						playerState1_.faceDownCard_ = task;
					}
					{
						//Add face-down card to player 2
						RegularCardTask task = _CreateCardTask(260, 110,
							RegularCardTask.StartMode.FaceDown);
						playerState2_.faceDownCard_ = task;
					}
				}
				else if (frame_ == 120) {
					{
						final int Y_OFF = 90;
						player1CardValue_ = new CardValueText(own, playerState1_,
							false, new Vector2f(50, 440 - Y_OFF));
						player2CardValue_ = new CardValueText(own, playerState2_,
							true, new Vector2f(50, 110 + Y_OFF + 20));
						own.AddTask(player1CardValue_);
						own.AddTask(player2CardValue_);
					}
					
					this.Stop();
				}
				++frame_;
			}
		}.Start();
	}
	
	private RegularCardTask _CreateCardTask(float x, float y, RegularCardTask.StartMode mode) {
		var card = deck_.GetCard();
		if (card == null) return null;
		var task = new RegularCardTask(this, card, mode,
			new Vector2f(820, y),
			new Vector2f(x, y));
		return task;
	}
	
	private void _ActivatePlayer2Decision(int delay) {
		if (currentTurn_ != 2) return;
		
		DammahAI dammahAct = new DammahAI(this);
		dammahAct.Begin();
		
		BoardScene own = this;
		new FrameTimer() {
			@Override
			public void Loop(ActionEvent actionEvent) {
				if (dammahAct.IsDone()) {
					playerState2_.previousTurnAction_ = dammahAct.action_;
					if (dammahAct.action_ == PlayerState.Action.Draw) {
						Vector2f newPos = _GetNextCardPos(playerState2_, 260, 110);
						RegularCardTask task = _CreateCardTask(newPos.x, newPos.y,
							RegularCardTask.StartMode.FaceDownToUp);
						playerState2_.listFaceUpCards_.add(task);
						
						_UpdateText(player2CardValue_, true, false,80);
					}
					else {
						playerState2_.previousTurnAction_ = PlayerState.Action.Pass;
					}
					
					currentTurn_ = 1;	//Return control back to the player
					
					_CheckRoundEnd();
					this.Stop();
				}
			}
		}.Start();
	}
	
	private void _UpdateText(CardValueText obj, boolean hideFirst, boolean bAllowGreen, int delay) {
		new FrameTimer(delay) {
			@Override
			public void Loop(ActionEvent actionEvent) {
				++frame_;
				if (frame_ == delay)
					obj.UpdateText(hideFirst, bAllowGreen);
			}
		}.Start();
	}
	
	private Vector2f _GetNextCardPos(PlayerState state, int baseX, int baseY) {
		return _GetNextCardPos(state, baseX, baseY, CARD_OFF_X, CARD_OFF_Y);
	}
	private Vector2f _GetNextCardPos(PlayerState state, int baseX, int baseY, int offX, int offY) {
		Vector2f res = new Vector2f(baseX, baseY);
		
		int heldCards = state.listFaceUpCards_.size() + 1;
		while (heldCards >= MAX_CARD_PER_ROW) {
			baseX += offX * 0.34f;
			res.y += offY;
			heldCards -= MAX_CARD_PER_ROW;
		}
		res.x = baseX + offX * heldCards;
		
		return res;
	}
	
	private boolean _CheckRoundEnd() {
		//Round ends if both player passed
		if (playerState1_.previousTurnAction_ == playerState2_.previousTurnAction_
			&& playerState1_.previousTurnAction_ == PlayerState.Action.Pass)
		{
			currentTurn_ = 0;
			buttonDelay_ = 999999;
			
			BoardScene own = this;
			new FrameTimer() {
				@Override
				public void Loop(ActionEvent actionEvent) {
					if (frame_ == 30) {
						playerState2_.faceDownCard_.FlipToScale(1, 16);
					}
					else if (frame_ == 30 + 10) {
						player2CardValue_.UpdateText(false, true);
					}
					else if (frame_ == 30 + 10 + 30) {
						int scorePlayer = playerState1_.GetHeldCardValueTotal();
						int scoreDammah = playerState2_.GetHeldCardValueTotal();
						
						int result = 0;
						if (scorePlayer != scoreDammah) {
							if (scorePlayer > 21 && scoreDammah > 21) {
								int playerDiff = scorePlayer - 21;
								int dammahDiff = scoreDammah - 21;
								result = playerDiff < dammahDiff ? 1 : -1;
							}
							else {
								if (scorePlayer > 21) result = -1;
								else if (scoreDammah > 21) result = 1;
								else result = scorePlayer - scoreDammah;
							}
						}
						
						if (result > 0)
							++scoreboard_.countWin_;
						else if (result < 0)
							++scoreboard_.countLose_;
						else
							++scoreboard_.countDraw_;
						
						var taskResult = new GameWinLoseText(own, result);
						own.AddTask(taskResult);
					}
					else if (frame_ == 30 + 10 + 30 + 160) {
						player1CardValue_.bEnd_ = true;
						player2CardValue_.bEnd_ = true;
						
						playerState2_.SetEndAllCards();
					}
					else if (frame_ == 30 + 10 + 30 + 160 + 15) {
						playerState1_.SetEndAllCards();
					}
					else if (frame_ == 30 + 10 + 30 + 160 + 90) {
						_InitializeGame();
						this.Stop();
					}
					++frame_;
				}
			}.Start();
			
			return true;
		}
		return false;
	}
}
