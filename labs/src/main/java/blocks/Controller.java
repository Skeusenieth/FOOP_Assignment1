package blocks;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import blocks.BlockShapes.PixelLoc;
import blocks.BlockShapes.Sprite;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Piece;

public class Controller extends MouseAdapter {
    final GameView view;
    final ModelInterface model; // The logical model for game operations.
    final Palette palette; // The palette managing available sprites.
    final JFrame frame; // The main application frame.
    private Sprite selectedSprite = null; // Currently selected sprite.
    private Piece ghostShape = null; // Visualization for the potential placement.
    final String title = "Blocks Puzzle";
    private boolean gameOver = false;

    public Controller(GameView view, ModelInterface model, Palette palette, JFrame frame) {
        this.view = view;
        this.model = model;
        this.palette = palette;
        this.frame = frame;
        frame.setTitle(title);

        // Force the palette to arrange sprites based on the grid and margins.
        palette.doLayout(view.margin, view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handles mouse press to select a sprite.
        selectedSprite = palette.getSprite(new PixelLoc(e.getX(), e.getY()), view.paletteCellSize); // Find sprite at mouse location.

        if (selectedSprite != null) {
            selectedSprite.setState(SpriteState.IN_PLAY); // Mark the sprite as being moved.
            SoundPlayer.playSound("Pickup.wav");
            view.repaint(); // Refresh the view to reflect changes.
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedSprite != null) {
            // Get the current mouse location
            int mouseX = e.getX();
            int mouseY = e.getY();

            // Get the frame's size and calculate the total boundaries
            int minX = 0;
            int minY = 0;
            int maxX = frame.getContentPane().getWidth(); // Full width of the window
            int maxY = frame.getContentPane().getHeight(); // Full height of the window

            // Check if the sprite is completely out of bounds
            boolean isOutOfBounds =
                    (mouseX < minX-10 || mouseX > maxX+10) ||
                            (mouseY < minY-10 || mouseY > maxY+10);

            if (isOutOfBounds) {
                // Snap the sprite back to the palette and reset its state
                selectedSprite.setState(SpriteState.IN_PALETTE);

                // Clear the ghost shape and regions
                view.ghostShape = null;
                view.poppableRegions = null;

                // Repaint the view to reflect changes
                view.repaint();
                return;
            }

            // Otherwise, allow dragging within bounds
            selectedSprite.moveTo(new PixelLoc(mouseX, mouseY));

            // Dynamically generate the ghost shape
            view.ghostShape = selectedSprite.snapToGrid(view.margin, view.cellSize);

            // Update poppable regions based on the ghost shape
            if (view.ghostShape != null && model.canPlace(view.ghostShape)) {
                view.poppableRegions = model.getPoppableRegions(view.ghostShape);
            } else {
                view.poppableRegions = null; // Clear if no ghost shape
            }

            // Repaint to reflect the updated ghost shape
            view.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedSprite != null) {
            Piece piece = selectedSprite.snapToGrid(view.margin, view.cellSize);

            if (model.canPlace(piece)) {
                handleValidPlacement(piece);
            } else {
                handleInvalidPlacement();
            }

            // Clear the selected sprite and ghost shape.
            clearSelection();

            // Update the title and repaint the view.
            frame.setTitle(getTitle());
            view.repaint();
        }
    }

    private void handleValidPlacement(Piece piece) {
        model.place(piece); // Place the piece in the model.

        // Delegate sprite handling to the Palette class.
        palette.handleValidSpritePlacement(selectedSprite, view.margin,
                view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);

        selectedSprite.setState(SpriteState.PLACED); // Update state to indicate placement.
        SoundPlayer.playSound("Drop.wav");
        // Check game-over status.
        gameOver = model.isGameOver(palette.getShapesToPlace());
        if (gameOver) {
            SoundPlayer.playSound("GameOver.wav");
        }
    }

    private void handleInvalidPlacement() {
        selectedSprite.setState(SpriteState.IN_PALETTE); // Set state back to palette.
        SoundPlayer.playSound("Invalid.wav");
    }

    private void clearSelection() {
        selectedSprite = null;
        ghostShape = null;
        view.ghostShape = null; // Clear the ghost shape from the view.
        view.poppableRegions = null;
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
        JFrame frame = new JFrame("Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Preload all sounds
        SoundPlayer.preloadSounds("Pickup.wav", "Drop.wav", "SectionPopped.wav", "MultipleSectionsPopped.wav",
                "Invalid.wav", "GameOver.wav");

        ModelInterface model = new ModelSet();
        Palette palette = new Palette();
        GameView view = new GameView(model, palette);

        Controller controller = new Controller(view, model, palette, frame);

        view.addMouseListener(controller);
        view.addMouseMotionListener(controller);

        frame.add(view);
        frame.pack();
        frame.setVisible(true);
    }
}