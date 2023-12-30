package graphics.font;

public class Character {

	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private int xoffset;
	private int yoffset;
	private int xadvance;

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
