package org.dmarshaq.kubix.core.graphic.data;

import lombok.*;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CameraDto {
    private Matrix4x4 projection;
}
