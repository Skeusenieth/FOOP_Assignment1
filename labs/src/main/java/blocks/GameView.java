package blocks;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

import blocks.BlockShapes.*;
import blocks.BlockShapes.Shape;

public class GameView extends JComponent {
    final ModelInterface model;
    final Palette palette;
    final int margin = 5;
    final int shapeRegionHeight;
    int cellSize;
    int paletteCellSize;
    int shrinkSize = 30;
    Piece ghostShape = null;
    List<Shape> poppableRegions = null;

    public GameView(ModelInterface model, Palette palette) {
        this.model = model;
        this.palette = palette;
        this.cellSize = 40; // Grid cell size
        this.paletteCellSize = cellSize; // Align palette sprite size with grid cell size
        this.shapeRegionHeight = cellSize * ModelInterface.height / 2;
    }

    private void paintShapePalette(Graphics g, int cellSize) {
        // Define palette area
        int paletteTop = margin + ModelInterface.height * cellSize;
        int paletteHeight = shapeRegionHeight;

        // Paint the background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(margin, paletteTop, ModelInterface.width * cellSize, paletteHeight);

        // Paint the sprites
        for (Sprite sprite : palette.getSprites()) {
            g.setColor(new Color(173, 216, 230)); // Light blue (RGB: 173, 216, 230)
            for (Cell cell : sprite.shape) {
                int x = sprite.px + cell.x() * cellSize;
                int y = sprite.py + cell.y() * cellSize;
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);
                g.setColor(new Color(173, 216, 230)); // Reset color to light blue for the next cell
            }
        }
    }

    private void paintPoppableRegions(Graphics g, int cellSize) {
        // Paint regions that would be cleared if a piece is placed.
        if (poppableRegions == null) return;

        g.setColor(new Color(0, 255, 0, 128)); // Semi-transparent green for poppable regions.
        for (Shape region : poppableRegions) {
            for (Cell cell : region) {
                int x = margin + cell.x() * cellSize;
                int y = margin + cell.y() * cellSize;
                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void paintGhostShape(Graphics g, int cellSize) {
        if (ghostShape == null) {
            return;
        }

        g.setColor(new Color(100, 100, 100, 128)); // Semi-transparent gray for ghost shape.
        for (Cell cell : ghostShape.cells()) {
            int x = margin + cell.x() * cellSize;
            int y = margin + cell.y() * cellSize;
            g.fillRect(x, y, cellSize, cellSize);
        }
    }

    private void paintGrid(Graphics g) {
        int x0 = margin;
        int y0 = margin;
        int width = ModelInterface.width * cellSize;
        int height = ModelInterface.height * cellSize;

        // Draw grid outline.
        g.setColor(Color.BLACK);
        g.drawRect(x0, y0, width, height);

        // Draw cells.
        Set<Cell> occupiedCells = model.getOccupiedCells();
        for (int x = 0; x < ModelInterface.width; x++) {
            for (int y = 0; y < ModelInterface.height; y++) {
                int cellX = x0 + x * cellSize;
                int cellY = y0 + y * cellSize;

                if (occupiedCells.contains(new Cell(x, y))) {
                    // Paint occupied cells in gray.
                    g.setColor(Color.BLUE);
                } else {
                    // Paint empty cells in white.
                    g.setColor(Color.WHITE);
                }
                g.fill3DRect(cellX, cellY, cellSize, cellSize, true);

                // Draw cell border.
                g.setColor(Color.BLACK);
                g.drawRect(cellX, cellY, cellSize, cellSize);
            }
        }
    }

    private void paintMiniGrids(Graphics2D g) {
        // Draw thicker lines for subgrids.
        int s = ModelInterface.subSize;
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        for (int x = 0; x < ModelInterface.width; x += s) {
            for (int y = 0; y < ModelInterface.height; y += s) {
                g.drawRect(margin + x * cellSize, margin + y * cellSize, s * cellSize, s * cellSize);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Paint game components.
        paintGrid(g);
        paintMiniGrids((Graphics2D) g);
        paintGhostShape(g, cellSize);
        paintPoppableRegions(g, cellSize);
        paintShapePalette(g, paletteCellSize);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                ModelInterface.width * cellSize + 2 * margin,
                ModelInterface.height * cellSize + 2 * margin + shapeRegionHeight
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clean Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelInterface model = new ModelSet();
        BlockShapes.Shape shape = new ShapeSet().getShapes().get(0);
        Piece piece = new Piece(shape, new Cell(0, 0));
        Palette palette = new Palette();
        model.place(piece);
        frame.add(new GameView(model, palette));
        frame.pack();
        frame.setVisible(true);
    }
}