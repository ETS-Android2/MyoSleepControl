package de.nachregenkommtsonne.myoarengine;

public class Vector {
	private float[] p = new float[3];

	public Vector(float x, float y, float z) {
		this.p[0] = x;
		this.p[1] = y;
		this.p[2] = z;
	}

	public Vector() {
	}

	public Vector(Vector v) {
		p = v.p.clone();
	}

	public Vector(float p[]) {
		if (p.length != 3)
			throw new RuntimeException("Not a 3 dimensional vector");
		
		this.p = p.clone();
	}

	public float getX() {
		return p[0];
	}

	public float getY() {
		return p[1];
	}

	public float getZ() {
		return p[2];
	}

	public void add(Vector v) {
		if (v == null)
			return;
		this.p[0] += v.p[0];
		this.p[1] += v.p[1];
		this.p[2] += v.p[2];
	}

	public void sub(Vector v) {
		if (v == null)
			return;
		this.p[0] -= v.p[0];
		this.p[1] -= v.p[1];
		this.p[2] -= v.p[2];
	}

	public Vector cross(Vector v) {
		Vector ret = new Vector(0, 0, 0);
		ret.p[0] = this.p[1] * v.p[2] - this.p[2] * v.p[1];
		ret.p[1] = this.p[2] * v.p[0] - this.p[0] * v.p[2];
		ret.p[2] = this.p[0] * v.p[1] - this.p[1] * v.p[0];
		ret.normalize();
		return ret;
	}

	public float[] getValues() {
		return p;
	}

	public void mult(float m) {
		this.p[0] *= m;
		this.p[1] *= m;
		this.p[2] *= m;
	}

	public void div(float m) {
		this.p[0] /= m;
		this.p[1] /= m;
		this.p[2] /= m;
	}

	public void normalize() {
		float length = p[0] * p[0] + p[1] * p[1] + p[2] * p[2];
		this.div((float)Math.sqrt(length));
	}
}
