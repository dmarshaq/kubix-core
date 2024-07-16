package org.dmarshaq.kubix.graphic.render;

import lombok.Getter;
import org.dmarshaq.kubix.core.graphic.Shader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Snapshot {
    private final List<Quad> quadRenderBuffer;
    @Getter
    private Quad[] quadRenderArray;

    public Snapshot() {
        quadRenderBuffer = new ArrayList<>();
    }

    public void addQuadToRenderBuffer(Quad quad, Layer layer) {
        quadRenderBuffer.add(quad);
        layer.quadCount++;
    }

    public void releaseQuadRenderBuffer() {

//        Comparator<Quad> comparator = new Comparator<Quad>() {
//            @Override
//            public int compare(Quad o1, Quad o2) {
//                return 0;
//            }
//        };
//
//
//        quadRenderArray = (Quad[]) quadRenderBuffer.toArray();
//
//        //Parallel sorting
//        Arrays.parallelSort(quadRenderArray, comparator);
//
//        Layer currentLayer;
//        ShaderType currentShader = null;

    }

}
