package mathj;

import app.Context;

public interface MathJ {
	
	static float dot(Vector3f a, Vector3f b) {
		return a.x * b.x + a.y * b.y;
	}
	
	static Vector3f sum2d(Vector3f a, Vector3f b) {
		return new Vector3f(a.x + b.x, a.y + b.y);
	}
	
	static Vector3f neg2d(Vector3f a) {
		return new Vector3f(-a.x, -a.y);
	} 
	
	static Vector3f diffrence2d(Vector3f a, Vector3f b) {
		return sum2d(a, neg2d(b));
	}

	static boolean isInRect(Vector3f point, Rect rect) {
		return rect.getXDomain().isInDomain(point.x) == 0 && rect.getYDomain().isInDomain(point.y) == 0;
	}

	static boolean isInRect(Rect rect1, Rect rect2) {
		for (int i = 0; i < 4; i++) {
			Vector3f corner = getRectCorner(i, rect1);
			if (rect2.getXDomain().isInDomain(corner.x) == 0 && rect2.getYDomain().isInDomain(corner.y) == 0) {
				return true;
			}
		}
		return false;
	}

	static Vector3f getRectCorner(int cornerId, Rect rect) {
		return switch (cornerId) {
			case (0) -> rect.getPosition();
			case (1) -> MathJ.sum2d(rect.getPosition(), new Vector3f(0, rect.height));
			case (2) -> MathJ.sum2d(rect.getPosition(), new Vector3f(rect.width, rect.height));
			case (3) -> MathJ.sum2d(rect.getPosition(), new Vector3f(rect.width, 0));
			default -> null;
		};
	}

//
//	static int worldToPixel(float size) {
//		return (int) (size * Context.TILE_SIZE); // 64 is tile size
//	}

	static float pixelToWorld(int size) {
		return (float) size / Context.TILE_SIZE; // 64 is tile size
	}
}
