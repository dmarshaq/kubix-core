package org.dmarshaq.kubix.graphic.render;

import lombok.Getter;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.Shader;

import java.util.*;


public class Snapshot {
    private final List<Quad> quadRenderBuffer;
    @Getter
    private Quad[] quadRenderArray;

    public Snapshot() {
        quadRenderBuffer = new ArrayList<>();
    }

    public void addQuadToRenderBuffer(Quad quad) {
        quadRenderBuffer.add(quad);
    }

    public void releaseQuadRenderBuffer() {
        quadRenderArray = (Quad[]) quadRenderBuffer.toArray();

        Comparator<Quad> comparator = Quad::compareTo;

        //Parallel sorting
        Arrays.parallelSort(quadRenderArray, comparator);

        quadRenderBuffer.clear();
    }

}
