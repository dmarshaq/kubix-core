package org.dmarshaq.kubix.core.graphic.base.texture;

class AtlasCoder {
    private final int hashCode;
    private final TextureAtlas atlas;

    public AtlasCoder(TextureAtlas atlas) {
        this.atlas = atlas;
        this.hashCode = atlas.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AtlasCoder that)) return false;
        return atlas.equals(((AtlasCoder) o).atlas);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
