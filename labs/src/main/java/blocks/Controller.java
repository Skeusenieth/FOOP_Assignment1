package blocks;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import blocks.BlockShapes.Sprite;
import blocks.BlockShapes.PixelLoc;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Piece;

public class Controller extends MouseAdapter {
    GameView view;
    ModelInterface model;
    Palette palette;
    JFrame frame;
    Sprite selectedSprite = null;
    Piece ghostShape = null;
    String title = "Blocks Puzzle";
    boolean gameOver = false;

    public Controller(GameView view, ModelInterface model, Palette palette, JFrame frame) {
        this.view = view;
        this.model = model;
        this.palette = palette;
        this.frame = frame;
        frame.setTitle(title);
        // force palette to do a layout
        palette.doLayout(view.margin, view.margin + ModelInterface.height * view.cellSize, view.paletteCellSize);
        System.out.println("Palette layout done: " + palette.sprites);
    }

    public void mousePressed(MouseEvent e) {
        // just call the model to try a piece selection given
        // this coordinate, and any other details such as margin and cell size
        // implementation of this method is provided, but you need to make the other controller methods work
        // see todos below
        System.out.println("Mouse pressed: " + e);
        PixelLoc loc = new PixelLoc(e.getX(), e.getY());
        selectedSprite = palette.getSprite(loc, view.paletteCellSize);
        if (selectedSprite == null) {
            return;
        }
        selectedSprite.state = SpriteState.IN_PLAY;
        System.out.println("Selected sprite: " + selectedSprite);
        view.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        // todo: implement
    }

    public void mouseReleased(MouseEvent e) {
        // todo: implement

        // update the title with the score and whether the game is over
        frame.setTitle(getTitle());
        view.repaint();
    }

    private String getTitle() {
        // make the title from the base title, score, and add GameOver if the game is over
        String title = this.title + " Score: " + model.getScore();
        if (gameOver) {
            title += " Game Over!";
        }
        return title;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ModelInterface model = new ModelSet();
        ModelInterface model = new Model2dArray();
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