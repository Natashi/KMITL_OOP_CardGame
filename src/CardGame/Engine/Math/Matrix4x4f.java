package CardGame.Engine.Math;

import javafx.scene.transform.Affine;

public class Matrix4x4f implements Cloneable {
	public Vector4f r1;
	public Vector4f r2;
	public Vector4f r3;
	public Vector4f r4;
	
	//----------------------------------------------
	
	public Matrix4x4f() {
		this.Set();
	}
	public Matrix4x4f(float m11, float m12, float m13, float m14,
					  float m21, float m22, float m23, float m24,
					  float m31, float m32, float m33, float m34,
					  float m41, float m42, float m43, float m44) {
		this.Set(
			m11, m12, m13, m14,
			m21, m22, m23, m24,
			m31, m32, m33, m34,
			m41, m42, m43, m44);
	}
	public Matrix4x4f(float[] v) {
		this.Set(v);
	}
	public Matrix4x4f(Vector4f r1, Vector4f r2, Vector4f r3, Vector4f r4) {
		this.Set(r1, r2, r3, r4);
	}
	public Matrix4x4f(Vector4f[] v) {
		this.Set(v[0], v[1], v[2], v[3]);
	}
	public Matrix4x4f(Matrix4x4f m) {
		this.Set(m);
	}
	
	public final void Set() {
		this.Set(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1);
	}
	public final void Set(float m11, float m12, float m13, float m14,
						  float m21, float m22, float m23, float m24,
						  float m31, float m32, float m33, float m34,
						  float m41, float m42, float m43, float m44) {
		this.r1 = new Vector4f(m11, m12, m13, m14);
		this.r2 = new Vector4f(m21, m22, m23, m24);
		this.r3 = new Vector4f(m31, m32, m33, m34);
		this.r4 = new Vector4f(m41, m42, m43, m44);
	}
	public final void Set(float[] v) {
		this.Set(
			v[0], v[1], v[2], v[3],
			v[4], v[5], v[6], v[7],
			v[8], v[9], v[10], v[11],
			v[12], v[13], v[14], v[15]);
	}
	public final void Set(Vector4f r1, Vector4f r2, Vector4f r3, Vector4f r4) {
		this.Set(
			r1.x, r1.y, r1.z, r1.w,
			r2.x, r2.y, r2.z, r2.w,
			r3.x, r3.y, r3.z, r3.w,
			r4.x, r4.y, r4.z, r4.w);
	}
	public final void Set(Matrix4x4f m) {
		this.Set(m.r1, m.r2, m.r3, m.r4);
	}
	
	@Override
	public Matrix4x4f clone() {
		return new Matrix4x4f(this);
	}
	
	//----------------------------------------------
	
	public final void SetRow(int row, float x, float y, float z, float w) {
		final Vector4f[] listRows = new Vector4f[] { r1, r2, r3, r4 };
		switch (row) {
			case 0:
			case 1:
			case 2:
			case 3:
				break;
			default:
				throw new ArrayIndexOutOfBoundsException("Matrix4x4f::SetRow");
		}
		listRows[row].Set(x, y, z, w);
	}
	public final void SetRow(int row, float[] v) {
		this.SetRow(row, v[0], v[1], v[2], v[3]);
	}
	public final void SetRow(int row, Vector4f v) {
		this.SetRow(row, v.x, v.y, v.z, v.w);
	}
	
	public final void GetRow(int row, Vector4f v) {
		final Vector4f[] listRows = new Vector4f[] { r1, r2, r3, r4 };
		switch (row) {
			case 0:
			case 1:
			case 2:
			case 3:
				break;
			default:
				throw new ArrayIndexOutOfBoundsException("Matrix4x4f::SetRow");
		}
		v = listRows[row].clone();
	}
	public final void GetRow(int row, float[] v) {
		Vector4f res = new Vector4f();
		this.GetRow(row, res);
		v[0] = res.x;
		v[1] = res.y;
		v[2] = res.z;
		v[3] = res.w;
	}
	public final Vector4f GetRow(int row) {
		Vector4f res = new Vector4f();
		this.GetRow(row, res);
		return res;
	}
	
	//----------------------------------------------
	
	public void Add(float v) {
		this.r1.Add(v);
		this.r2.Add(v);
		this.r3.Add(v);
		this.r4.Add(v);
	}
	public void Add(Matrix4x4f m) {
		this.r1.Add(m.r1);
		this.r2.Add(m.r2);
		this.r3.Add(m.r3);
		this.r4.Add(m.r4);
	}
	public static Matrix4x4f Add(Matrix4x4f m1, Matrix4x4f m2) {
		Matrix4x4f res = m1.clone();
		m1.Add(m2);
		return res;
	}
	
	public void Sub(float v) {
		this.r1.Sub(v);
		this.r2.Sub(v);
		this.r3.Sub(v);
		this.r4.Sub(v);
	}
	public void Sub(Matrix4x4f m) {
		this.r1.Sub(m.r1);
		this.r2.Sub(m.r2);
		this.r3.Sub(m.r3);
		this.r4.Sub(m.r4);
	}
	public static Matrix4x4f Sub(Matrix4x4f m1, Matrix4x4f m2) {
		Matrix4x4f res = m1.clone();
		m1.Sub(m2);
		return res;
	}
	
	//Scalar
	public void Mul(float v) {
		this.r1.Mul(v);
		this.r2.Mul(v);
		this.r3.Mul(v);
		this.r4.Mul(v);
	}
	//Matrix
	public void Mul(Matrix4x4f m) {
		Vector4f vecColX = new Vector4f(m.r1.x, m.r2.x, m.r3.x, m.r4.x);
		Vector4f vecColY = new Vector4f(m.r1.y, m.r2.y, m.r3.y, m.r4.y);
		Vector4f vecColZ = new Vector4f(m.r1.z, m.r2.z, m.r3.z, m.r4.z);
		Vector4f vecColW = new Vector4f(m.r1.w, m.r2.w, m.r3.w, m.r4.w);
		r1.Set(r1.Dot(vecColX), r1.Dot(vecColY), r1.Dot(vecColZ), r1.Dot(vecColW));
		r2.Set(r2.Dot(vecColX), r2.Dot(vecColY), r2.Dot(vecColZ), r2.Dot(vecColW));
		r3.Set(r3.Dot(vecColX), r3.Dot(vecColY), r3.Dot(vecColZ), r3.Dot(vecColW));
		r4.Set(r4.Dot(vecColX), r4.Dot(vecColY), r4.Dot(vecColZ), r4.Dot(vecColW));
	}
	public static Matrix4x4f Mul(Matrix4x4f m1, Matrix4x4f m2) {
		Matrix4x4f res = m1.clone();
		m1.Mul(m2);
		return res;
	}
	
	//----------------------------------------------
	
	public int hashCode() {
		int res = r1.hashCode();
		res ^= 31 * res + r2.hashCode();
		res ^= 31 * res + r3.hashCode();
		res ^= 31 * res + r4.hashCode();
		return res;
	}
	public String toString() {
		return String.format(
			"Matrix4x4f[[%f,%f,%f,%f],[%f,%f,%f,%f],[%f,%f,%f,%f],[%f,%f,%f,%f]]",
			r1.x, r1.y, r1.z, r1.w,
			r2.x, r2.y, r2.z, r2.w,
			r3.x, r3.y, r3.z, r3.w,
			r4.x, r4.y, r4.z, r4.w);
	}
	
	public Affine ToAffine() {
		return new Affine(
			r1.x, r1.y, r1.z, r1.w,
			r2.x, r2.y, r2.z, r2.w,
			r3.x, r3.y, r3.z, r3.w);
	}
}
