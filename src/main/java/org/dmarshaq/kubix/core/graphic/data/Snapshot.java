package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;

import java.util.*;

public class Snapshot {
    private final List<Texture> textureUsedList;
    private int currentTextureGroup;

    private final List<QuadStructure> quadList;
    private final List<Line> lineList;
    @Getter
    private QuadStructure[] quadRenderArray;
    @Getter
    private Line[] lineRenderArray;

    @Getter
    @Setter
    private CameraDto camera;

    public Snapshot() {
        currentTextureGroup = 0;
        textureUsedList = new ArrayList<>();
        textureUsedList.add(Texture.NO_TEXTURE);
        quadList = new ArrayList<>();
        lineList = new ArrayList<>();
    }

    public void addQuad(QuadStructure quad) {
        if (textureUsedList.contains(quad.getTexture())) {
            quad.setTextureGroup(currentTextureGroup, textureUsedList.indexOf(quad.getTexture()));
        }
        else if (textureUsedList.size() < 32) {
            textureUsedList.add(quad.getTexture());
            quad.setTextureGroup(currentTextureGroup, textureUsedList.size() - 1);
        }
        else {
            textureUsedList.clear();
            textureUsedList.add(Texture.NO_TEXTURE);
            textureUsedList.add(quad.getTexture());
            quad.setTextureGroup(++currentTextureGroup, 1);
        }
        quadList.add(quad);
    }
    public void addQuad(QuadStructure[] quads) {
        for (QuadStructure quad : quads) {
            addQuad(quad);
        }
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
        quadRenderArray = quadList.toArray(new QuadStructure[0]);

        Comparator<QuadStructure> comparator = QuadStructure::compareTo;

        Arrays.parallelSort(quadRenderArray, comparator);

        quadList.clear();
    }
}
