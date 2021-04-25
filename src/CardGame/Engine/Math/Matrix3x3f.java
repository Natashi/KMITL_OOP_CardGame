package CardGame.Engine.Math;

public class Matrix3x3f {
	public float m11;
	public float m12;
	public float m13;
	public float m21;
	public float m22;
	public float m23;
	public float m31;
	public float m32;
	public float m33;
	
	//----------------------------------------------
	
	public Matrix3x3f() {
		this.Set();
	}
	public Matrix3x3f(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
		this.Set(m11, m12, m13, m21, m22, m23, m31, m32, m33);
	}
	public Matrix3x3f(float[] v) {
		this.Set(v);
	}
	public Matrix3x3f(Vector3f[] v) {
		this.Set(v);
	}
	public Matrix3x3f(Matrix3x3f m) {
		this.Set(m);
	}
	
	public final void Set() {
		this.Set(1, 0, 0,
			0, 1, 0,
			0, 0, 1);
	}
	public final void Set(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m11 = m21;
		this.m12 = m22;
		this.m13 = m23;
		this.m11 = m31;
		this.m12 = m32;
		this.m13 = m33;
	}
	public final void Set(float[] v) {
		this.Set(v[0], v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
	}
	public final void Set(Vector3f[] v) {
		this.Set(v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z);
	}
	public final void Set(Matrix3x3f m) {
		this.Set(m.m11, m.m12, m.m13, m.m21, m.m22, m.m23, m.m31, m.m32, m.m33);
	}
	
	//----------------------------------------------
	
	public final void SetRow(int row, float x, float y, float z) {
		switch (row) {
			case 0:
				this.m11 = x;
				this.m12 = y;
				this.m13 = z;
				return;
			case 1:
				this.m21 = x;
				this.m22 = y;
				this.m23 = z;
				return;
			case 2:
				this.m31 = x;
				this.m32 = y;
				this.m33 = z;
				return;
		}
		throw new ArrayIndexOutOfBoundsException("Matrix3x3f::SetRow");
	}
	public final void SetRow(int row, float[] v) {
		this.SetRow(row, v[0], v[1], v[2]);
	}
	public final void SetRow(int row, Vector3f v) {
		this.SetRow(row, v.x, v.y, v.z);
	}
	
	public final void GetRow(int row, Vector3f v) {
		switch (row) {
			case 0:
				v.x = this.m11;
				v.y = this.m12;
				v.z = this.m13;
				return;
			case 1:
				v.x = this.m21;
				v.y = this.m22;
				v.z = this.m23;
				return;
			case 2:
				v.x = this.m31;
				v.y = this.m32;
				v.z = this.m33;
				return;
		}
		throw new ArrayIndexOutOfBoundsException("Matrix3x3f::GetRow");
	}
	public final void GetRow(int row, float[] v) {
		Vector3f res = new Vector3f();
		this.GetRow(row, res);
		v[0] = res.x;
		v[1] = res.y;
		v[2] = res.z;
	}
	public final Vector3f GetRow(int row) {
		Vector3f res = new Vector3f();
		this.GetRow(row, res);
		return res;
	}
	
	//----------------------------------------------
	
	public String toString() {
		return String.format("Matrix3x3f[[%f,%f,%f],[%f,%f,%f],[%f,%f,%f]]",
			m11, m12, m13, m21, m22, m23, m31, m32, m33);
	}
}
