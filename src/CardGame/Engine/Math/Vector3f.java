package CardGame.Engine.Math;

public class Vector3f  extends Vector implements Cloneable {
	@Override
	public int Size() { return 3; }
	
	public float x;
	public float y;
	public float z;
	
	//----------------------------------------------
	
	public Vector3f() { super(); }
	public Vector3f(float v) { super(v); }
	public Vector3f(double v) { super(v); }
	public Vector3f(Vector v) { super(v); }
	public Vector3f(float x, float y, float z) {
		this.Set(x, y, z);
	}
	public Vector3f(double x, double y, double z) {
		this.Set(x, y, z);
	}
	
	@Override
	public void Set(float v) {
		this.Set(v, v, v);
	}
	@Override
	public void Set(double v) {
		this.Set(v, v, v);
	}
	@Override
	public void Set(Vector v) {
		var vc = (Vector3f)v;
		this.Set(vc.x, vc.y, vc.z);
	}
	
	public void Set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void Set(double x, double y, double z) {
		this.Set((float)x, (float)y, (float)z);
	}
	
	@Override
	public Vector3f clone() {
		return new Vector3f(this);
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
	public float Distance(float x, float y, float z) {
		return Distance(this.x, this.y, this.z, x, y, z);
	}
	public float Distance(Vector3f v) {
		return Distance(this.x, this.y, this.z, v.x, v.y, v.z);
	}
	
	//----------------------------------------------
	
	@Override
	public void Add(float v) {
		this.Add(v, v, v);
	}
	public void Add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void Add(Vector3f v) {
		this.Add(v.x, v.y, v.z);
	}
	
	@Override
	public void Sub(float v) {
		this.Sub(v, v, v);
	}
	public void Sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void Sub(Vector3f v) {
		this.Sub(v.x, v.y, v.z);
	}
	
	@Override
	public void Mul(float v) {
		this.Mul(v, v, v);
	}
	public void Mul(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}
	public void Mul(Vector3f v) {
		this.Mul(v.x, v.y, v.z);
	}
	
	//----------------------------------------------
	
	@Override
	public float Length() {
		return Vector3f.Distance(0, 0, 0, x, y, z);
	}
	
	@Override
	public float Dot(Vector v){
		if (!(v instanceof Vector3f)) throw new IllegalArgumentException();
		var vc = ((Vector3f)v).clone();
		vc.Mul(this);
		return vc.x + vc.y + vc.z;
	}
	
	public Vector3f Cross(Vector3f v1, Vector3f v2) {
		Vector3f res = new Vector3f();
		res.x = v1.y * v2.z - v1.z * v2.y;
		res.y = v2.x * v1.z - v2.z * v1.x;
		res.z = v1.x * v2.y - v1.y * v2.x;
		return res;
	}
	
	//----------------------------------------------
	
	@Override
	public int hashCode() {
		int res = 31 * 7 + Float.floatToIntBits(this.x);
		res ^= 31 * res + Float.floatToIntBits(this.y);
		res ^= 31 * res + Float.floatToIntBits(this.z);
		return res;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (o instanceof Vector3f) {
			Vector3f v = (Vector3f)o;
			return (this.x == v.x && this.y == v.y && this.z == v.z);
		}
		return false;
	}
	@Override
	public String toString() {
		return "Vector3f[" + this.x + ", " + this.y + ", " + this.z + "]";
	}
	
	@Override
	public Vector2f AsVec2() {
		return new Vector2f(x, y);
	}
	@Override
	public Vector3f AsVec3() {
		return new Vector3f(x, y, z);
	}
	@Override
	public Vector4f AsVec4() {
		return new Vector4f(x, y, z, 0);
	}
}
