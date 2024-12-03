package blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import blocks.BlockShapes.*;

public class Palette {
    ArrayList<Shape> shapes = new ArrayList<>(); // All possible shapes.
    List<Sprite> sprites; // Currently available sprites in the palette.
    int nShapes = 3; // Number of sprites to display in the palette.

    public Palette() {
        shapes.addAll(new ShapeSet().getShapes()); // Populate shapes from ShapeSet.
        sprites = new ArrayList<>();
        replenish(); // Initial replenishment of sprites.
    }

    public ArrayList<Shape> getShapes() {
        return shapes; // Returns all possible shapes.
    }

    public ArrayList<Shape> getShapesToPlace() {
        // Returns a list of shapes that are ready to be placed (IN_PALETTE state).
        ArrayList<Shape> toPlace = new ArrayList<>();
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE) {
                toPlace.add(sprite.shape);
            }
        }
        return toPlace;
    }

    public List<Sprite> getSprites() {
        return sprites; // Returns the list of sprites in the palette.
    }

    public Sprite getSprite(PixelLoc mousePoint, int cellSize) {
        // Checks if any sprite contains the mouse point.
        for (Sprite sprite : sprites) {
            if (sprite.contains(mousePoint, cellSize)) {
                return sprite; // Return the sprite if the mouse is over it.
            }
        }
        return null; // Return null if no sprite matches.
    }

    private int nReadyPieces() {
        // Counts the number of sprites in IN_PALETTE or IN_PLAY states.
        int count = 0;
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE || sprite.state == SpriteState.IN_PLAY) {
                count++;
            }
        }
        System.out.println("nReadyPieces: " + count);
        return count;
    }

    public void doLayout(int x0, int y0, int cellSize) {
        int spacing = cellSize; // Define spacing between sprites.
        int x = x0;
        int y = y0;

        for (Sprite sprite : sprites) {
            sprite.px = x; // Set the x-coordinate for the sprite.
            sprite.py = y; // Set the y-coordinate for the sprite.

            sprite.setOriginalPosition();

            // Adjust x for the next sprite, wrapping to the next row if needed.
            x += (sprite.shape.size() + 1) * cellSize + spacing;

            if (x + cellSize > ModelInterface.width * cellSize) {
                x = x0; // Reset to the first column.
                y += cellSize + spacing; // Move to the next row.
            }
        }
    }

    public boolean replenish() {
        if (nReadyPieces() > 0) {
            return false; // Do not replenish if there are still active sprites.
        }

        System.out.println("Replenishing palette...");
        sprites.clear(); // Clear the current sprites.


        Random random = new Random();
        for (int i = 0; i < nShapes; i++) {
            // Randomly select shapes to add to the palette.
            Shape shape = shapes.get(random.nextInt(shapes.size()));

            // Initialize sprites with default positions (will be updated by doLayout).
            Sprite sprite = new Sprite(shape, 0, 0);
            sprites.add(sprite);
        }

        System.out.println("Sprites after replenish: " + sprites);

        // Lay out the sprites just like in the Controller constructor.
        return true;
    }
    public static void main(String[] args) {
        Palette palette = new Palette();
        System.out.println(palette.shapes); // Display all possible shapes.
        System.out.println(palette.sprites); // Display current sprites.
        palette.doLayout(0, 0, 20); // Lay out sprites with a cell size of 20 pixels.
        System.out.println(palette.sprites); // Display updated sprite positions.
    }

}

