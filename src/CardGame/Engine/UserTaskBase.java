package CardGame.Engine;

public class UserTaskBase implements IDisposable {
	protected UserScene parent_;
	protected int frame_;
	protected int frameEnd_;
	protected boolean bFinish_;
	
	//----------------------------------------------
	
	public UserTaskBase(UserScene parent) {
		parent_ = parent;
		frame_ = 0;
		frameEnd_ = Integer.MAX_VALUE;
		bFinish_ = false;
	}
	@Override
	public void Dispose() {}
	
	public void Render(int layer) {}
	public void Update() {}
	
	public UserScene GetParent() { return parent_; }
	
	public void SetEndFrame(int frame) { frameEnd_ = frame; }
	
	public int GetFrame() { return frame_; }
	public int GetFrameEnd() { return frameEnd_; }
	public boolean IsFinished() { return bFinish_; }
}
