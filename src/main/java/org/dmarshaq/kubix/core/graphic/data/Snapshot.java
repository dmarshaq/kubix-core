package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Snapshot {
    private final List<Quad> quadList;
    private final List<Line> lineList;
    @Getter
    private Quad[] quadRenderArray;
    @Getter
    private Line[] lineRenderArray;

    @Getter
    @Setter
    private CameraDto camera;

    public Snapshot() {
        quadList = new ArrayList<>();
        lineList = new ArrayList<>();
    }

    public void addQuad(Quad quad) {
        quadList.add(quad);
    }
    public void addQuad(Quad[] quads) {
        Collections.addAll(quadList, quads);
    }

    public void addLine(Line line) {
        lineList.add(line);
    }
    public void addLine(Line[] lines) {
        Collections.addAll(lineList, lines);
    }



    public void releaseLineRenderBuffer() {
        lineRenderArray = lineList.toArray(new Line[0]);

        Comparator<Line> comparator = Line::compareTo;

        Arrays.parallelSort(lineRenderArray, comparator);

        lineList.clear();
    }

    public void releaseQuadRenderBuffer() {
        quadRenderArray = quadList.toArray(new Quad[0]);

        Comparator<Quad> comparator = Quad::compareTo;

        Arrays.parallelSort(quadRenderArray, comparator);

        quadList.clear();
    }


}
