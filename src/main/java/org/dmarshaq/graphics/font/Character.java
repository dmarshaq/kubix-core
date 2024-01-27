package org.dmarshaq.graphics.font;

public class Character {

	private final int id;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int xoffset;
	private final int yoffset;
	private final int xadvance;

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getXoffset() {
		return xoffset;
	}

	public int getYoffset() {
		return yoffset;
	}

	public int getXadvance() {
		return xadvance;
	}

	public Character(int id, int x, int y, int width, int height, int xoffset, int yoffset, int xadvance) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xadvance = xadvance;
	}
}
