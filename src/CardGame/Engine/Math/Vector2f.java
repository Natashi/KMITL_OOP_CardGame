package CardGame.Engine.Math;

import com.sun.javafx.geom.Vec2f;

public class Vector2f {
	public float x;
	public float y;
	
	//----------------------------------------------
	
	public Vector2f() {
		this.Set(0);
	}
	public Vector2f(float v) {
		this.Set(v);
	}
	public Vector2f(float x, float y) {
		this.Set(x, y);
	}
	public Vector2f(double v) {
		this.Set(v);
	}
	public Vector2f(double x, double y) {
		this.Set(x, y);
	}
	public Vector2f(Vec2f v) {
		this.Set(v);
	}
	
	public void Set(float v) {
		this.Set(v, v);
	}
	public void Set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void Set(double v) {
		this.Set((float)v);
	}
	public void Set(double x, double y) {
		this.Set((float)x, (float)y);
	}
	public void Set(Vec2f v) {
		this.Set(v.x, v.y);
	}
	
	//----------------------------------------------
	
	public static float DistanceSq(float x1, float y1, float x2, float y2) {
		x1 -= x2;
		y1 -= y2;
		return (x1 * x1 + y1 * y1);
	}
	public static float Distance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Vector2f.DistanceSq(x1, y1, x2, y2));
	}
	
	public float DistanceSq(float x, float y) {
		return DistanceSq(this.x, this.y, x, y);
	}
	public float DistanceSq(Vec2f v) {
		return DistanceSq(this.x, this.y, v.x, v.y);
	}
	public float Distance(float x, float y) {
		return Distance(this.x, this.y, x, y);
	}
	public float Distance(Vec2f v) {
		return Distance(this.x, this.y, v.x, v.y);
	}
	
	//----------------------------------------------
	
	public final void Add(float v) {
		this.x += v;
		this.y += v;
	}
	public final void Add(float x, float y) {
		this.Mul(x, y);
	}
	public final void Add(Vec2f v) {
		this.Mul(v.x, v.y);
	}
	public final void Add(Vector2f v) {
		this.Mul(v.x, v.y);
	}
	
	public final void Sub(float v) {
		this.x -= v;
		this.y -= v;
	}
	public final void Sub(float x, float y) {
		this.Mul(x, y);
	}
	public final void Sub(Vec2f v) {
		this.Mul(v.x, v.y);
	}
	public final void Sub(Vector2f v) {
		this.Mul(v.x, v.y);
	}
	
	public final void Mul(float v) {
		this.x *= v;
		this.y *= v;
	}
	public final void Mul(float x, float y) {
		this.Mul(x, y);
	}
	public final void Mul(Vec2f v) {
		this.Mul(v.x, v.y);
	}
	public final void Mul(Vector2f v) {
		this.Mul(v.x, v.y);
	}
	
	//----------------------------------------------
	
	public int hashCode() {
		byte var1 = 7;
		int var2 = 31 * var1 + Float.floatToIntBits(this.x);
		var2 = 31 * var2 + Float.floatToIntBits(this.y);
		return var2;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else {
			if (o instanceof Vector2f) {
				Vector2f v = (Vector2f)o;
				return (this.x == v.x && this.y == v.y);
			}
			else if (o instanceof Vec2f) {
				Vec2f v = (Vec2f)o;
				return (this.x == v.x && this.y == v.y);
			}
		}
		return false;
	}
	public String toString() {
		return "Vector2f[" + this.x + ", " + this.y + "]";
	}
	
	public Vec2f ToJFXVec2() {
		return new Vec2f(this.x, this.y);
	}
}
