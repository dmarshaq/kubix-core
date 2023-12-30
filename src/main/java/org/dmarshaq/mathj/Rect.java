package org.dmarshaq.mathj;

public class Rect {
	private Vector3f position;
	public float width;
	public float height;
	
	public Rect() {
		this.position = new Vector3f();
		this.width = 0;
		this.height = 0;
	}
	
	public Rect(float x, float y, float width, float height, float z) {
		this.position = new Vector3f(x, y, z);
		this.width = width;
		this.height = height;
	}
	
	public Rect(Vector3f pos, float width, float height) {
		this.position = new Vector3f(pos.x, pos.y, pos.z);
		this.width = width;
		this.height = height;
	}

	public Rect(Rect rect) {
		position = rect.getPosition();
		this.width = rect.width;
		this.height = rect.height;
	}
	
	public String toString() {
		return "at: " + position + " width: " + width + " height: " + height;
	}

	public Domain getXDomain() {
		return new Domain(position.x, position.x + width);
	}

	public Domain getYDomain() {
		return new Domain(position.y, position.y + height);
	}
	
	public Vector3f getCenter() {
		return MathJ.sum2d(position, new Vector3f(width/2, height/2));
	}

	public void setCenter(Vector3f pos) {
		position.copyValues(MathJ.sum2d(pos, new Vector3f(width/-2, height/-2)));
	}

	public Vector3f getPosition() {
		return new Vector3f(position.x, position.y, position.z);
	}

	protected Vector3f getPositionObject() {
		return position;
	}

	public float x() {
		return position.x;
	}

	public float y() {
		return position.y;
	}

	public void setPosition(Vector3f pos) {
		position.copyValues(pos);
	}

}
