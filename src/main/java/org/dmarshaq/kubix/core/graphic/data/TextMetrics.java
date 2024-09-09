package org.dmarshaq.kubix.core.graphic.data;


public record TextMetrics(CachedGlyph[] cachedGlyphs, Number height, Number width, Integer[] endLineIndices) {
}
