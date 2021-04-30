package CardGame.Engine.Math;

public abstract class Vector {
	public int Size() { return 0; }
	
	public Vector() {
		this.Set(0);
	}
	public Vector(float v) {
		this.Set(v);
	}
	public Vector(double v) {
		this.Set(v);
	}
	public Vector(Vector v) { this.Set(v); }
	
	public abstract void Set(float v);
	public abstract void Set(double v);
	public abstract void Set(Vector v);
	
	//----------------------------------------------
	
	public abstract void Add(float v);
	public abstract void Sub(float v);
	public abstract void Mul(float v);
	
	//----------------------------------------------
	
	public abstract float Length();
	public void Normalize() {
		this.Mul(1.0f / this.Length());
	}
	public abstract float Dot(Vector v);
	
	//----------------------------------------------
	
	public abstract int hashCode();
	public abstract boolean equals(Object o);
	public String toString() {
		return "Vector[null]";
	}
	
	public abstract Vector2f AsVec2();
	public abstract Vector3f AsVec3();
	public abstract Vector4f AsVec4();
}
