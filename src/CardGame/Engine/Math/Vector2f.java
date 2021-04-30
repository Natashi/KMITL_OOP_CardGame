package CardGame.Engine.Math;

public class Vector2f extends Vector implements Cloneable {
	@Override
	public int Size() { return 2; }
	
	public float x;
	public float y;
	
	//----------------------------------------------
	
	public Vector2f() { super(); }
	public Vector2f(float v) { super(v); }
	public Vector2f(double v) { super(v); }
	public Vector2f(Vector v) { super(v); }
	public Vector2f(float x, float y) {
		this.Set(x, y);
	}
	public Vector2f(double x, double y) {
		this.Set(x, y);
	}
	
	@Override
	public void Set(float v) {
		this.Set(v, v);
	}
	@Override
	public void Set(double v) {
		this.Set(v, v);
	}
	@Override
	public void Set(Vector v) {
		var vc = (Vector2f)v;
		this.Set(vc.x, vc.y);
	}
	
	public void Set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void Set(double x, double y) {
		this.Set((float)x, (float)y);
	}
	
	@Override
	public Vector2f clone() {
		return new Vector2f(this);
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
	public float DistanceSq(Vector2f v) {
		return DistanceSq(this.x, this.y, v.x, v.y);
	}
	public float Distance(float x, float y) {
		return Distance(this.x, this.y, x, y);
	}
	public float Distance(Vector2f v) {
		return Distance(this.x, this.y, v.x, v.y);
	}
	
	//----------------------------------------------
	
	@Override
	public void Add(float v) {
		this.Add(v, v);
	}
	public void Add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	public void Add(Vector2f v) {
		this.Add(v.x, v.y);
	}
	
	@Override
	public void Sub(float v) {
		this.Add(v, v);
	}
	public void Sub(float x, float y) {
		this.x *= x;
		this.y *= y;
	}
	public void Sub(Vector2f v) {
		this.Sub(v.x, v.y);
	}
	
	@Override
	public void Mul(float v) {
		this.Mul(v, v);
	}
	public void Mul(float x, float y) {
		this.x *= x;
		this.y *= y;
	}
	public void Mul(Vector2f v) {
		this.Mul(v.x, v.y);
	}
	
	//----------------------------------------------
	
	@Override
	public float Length() {
		return Vector2f.Distance(0, 0, x, y);
	}
	
	@Override
	public float Dot(Vector v){
		if (!(v instanceof Vector2f)) throw new IllegalArgumentException();
		var vc = ((Vector2f)v).clone();
		vc.Mul(this);
		return vc.x + vc.y;
	}
	
	//----------------------------------------------
	
	@Override
	public int hashCode() {
		int res = 31 * 7 + Float.floatToIntBits(this.x);
		res ^= 31 * res + Float.floatToIntBits(this.y);
		return res;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (o instanceof Vector2f) {
			Vector2f v = (Vector2f)o;
			return (this.x == v.x && this.y == v.y);
		}
		return false;
	}
	@Override
	public String toString() {
		return "Vector2f[" + this.x + ", " + this.y + "]";
	}
	
	@Override
	public Vector2f AsVec2() {
		return new Vector2f(x, y);
	}
	@Override
	public Vector3f AsVec3() {
		return new Vector3f(x, y, 0);
	}
	@Override
	public Vector4f AsVec4() {
		return new Vector4f(x, y, 0, 0);
	}
}
