package CardGame.Engine.Math;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	
	//----------------------------------------------
	
	public Vector3f() {
		this.Set(0);
	}
	public Vector3f(float v) {
		this.Set(v);
	}
	public Vector3f(float x, float y, float z) {
		this.Set(x, y, z);
	}
	public Vector3f(double v) {
		this.Set(v);
	}
	public Vector3f(double x, double y, double z) {
		this.Set(x, y, z);
	}
	public Vector3f(Vector2f v) {
		this.Set(v);
	}
	public Vector3f(Vec2f v) {
		this.Set(v);
	}
	public Vector3f(Vec3f v) {
		this.Set(v);
	}
	
	public void Set(float v) {
		this.Set(v, v, v);
	}
	public void Set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void Set(double v) {
		this.Set((float)v);
	}
	public void Set(double x, double y, double z) {
		this.Set((float)x, (float)y, (float)z);
	}
	public void Set(Vector2f v) {
		this.Set(v.x, v.y, 0);
	}
	public void Set(Vec2f v) {
		this.Set(v.x, v.y, 0);
	}
	public void Set(Vec3f v) {
		this.Set(v.x, v.y, v.z);
	}
	
	//----------------------------------------------
	
	public static float DistanceSq(float x1, float y1, float z1, float x2, float y2, float z2) {
		x1 -= x2;
		y1 -= y2;
		z1 -= z2;
		return (x1 * x1 + y1 * y1 + z1 * z1);
	}
	public static float Distance(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (float)Math.sqrt(Vector3f.DistanceSq(x1, y1, z1, x2, y2, z2));
	}
	
	public float DistanceSq(float x, float y, float z) {
		return DistanceSq(this.x, this.y, this.z, x, y, z);
	}
	public float DistanceSq(Vector3f v) {
		return DistanceSq(this.x, this.y, this.z, v.x, v.y, v.z);
	}
	public float DistanceSq(Vec3f v) {
		return DistanceSq(this.x, this.y, this.z, v.x, v.y, v.z);
	}
	public float Distance(float x, float y, float z) {
		return Distance(this.x, this.y, this.z, x, y, z);
	}
	public float Distance(Vector3f v) {
		return Distance(this.x, this.y, this.z, v.x, v.y, v.z);
	}
	public float Distance(Vec3f v) {
		return Distance(this.x, this.y, this.z, v.x, v.y, v.z);
	}
	
	public float Length() {
		return this.Distance(0, 0, 0);
	}
	public void Normalize() {
		float d = 1.0F / this.Length();
		this.x *= d;
		this.y *= d;
		this.z *= d;
	}
	
	public float Dot(Vector3f v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}
	public void Cross(Vector3f v1, Vector3f v2) {
		this.x = v1.y * v2.z - v1.z * v2.y;
		this.y = v2.x * v1.z - v2.z * v1.x;
		this.z = v1.x * v2.y - v1.y * v2.x;
	}
	
	//----------------------------------------------
	
	public final void Add(float v) {
		this.x += v;
		this.y += v;
		this.z += v;
	}
	public final void Add(float x, float y, float z) {
		this.Mul(x, y, x);
	}
	public final void Add(Vec3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	public final void Add(Vector3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	
	public final void Sub(float v) {
		this.x -= v;
		this.y -= v;
	}
	public final void Sub(float x, float y, float z) {
		this.Mul(x, y, x);
	}
	public final void Sub(Vec3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	public final void Sub(Vector3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	
	public final void Mul(float v) {
		this.x *= v;
		this.y *= v;
	}
	public final void Mul(float x, float y, float z) {
		this.Mul(x, y, x);
	}
	public final void Mul(Vec3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	public final void Mul(Vector3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	
	//----------------------------------------------
	
	public int hashCode() {
		byte var1 = 7;
		int var2 = 31 * var1 + Float.floatToIntBits(this.x);
		var2 = 31 * var2 + Float.floatToIntBits(this.y);
		var2 = 31 * var2 + Float.floatToIntBits(this.z);
		return var2;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else {
			if (o instanceof Vector3f) {
				Vector3f v = (Vector3f)o;
				return (this.x == v.x && this.y == v.y && this.z == v.z);
			}
			else if (o instanceof Vec3f) {
				Vec3f v = (Vec3f)o;
				return (this.x == v.x && this.y == v.y && this.z == v.z);
			}
		}
		return false;
	}
	public String toString() {
		return "Vector3f[" + this.x + ", " + this.y + ", " + this.z + "]";
	}
	
	public Vec3f ToJFXVec3() {
		return new Vec3f(this.x, this.y, this.z);
	}
}
