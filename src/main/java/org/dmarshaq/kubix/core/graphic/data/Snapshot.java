package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.graphic.element.Camera;

import java.util.*;

public class Snapshot {
    private final List<Quad> quadRenderBuffer;
    private final List<Line> lineRenderBuffer;
    @Getter
    private Quad[] quadRenderArray;
    @Getter
    private Line[] lineRenderArray;

    // TODO CameraDto for snapshot.
    @Getter
    @Setter
    private CameraDto camera;

    public Snapshot() {
        quadRenderBuffer = new ArrayList<>();
        lineRenderBuffer = new ArrayList<>();
    }

    public void addQuad(Quad quad) {
        quadRenderBuffer.add(quad);
    }

    public void releaseQuadRenderBuffer() {
        quadRenderArray = quadRenderBuffer.toArray(new Quad[0]);

        Comparator<Quad> comparator = Quad::compareTo;

        Arrays.parallelSort(quadRenderArray, comparator);

        quadRenderBuffer.clear();
    }

    public void addLine(Line line) {
        lineRenderBuffer.add(line);
    }

    public void addLine(Line[] line) {
        Collections.addAll(lineRenderBuffer, line);
    }

    public void releaseLineRenderBuffer() {
        lineRenderArray = lineRenderBuffer.toArray(new Line[0]);

        Comparator<Line> comparator = Line::compareTo;

        Arrays.parallelSort(lineRenderArray, comparator);

        lineRenderBuffer.clear();
    }


}
