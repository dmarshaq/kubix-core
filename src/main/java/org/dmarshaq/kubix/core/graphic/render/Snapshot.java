package org.dmarshaq.kubix.core.graphic.render;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.graphic.Camera;

import java.util.*;

public class Snapshot {
    private final List<Quad> quadRenderBuffer;
    @Getter
    private Quad[] quadRenderArray;

    // TODO CameraDto for snapshot.
    @Getter
    @Setter
    private Camera camera;

    public Snapshot() {
        quadRenderBuffer = new ArrayList<>();
    }

    public void addQuadToRenderBuffer(Quad quad) {
        quadRenderBuffer.add(quad);
    }

    public void releaseQuadRenderBuffer() {
        quadRenderArray = quadRenderBuffer.toArray(new Quad[0]);

        Comparator<Quad> comparator = Quad::compareTo;

        Arrays.parallelSort(quadRenderArray, comparator);

        quadRenderBuffer.clear();
    }

}
