package org.dmarshaq.mathj;

import static org.dmarshaq.mathj.MathJ.Math2D;

public class Rect {
	private Vector2f position;
	public float width;
	public float height;
	
	public Rect() {
		this.position = new Vector2f();
		this.width = 0;
		this.height = 0;
	}
	
	public Rect(float x, float y, float width, float height) {
		this.position = new Vector2f(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Rect(Vector2f pos, float width, float height) {
		this.position = new Vector2f(pos.x, pos.y);
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
	
	public Vector2f getCenter() {
		return Math2D.sum(position, new Vector2f(width/2, height/2));
	}

	public void setCenter(Vector2f pos) {
		position.copyValues(Math2D.sum(pos, new Vector2f(width/-2, height/-2)));
	}

	public Vector2f getPosition() {
		return new Vector2f(position.x, position.y);
	}

	protected Vector2f getPositionObject() {
		return position;
	}

	public float x() {
		return position.x;
	}

	public float y() {
		return position.y;
	}

	public void setPosition(Vector2f pos) {
		position.copyValues(pos);
	}

	public boolean touchesRect(Vector2f point) {
		return getXDomain().isInDomain(point.x) == 0 && getYDomain().isInDomain(point.y) == 0;
	}

	public boolean touchesRect(Rect rect) {
		for (int i = 0; i < 4; i++) {
			Vector2f corner = rect.getRectCorner(i);
			if (getXDomain().isInDomain(corner.x) == 0 && getYDomain().isInDomain(corner.y) == 0) {
				return true;
			}
		}
		return false;
	}

	public Vector2f getRectCorner(int cornerId) {
		return switch (cornerId) {
			case (0) -> getPosition();
			case (1) -> Math2D.sum(getPosition(), new Vector2f(0, height));
			case (2) -> Math2D.sum(getPosition(), new Vector2f(width, height));
			case (3) -> Math2D.sum(getPosition(), new Vector2f(width, 0));
			default -> null;
		};
	}

}
