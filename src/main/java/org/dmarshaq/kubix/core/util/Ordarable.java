package org.dmarshaq.kubix.core.util;

public interface Ordarable extends Comparable<Ordarable> {
    int ordinal();

    default int compareTo(Ordarable o) {
        return Integer.compare(ordinal(), o.ordinal());
    }
}
