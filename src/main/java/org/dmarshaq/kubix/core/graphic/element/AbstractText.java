package org.dmarshaq.kubix.core.graphic.element;


import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector;

public interface AbstractText<T extends Number, E extends Vector<T>> {
    String getText();
    Font getFont();
    T getLineLimit();
    TextMetrics<T, E> getMetrics();
}
