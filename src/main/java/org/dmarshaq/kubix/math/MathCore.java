package org.dmarshaq.kubix.math;

import org.dmarshaq.kubix.math.operations.VectorDotProduct;
import org.dmarshaq.kubix.math.operations.VectorNegation;
import org.dmarshaq.kubix.math.operations.VectorSummation;
import org.dmarshaq.kubix.math.processors.*;

public abstract class MathCore {

    public static final String AXIS = "xyzw";
    public static final FloatProcessor OPERATION_PROCESSOR = new FloatProcessor(new IntegerProcessor(new DoubleProcessor(new LongProcessor(new ShortProcessor(new ByteProcessor(null))))));

    public static <T extends Number> Vector<T> vector2(T x, T y) {
        return new Vector<T>((T[])new Number[] {x, y});
    }

    public static <T extends Number> Vector<T> vector3(T x, T y, T z) {
        return new Vector<T>((T[])new Number[] {x, y, z});
    }

    public static <T extends Number> Vector<T> vector4(T x, T y, T z, T w) {
        return new Vector<T>((T[])new Number[] {x, y, z, w});
    }

    public static <T extends Number> Vector<T> summation(Vector<T> vector1, Vector<T> vector2) {
        VectorSummation<T> summation = new VectorSummation<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(summation);
        return summation.getResultant();
    }
    public static <T extends Number> Vector<T> negation(Vector<T> vector1) {
        VectorNegation<T> negation = new VectorNegation<>(vector1);
        OPERATION_PROCESSOR.processOperation(negation);
        return negation.getResultant();
    }
    public static <T extends Number> Vector<T> difference(Vector<T> vector1, Vector<T> vector2) {
        return summation(vector1, negation(vector2));
    }
    public static <T extends Number> T dotProduct(Vector<T> vector1, Vector<T> vector2) {
        VectorDotProduct<T> dotProduct = new VectorDotProduct<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(dotProduct);
        return dotProduct.getProduct();
    }

}
