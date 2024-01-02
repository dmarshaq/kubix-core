package org.dmarshaq.mathj;

import org.dmarshaq.app.GameContext;

public interface MathJ {

	final class Math2D {
		public static float dot(Vector2f a, Vector2f b) {
			return a.x * b.x + a.y * b.y;
		}

		public static Vector2f sum(Vector2f a, Vector2f b) {
			return new Vector2f(a.x + b.x, a.y + b.y);
		}

		public static Vector3f sum(Vector3f a, Vector3f b, float layer) {
			return new Vector3f(a.x + b.x, a.y + b.y, layer);
		}

		public static void negate(Vector2f v) {
			v.x = -v.x;
			v.y = -v.y;
		}

		public static void negate(Vector3f v) {
			v.x = -v.x;
			v.y = -v.y;
		}

		public static Vector2f diffrence(Vector2f a, Vector2f b) {
			negate(b);
			return sum(a, b);
		}

		public static Vector3f diffrence(Vector3f a, Vector3f b, float layer) {
			negate(b);
			return sum(a, b, layer);
		}

		public static Vector2f toVector2f(Vector3f v) {
			return new Vector2f(v.x, v.y);
		}

		public static Vector3f toVector3f(Vector2f v, float layer) {
			return new Vector3f(v.x, v.y, layer);
		}
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


	static int worldToPixel(float size) {
		return (int) (size * GameContext.TILE_SIZE); // 64 is tile size
	}

	static float pixelToWorld(int size) {
		return (float) size / GameContext.TILE_SIZE; // 64 is tile size
	}
}
