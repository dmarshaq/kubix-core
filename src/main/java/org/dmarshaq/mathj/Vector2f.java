package org.dmarshaq.mathj;

public final class Vector2f {
	public float x, y;
	
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void multiply(float value) {
		x *= value;
		y *= value;
	}

	public void divide(float value) {
		x /= value;
		y /= value;
	}

	public void add(float value) {
		x += value;
		y += value;
	}

	public void add(Vector2f vector) {
		x += vector.x;
		y += vector.y;
	}

	public void negate() {
		multiply(-1.0f);
	}

	public void subtract(float value) {
		x -= value;
		y -= value;
	}

	public void subtract(Vector2f vector) {
		x -= vector.x;
		y -= vector.y;
	}

	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		divide( magnitude() );
	}

	public void copyValues(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
	}

	public static Vector2f duplicate(Vector2f v) {
		if (v == null) {
			return null;
		}
		return new Vector2f(v.x, v.y);
	}

	public String toString() {
		return "( " + x + " , " + y + " )";
	}
}
