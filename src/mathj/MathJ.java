package mathj;

import app.GameContext;

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

	// EASING functions
	enum Easing {
		LINEAR,
		EASE_IN_OUT_SINE,
		EASE_IN_OUT_BOUNCE,
		EASE_OUT_BOUNCE;

		public static float applyEasing(float x, Easing type) {
			return switch (type) {
				case EASE_IN_OUT_SINE -> easeInOutSine(x);
				case EASE_IN_OUT_BOUNCE -> easeInOutBounce(x);
				case EASE_OUT_BOUNCE -> easeOutBounce(x);
				default -> linear(x);
			};
        }

		private static float linear(float x) {
			return x;
		}

		private static float easeInOutSine(float x) {
			return (float) (-(Math.cos(Math.PI * x) - 1) / 2);
		}

		private static float easeInOutBounce(float x) {
			if (x < 0.5) {
				return (1 - easeOutBounce(1 - 2 * x)) / 2;
			}
			return (1 + easeOutBounce(2 * x - 1)) / 2;
		}

		private static float easeOutBounce(float x) {
			float n1 = 7.5625f;
			float d1 = 2.75f;

			if (x < 1 / d1) {
				return n1 * x * x;
			} else if (x < 2 / d1) {
				return n1 * (x -= 1.5f / d1) * x + 0.75f;
			} else if (x < 2.5 / d1) {
				return n1 * (x -= 2.25f / d1) * x + 0.9375f;
			} else {
				return n1 * (x -= 2.625f / d1) * x + 0.984375f;
			}
		}
	}
//
//	static int worldToPixel(float size) {
//		return (int) (size * Context.TILE_SIZE); // 64 is tile size
//	}

	static float pixelToWorld(int size) {
		return (float) size / GameContext.TILE_SIZE; // 64 is tile size
	}
}
