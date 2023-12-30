package org.dmarshaq.mathj;

public final class Vector3int {
	public int x, y, z;
	
	public Vector3int() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3int(int x, int y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public Vector3int(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void copyValues(Vector3int v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public String toString() {
		return "( " + x + " , " + y + ", " + z + " )";
	}
}
