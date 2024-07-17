package org.dmarshaq.kubix.core.util;

/**
 * Ordarable is an interface that helps define order of any object it was applied to.
 * It extends Comparable interface by defining compareTo() using order int value as comparing factor.
 * Comparing Ordarable's always favors greater order value.
 */
public interface Ordarable extends Comparable<Ordarable> {
    int ordinal();

    default int compareTo(Ordarable o) {
        return Integer.compare(ordinal(), o.ordinal());
    }
}
