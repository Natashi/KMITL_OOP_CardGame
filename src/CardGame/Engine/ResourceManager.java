package CardGame.Engine;

import javafx.scene.Scene;

import java.io.*;
import java.lang.*;
import java.util.*;

public class ResourceManager {
	private static ResourceManager base_ = null;
	private HashMap<String, Object> mapResource_;
	
	//----------------------------------------------
	
	public ResourceManager() {
	}
	
	public void Initialize() throws EngineError {
		if (base_ != null) throw new EngineError("ResourceManager already initialized.");
		base_ = this;
		
		mapResource_ = new HashMap<>();
	}
	
	public static ResourceManager GetBase() {
		return base_;
	}
	
	public Object AddResource(String name, Object rsrc) {
		mapResource_.put(name, rsrc);
		return rsrc;
	}
	public void RemoveResource(String name) {
		mapResource_.remove(name);
	}
	public Object GetResource(String name) {
		return mapResource_.get(name);
	}
}
