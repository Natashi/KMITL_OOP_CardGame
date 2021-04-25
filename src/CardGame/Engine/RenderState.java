package CardGame.Engine;

import CardGame.Engine.Math.*;

import javafx.scene.canvas.GraphicsContext;

public class RenderState {
	public Vector2f position_;
	public Vector2f scale_;
	public Vector3f angle_;
	
	private Vector2f _ax = new Vector2f();
	private Vector2f _ay = new Vector2f();
	private Vector2f _az = new Vector2f();
	
	private Matrix3x3f _mat = new Matrix3x3f();
	
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
		position_ = pos;
		scale_ = scale;
		angle_ = angle;
		LoadAngle();
	}
	
	public void LoadAngle() {
		_ax.Set(Math.sin(angle_.x), Math.cos(angle_.x));
		_ay.Set(Math.sin(angle_.y), Math.cos(angle_.y));
		_az.Set(Math.sin(angle_.z), Math.cos(angle_.z));
	}
	
	public void LoadMat() {
		Vector3f tmp = new Vector3f();
		
		tmp.Set(_ay.y * _az.y - _ax.x * _ay.x * _az.x,
			-_ax.y * _az.x,
			_ay.x * _az.y + _ax.x * _ay.y * _az.x);
		tmp.Mul(scale_.x);
		_mat.SetRow(0, tmp);
		
		tmp.Set(_ay.y * _az.x + _ax.x * _ay.x * _az.y,
			_ax.y * _az.y,
			_ay.x * _az.x - _ax.x * _ay.y * _az.y);
		tmp.Mul(scale_.y);
		_mat.SetRow(1, tmp);
		
		tmp.Set(0, 0, 1);
		_mat.SetRow(2, tmp);
	}
	public void Apply(GraphicsContext context) {
		context.setTransform(_mat.m11, _mat.m12, _mat.m21, _mat.m22,
			position_.x, position_.y);
	}
	public void LoadMatAndApply(GraphicsContext context) {
		this.LoadMat();
		this.Apply(context);
	}
}
