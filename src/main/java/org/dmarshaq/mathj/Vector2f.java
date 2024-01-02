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

	public void copyValues(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
	}

	public String toString() {
		return "( " + x + " , " + y + " )";
	}
}
