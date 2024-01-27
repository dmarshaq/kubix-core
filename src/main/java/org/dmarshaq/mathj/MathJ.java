package org.dmarshaq.mathj;

import org.dmarshaq.app.GameContext;

public interface MathJ {

	final class Math2D {

		// Vector 2 & 3 operations in 2D
		public static float dot(Vector2f a, Vector2f b) {
			return a.x * b.x + a.y * b.y;
		}

		public static Vector2f multiply(Vector2f v, float value) {
			return new Vector2f(v.x * value, v.y * value);
		}

		public static Vector2f divide(Vector2f v, float value) {
			return new Vector2f(v.x / value, v.y / value);
		}

		public static Vector2f sum(Vector2f a, Vector2f b) {
			return new Vector2f(a.x + b.x, a.y + b.y);
		}

		public static Vector3f sum(Vector3f a, Vector3f b) {
			return new Vector3f(a.x + b.x, a.y + b.y, a.z);
		}

		public static Vector2f negate(Vector2f v) {
			return new Vector2f(-v.x, -v.y);
		}

		public static Vector3f negate(Vector3f v) {
			return new Vector3f(-v.x, -v.y, v.z);
		}

		public static Vector2f diffrence(Vector2f a, Vector2f b) {
			return sum(a, negate(b));
		}

		public static Vector3f diffrence(Vector3f a, Vector3f b) {
			return sum(a, negate(b));
		}

		public static Vector2f toVector2f(Vector3f v) {
			return new Vector2f(v.x, v.y);
		}

		public static Vector3f toVector3f(Vector2f v, float z) {
			return new Vector3f(v.x, v.y, z);
		}

		public static float magnitude(Vector2f v) {
			return v.magnitude();
		}

		public static Vector2f normalize(Vector2f v) {
			Vector2f result = new Vector2f();
			result.copyValues(v);
			result.normalize();
			return result;
		}

		// Matrix4x4 operations in 2D
		public static Matrix4f multiply(Matrix4f first, Matrix4f second) {
			Matrix4f product = new Matrix4f();

			for (int i = 0; i < 16; i += 4) {
				for (int j = 0; j < 4; j++) {
					product.elements[i + j] = 0.0f;
					for (int k = 0; k < 4; k++)
						product.elements[i + j] += first.elements[i + k] * second.elements[k * 4 + j];
				}
			}

			return product;
		}

		public static Vector2f multiply(Matrix4f matrix, Vector2f vector, float w) {
			Vector2f result = new Vector2f();

			result.x = matrix.elements[0 + 0 * 4] * vector.x + matrix.elements[1 + 0 * 4] * vector.y + matrix.elements[3 + 0 * 4] * w;
			result.y = matrix.elements[0 + 1 * 4] * vector.x + matrix.elements[1 + 1 * 4] * vector.y + matrix.elements[3 + 1 * 4] * w;

			return result;
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
		return (int) (size * GameContext.UNIT_SIZE);
	}

	static float pixelToWorld(int size) {
		return (float) size / GameContext.UNIT_SIZE;
	}
}
