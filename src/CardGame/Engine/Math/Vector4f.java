package CardGame.Engine.Math;

public class Vector4f  extends Vector implements Cloneable {
	@Override
	public int Size() { return 4; }
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	//----------------------------------------------
	
	public Vector4f() { super(); }
	public Vector4f(float v) { super(v); }
	public Vector4f(double v) { super(v); }
	public Vector4f(Vector v) { super(v); }
	public Vector4f(float x, float y, float z, float w) {
		this.Set(x, y, z, w);
	}
	public Vector4f(double x, double y, double z, double w) {
		this.Set(x, y, z, w);
	}
	
	@Override
	public void Set(float v) {
		this.Set(v, v, v, v);
	}
	@Override
	public void Set(double v) {
		this.Set(v, v, v, v);
	}
	@Override
	public void Set(Vector v) {
		var vc = (Vector4f)v;
		this.Set(vc.x, vc.y, vc.z, vc.w);
	}
	
	public void Set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public void Set(double x, double y, double z, double w) {
		this.Set((float)x, (float)y, (float)z, (float)w);
	}
	
	@Override
	public Vector4f clone() {
		return new Vector4f(this);
	}
	
	//----------------------------------------------
	
	public static float DistanceSq(float x1, float y1, float z1, float w1,
								   float x2, float y2, float z2, float w2)
	{
		x1 -= x2;
		y1 -= y2;
		z1 -= z2;
		w1 -= w2;
		return (x1 * x1 + y1 * y1 + z1 * z1 + w1 * w1);
	}
	public static float Distance(float x1, float y1, float z1, float w1,
								 float x2, float y2, float z2, float w2)
	{
		return (float)Math.sqrt(Vector4f.DistanceSq(x1, y1, z1, w1, x2, y2, z2, w2));
	}
	
	public float DistanceSq(float x, float y, float z, float w) {
		return DistanceSq(this.x, this.y, this.z, this.w, x, y, z, w);
	}
	public float DistanceSq(Vector4f v) {
		return DistanceSq(this.x, this.y, this.z, this.w, v.x, v.y, v.z, v.w);
	}
	public float Distance(float x, float y, float z, float w) {
		return Distance(this.x, this.y, this.z, this.w, x, y, z, w);
	}
	public float Distance(Vector4f v) {
		return Distance(this.x, this.y, this.z, this.w, v.x, v.y, v.z, v.w);
	}
	
	//----------------------------------------------
	
	@Override
	public void Add(float v) {
		this.Add(v, v, v, v);
	}
	public void Add(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
	}
	public void Add(Vector4f v) {
		this.Add(v.x, v.y, v.z, v.w);
	}
	
	@Override
	public void Sub(float v) {
		this.Sub(v, v, v, v);
	}
	public void Sub(float x, float y, float z, float w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
	}
	public void Sub(Vector4f v) {
		this.Sub(v.x, v.y, v.z, v.w);
	}
	
	@Override
	public void Mul(float v) {
		this.Mul(v, v, v, v);
	}
	public void Mul(float x, float y, float z, float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
	}
	public void Mul(Vector4f v) {
		this.Mul(v.x, v.y, v.z, v.w);
	}
	
	//----------------------------------------------
	
	@Override
	public float Length() {
		return Vector4f.Distance(0, 0, 0, 0, x, y, z, w);
	}
	
	@Override
	public float Dot(Vector v){
		if (!(v instanceof Vector4f)) throw new IllegalArgumentException();
		var vc = ((Vector4f)v).clone();
		vc.Mul(this);
		return vc.x + vc.y + vc.z + vc.w;
	}
	
	//----------------------------------------------
	
	@Override
	public int hashCode() {
		int res = 31 * 7 + Float.floatToIntBits(this.x);
		res ^= 31 * res + Float.floatToIntBits(this.y);
		res ^= 31 * res + Float.floatToIntBits(this.z);
		res ^= 31 * res + Float.floatToIntBits(this.w);
		return res;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (o instanceof Vector4f) {
			Vector4f v = (Vector4f)o;
			return (this.x == v.x && this.y == v.y && this.z == v.z && this.w == v.w);
		}
		return false;
	}
	@Override
	public String toString() {
		return "Vector4f[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w +"]";
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
		return new Vector4f(x, y, z, w);
	}
}
