package CardGame.Engine;

import java.util.ArrayList;

public class UserSceneManager implements IDisposable {
	public static final int MAX_SCENE_SPACE = 12;
	
	//----------------------------------------------
	
	private static UserSceneManager base_ = null;
	
	private UserScene primaryScene_;
	private UserScene rearScene_;
	private ArrayList<UserScene> listScene_;
	
	//----------------------------------------------
	
	public UserSceneManager() {
		primaryScene_ = null;
		rearScene_ = null;
	}
	@Override
	public void Dispose() {}
	
	static UserSceneManager GetBase() { return base_; }
	
	public void Initialize() throws EngineError {
		if (base_ != null) throw new EngineError("SceneManager already initialized.");
		base_ = this;
		
		primaryScene_ = new UserScene(this);
		rearScene_ = new UserScene(this);
		listScene_ = new ArrayList<>();
		for (int i = 0; i < MAX_SCENE_SPACE; i++) {
			listScene_.add(null);
		}
	}
	public void Release() {
		listScene_.clear();
	}
	
	public void Render() throws EngineError {
		primaryScene_.Render();
		
		for (var iScene : listScene_) {
			if (iScene != null && !iScene.IsToBeDeleted()) {
				if (iScene.bEnableRender_) iScene.Render();
			}
		}
		
		rearScene_.Render();
	}
	public void Update() throws EngineError {
		primaryScene_.Update();
		
		int i = 0;
		for (var iScene : listScene_) {
			if (iScene != null) {
				if (iScene.bEnableUpdate_) iScene.Update();
				if (iScene.IsToBeDeleted())
					listScene_.set(i, null);
			}
			++i;
		}
		
		rearScene_.Update();
	}
	
	public UserScene GetScene(int indexScene) { return listScene_.get(indexScene); }
	public <T2> T2 GetSceneAs(int indexScene) {
		return (T2)GetScene(indexScene);
	}
	public UserScene GetPrimaryScene() { return primaryScene_; }
	public UserScene GetRearScene() { return rearScene_; }
	
	public void AddScene(UserScene ptrScene, int indexScene) throws EngineError {
		this.AddScene(ptrScene, indexScene, false);
	}
	public void AddScene(UserScene ptrScene, int indexScene, boolean bReplace) throws EngineError {
		if (indexScene >= MAX_SCENE_SPACE)
			throw new EngineError(String.format("Scene index out of bounds: %d", indexScene));
		else if (!bReplace && listScene_.get(indexScene) != null)
			throw new EngineError(String.format("Scene already exists: %d", indexScene));
		listScene_.set(indexScene, ptrScene);
	}
	public void RemoveScene(int indexScene) {
		UserScene scene = listScene_.get(indexScene);
		if (scene == null) return;
		scene.bDelete_ = true;
		scene.bEnableRender_ = false;
		scene.bEnableUpdate_ = false;
	}
	public void RemoveScene(UserScene scene) {
		for (var iScene : listScene_) {
			if (iScene == scene) {		//Check if they are the same object
				scene.bDelete_ = true;
				scene.bEnableRender_ = false;
				scene.bEnableUpdate_ = false;
				return;
			}
		}
	}
	
	public boolean IsAnyActive() {
		for (var iScene : listScene_) {
			if (iScene != null) return true;
		}
		return false;
	}
}
