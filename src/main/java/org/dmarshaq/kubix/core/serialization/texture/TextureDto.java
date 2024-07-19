package org.dmarshaq.kubix.core.serialization.texture;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TextureDto {
    private String name;
    private long lastModified;
    private int width;
    private int height;
    private int[] data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextureDto that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
