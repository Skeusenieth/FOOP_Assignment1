package blocks;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import blocks.BlockShapes.PixelLoc;
import blocks.BlockShapes.Sprite;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Piece;

public class Controller extends MouseAdapter {
    GameView view;
    ModelInterface model; // The logical model for game operations.
    Palette palette; // The palette managing available sprites.
    JFrame frame; // The main application frame.
    Sprite selectedSprite = null; // Currently selected sprite.
    Piece ghostShape = null; // Visualization for the potential placement.
    String title = "Blocks Puzzle";
    boolean gameOver = false;

    public Controller(GameView view, ModelInterface model, Palette palette, JFrame frame) {
        this.view = view;
        this.model = model;
        this.palette = palette;
        this.frame = frame;
        frame.setTitle(title);

        // Force the palette to arrange sprites based on the grid and margins.
        palette.doLayout(view.margin, view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);
        System.out.println("Palette layout done: " + palette.sprites);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handles mouse press to select a sprite.
        PixelLoc loc = new PixelLoc(e.getX(), e.getY());
        selectedSprite = palette.getSprite(loc, view.paletteCellSize); // Find sprite at mouse location.

        if (selectedSprite != null) {
            selectedSprite.state = SpriteState.IN_PLAY; // Mark the sprite as being moved.
            System.out.println("Selected sprite: " + selectedSprite);
            view.repaint(); // Refresh the view to reflect changes.
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedSprite != null) {
            PixelLoc newLoc = new PixelLoc(e.getX(), e.getY());
            selectedSprite.px = newLoc.x();
            selectedSprite.py = newLoc.y();

            // Dynamically generate the ghost shape.
            view.ghostShape = selectedSprite.snapToGrid(view.margin, view.cellSize);

            // Update poppable regions based on the ghost shape.
            if (view.ghostShape != null & model.canPlace(view.ghostShape)) {
                view.poppableRegions = model.getPoppableRegions(view.ghostShape);
            } else {
                view.poppableRegions = null; // Clear if no ghost shape
            }
            System.out.println("Ghost shape updated: " + ghostShape);

            // Repaint to reflect the updated ghost shape.
            view.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedSprite != null) {
            Piece piece = selectedSprite.snapToGrid(view.margin, view.cellSize);

            if (model.canPlace(piece)) {
                model.place(piece); // Place the piece in the model.
                System.out.println("Piece placed successfully.");

                // Remove the sprite from the palette after successful placement.
                palette.getSprites().remove(selectedSprite);
                selectedSprite.state = SpriteState.PLACED; // Update state to indicate it's placed.
                boolean flag = palette.replenish(); // Check if the palette needs replenishment.
                if (flag){ // condition not needed?
                    palette.doLayout(view.margin, view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);
                }
                // Check game-over status.
                gameOver = model.isGameOver(palette.getShapesToPlace());
            } else {
                System.out.println("Invalid placement, returning piece to palette.");

                // Reset the sprite to the palette layout.
                selectedSprite.state = SpriteState.IN_PALETTE; // Set state back to palette.
                palette.doLayout(view.margin, view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);
            }

            // Clear the selected sprite and ghost shape.
            selectedSprite = null;
            ghostShape = null;
            view.ghostShape = null; // Clear the ghost shape from the view.
            view.poppableRegions = null;

            // Update the title and repaint the view.
            frame.setTitle(getTitle());
            view.repaint();
        }
    }

    private String getTitle() {
        // Dynamically generates the game title with the score and game-over status.
        String scoreTitle = title + " Score: " + model.getScore();
        if (gameOver) {
            scoreTitle += " Game Over!";
        }
        return scoreTitle;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instantiate the logical model, palette, and game view.
        ModelInterface model = new Model2dArray(); // Can switch to ModelSet if needed.
        Palette palette = new Palette();
        GameView view = new GameView(model, palette);

        // Create and configure the controller.
        Controller controller = new Controller(view, model, palette, frame);

        // Attach mouse listeners for interactions.
        view.addMouseListener(controller);
        view.addMouseMotionListener(controller);

        // Set up and display the main game window.
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
    }
}