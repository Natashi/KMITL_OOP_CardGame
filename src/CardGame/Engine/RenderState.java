package CardGame.Engine;

import CardGame.Engine.Math.*;

import CardGame.Main;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;

import javafx.scene.image.Image;
import javafx.scene.transform.*;

public class RenderState {
	public Vector2f origin_;
	public Vector2f position_;
	public Vector2f scale_;
	public Vector3f angle_;
	
	private Affine _affine = null;
	
	public BlendMode blendMode_;
	public int alpha_;
	
	//----------------------------------------------
	
	public RenderState() {
		this(new Vector2f(0, 0));
	}
	public RenderState(Vector2f pos) {
		this(pos, new Vector2f(1, 1));
	}
	public RenderState(Vector2f pos, Vector2f scale) {
		this(pos, scale, new Vector3f(0, 0, 0));
	}
	public RenderState(Vector2f pos, Vector2f scale, Vector3f angle) {
		origin_ = new Vector2f();
		position_ = pos;
		scale_ = scale;
		angle_ = angle;
		
		blendMode_ = BlendMode.SRC_OVER;
		alpha_ = 255;
	}
	
	public void LoadMat() {
		//Builds a transform matrix: scaling -> XYZ rotation -> translation
		
		_affine = new Affine();
		
		_affine.appendTranslation(position_.x - origin_.x, position_.y - origin_.y);
		
		_affine.appendRotation(angle_.x, origin_.x, origin_.y, 0, Rotate.X_AXIS);
		_affine.appendRotation(angle_.y, origin_.x, origin_.y, 0, Rotate.Y_AXIS);
		_affine.appendRotation(angle_.z, origin_.x, origin_.y, 0, Rotate.Z_AXIS);
		
		_affine.appendScale(scale_.x, scale_.y, origin_.x, origin_.y);
		
		//_affine.appendTranslation(position_.x, position_.y);
	}
	public void Apply(GraphicsContext context) {
		context.setTransform(_affine);
		context.setGlobalBlendMode(blendMode_);
		context.setGlobalAlpha(alpha_ / 255.0);
	}
	public void LoadMatAndApply(GraphicsContext context) {
		this.LoadMat();
		this.Apply(context);
	}
	public void Render(GraphicsContext context, Image img) {
		//context.drawImage(img, position_.x, position_.y);
		context.drawImage(img, 0, 0);
	}
}
