package shapes;

import geometry.Vec2d;

import java.util.ArrayList;
import java.util.List;

public class PuzzleModel {
    Tile box;
    final private ArrayList<Tile> tiles;

    public PuzzleModel(Tile box, ArrayList<Tile> tiles) {
        this.box = box;
        this.tiles = tiles;
        System.out.println("PuzzleModel created with " + tiles.size() + " tiles.");
    }

    public boolean isSolved() {
        // check containment and overlaps
        return countContains() == tiles.size() && countOverlaps() == 0;
    }

    public String getStatusText() {
        return "n Overlaps " + countOverlaps() + " Contains? " +
                countContains() + " Solved? " + isSolved();
    }

    public Tile getTileAt(Vec2d point) {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            Tile tile = tiles.get(i);
            if (tile.contains(point)) {
                return tile;
            }
        }
        return null;
    }

    public boolean checkOverlaps(Tile currentShape) {
        for (Tile tile : tiles) {
            if (tile != currentShape && tile.intersects(currentShape)) {
                return true;
            }
        }
        return false;
    }

    public int countOverlaps() {
        int total = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i).intersects(tiles.get(j))) {
                    total++;
                    break;
                }
            }
        }
        return total;
    }

    public int countContains() {
        int total = 0;
        for (Tile tile : tiles) {
            if (box.contains(tile)) {
                total++;
            }
        }
        return total;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}