package shapes;


import geometry.Vec2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TangramBetterTiles {

    static int size = 400;
    static int q1 = size / 4;
    static int q2 = size / 2;
    static int q3 = size * 3 / 4;
    static ArrayList<Tile> getTiles() {

        ArrayList<Tile> list = new ArrayList<>();
        // Large Triangle 1 (Magenta, top)
        Tile largeTriangle1 = new Tile(new Vec2d(200, 0), new ArrayList<>(List.of(
                new Vec2d(0, 0),
                new Vec2d(size, 0),
                new Vec2d(q2, q2)
        )), Color.MAGENTA);

        // Large Triangle 2 (Pink, left)
        Tile largeTriangle2 = new Tile(new Vec2d(200, 200), new ArrayList<>(List.of(
                new Vec2d(0, 0),
                new Vec2d(q2, q2),
                new Vec2d(0, size)
        )), Color.PINK);

        // Square (Pale green, bottom centre
        Tile diamond = new Tile(new Vec2d(100, 100), new ArrayList<>(List.of(
                new Vec2d(0, 0),
                new Vec2d(q1, q1),
                new Vec2d(0, q2),
                new Vec2d(-q1, q1)
        )), Color.RED);

        // Parallelogram (Yellow, right)
        Tile parallelogram = new Tile(new Vec2d(0, 100), new ArrayList<>(List.of(
                new Vec2d(q1, 0),
                new Vec2d(q1, q2),
                new Vec2d(0, q3),
                new Vec2d(0, q1)
        )), Color.YELLOW);

        // Small Triangle 1 (Cyan, centre-right)
        Tile smallTriangle1 = new Tile(new Vec2d(100, 0), new ArrayList<>(List.of(
                new Vec2d(0, 0),
                new Vec2d(q1, q1),
                new Vec2d(q1, -q1)
        )), Color.CYAN);

        // Small Triangle 2 (Yellow, right side of the square)
        Tile smallTriangle2 = new Tile(new Vec2d(200, 100), new ArrayList<>(List.of(
                new Vec2d(q2, 0),
                new Vec2d(q2, q2),
                new Vec2d(0, q2)
        )), Color.ORANGE);

        // Small triangle, green bottom left
        Tile mediumTriangle = new Tile(new Vec2d(200, 0), new ArrayList<>(List.of(
                new Vec2d(q1, 0),
                new Vec2d(q2, q1),
                new Vec2d(0, q1)
        )), Color.GREEN);

        list.add(largeTriangle1);
        list.add(largeTriangle2);
        list.add(diamond);
        list.add(parallelogram);
        list.add(smallTriangle1);
        list.add(smallTriangle2);
        list.add(mediumTriangle);

        return list;
    }

    public static Tile getBox() {
        int border = 10;
        int s = size + 2 * border;
        return new Tile(new Vec2d(350, 350), new ArrayList<>(List.of(
                new Vec2d(0, 0),
                new Vec2d(s, 0),
                new Vec2d(s, s),
                new Vec2d(0, s)
        )), Color.black);
    }
}

