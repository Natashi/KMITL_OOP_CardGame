package CardGame.Engine;

import java.lang.*;
import java.util.LinkedList;

public class UserScene implements IDisposable {
	public enum Type {
		Unknown,
		Menu,
		Background,
		Stage,
		StageUI,
		Pause,
	}
	
	public static final int MAX_RENDER_LAYER = 16;
	
	//----------------------------------------------
	
	protected UserSceneManager manager_;
	protected int frame_;
	protected Type type_;
	protected boolean bEnableRender_;
	protected boolean bEnableUpdate_;
	protected boolean bDelete_;
	protected boolean bAutoDelete_;
	protected LinkedList<UserTaskBase> listTask_;
	
	//----------------------------------------------
	
	public UserScene(UserSceneManager manager) {
		manager_ = manager;
		frame_ = 0;
		type_ = Type.Unknown;
		bEnableRender_ = true;
		bEnableUpdate_ = true;
		bDelete_ = false;
		bAutoDelete_ = true;
	}
	@Override
	public void Dispose() {}
	
	public void Render() {
		for (int iLayer = 0; iLayer < MAX_RENDER_LAYER; ++iLayer) {
			for (var iTask : listTask_) {
				if (iTask != null && !iTask.IsFinished())
					iTask.Render(iLayer);
			}
		}
	}
	public void Update() {
		for (var itr = listTask_.iterator(); itr.hasNext();) {
			UserTaskBase task = itr.next();
			if (task != null && task.IsFinished()) {
				task.Update();
				if (task.GetFrame() >= task.GetFrameEnd())
					task.bFinish_ = true;
			}
			else itr.remove();
		}
		++frame_;
	}
	
	public UserSceneManager GetParentManager() { return manager_; }
	
	public void SetEnableRender(boolean b) { bEnableRender_ = b; }
	public boolean IsEnableRender() { return bEnableRender_; }
	public void SetEnableUpdate(boolean b) { bEnableUpdate_ = b; }
	public boolean IsEnableUpdate() { return bEnableUpdate_; }
	
	public boolean IsDelete() { return bDelete_; }
	public boolean IsAutoDelete() { return bAutoDelete_; }
	public boolean IsToBeDeleted() { return bDelete_ || (bAutoDelete_ && GetTaskCount() == 0); }
	
	public void SetType(Type t) { type_ = t; }
	public Type GetType() { return type_; }
	
	public int GetFrame() { return frame_; }
	
	public int GetTaskCount() { return listTask_.size(); }
	
	public void AddTask(UserTaskBase task) {
		this.AddTask(GetTaskCount(), task);
	}
	public void AddTask(int pos, UserTaskBase task) {
		listTask_.add(pos, task);
	}
}
