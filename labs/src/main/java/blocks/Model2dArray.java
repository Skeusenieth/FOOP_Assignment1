package blocks;

import blocks.BlockShapes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model2dArray extends State2dArray implements ModelInterface {
    private final List<Shape> regions = new RegionHelper().allRegions(); // Predefined regions for validation.
    private int score = 0; // Tracks the score.

    public Model2dArray() {
        grid = new boolean[width][height]; // Initializes a 9x9 grid.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = false; // Explicitly set all cells to empty (false).
            }
        }
    }

    @Override
    public int getScore() {
        System.out.println("Current Score: " + score);
        return score; // Returns the current game score.
    }

    @Override
    public boolean canPlace(Piece piece) {
        for (Cell cell : piece.cells()) {
            if (isOutOfBounds(cell) || isOccupied(cell)) {
                return false;
            }
        }
        return true;
    }

    // Check if a cell is out of bounds
    private boolean isOutOfBounds(Cell cell) {
        return cell.x() < 0 || cell.x() >= width || cell.y() < 0 || cell.y() >= height;
    }

    // Check if a cell is already occupied
    private boolean isOccupied(Cell cell) {
        return grid[cell.x()][cell.y()];
    }

    @Override
    public void place(Piece piece) {
        // Mark cells as occupied.
        for (Cell cell : piece.cells()) {
            grid[cell.x()][cell.y()] = true;
        }

        // Collect all complete regions.
        List<Shape> regionsToRemove = new ArrayList<>();
        for (Shape region : regions) {
            if (isComplete(region)) {
                regionsToRemove.add(region);
            }
        }

        // Apply a multiplier based on the number of regions.
        int multiplier = Math.max(1, regionsToRemove.size()); // Ensure multiplier is at least 1.
        int pointsEarned = 0;

        // Remove all complete regions and calculate points.
        for (Shape region : regionsToRemove) {
            remove(region); // Clear the region.
            pointsEarned += region.size(); // Calculate points for this region.
        }

        // Apply the multiplier to the points earned.
        pointsEarned *= multiplier;
        score += pointsEarned; // Update the total score.

        // Play sound effects for points earned.
        playSoundEffect(regionsToRemove);

        // Optionally log placement details for debugging.
        System.out.println("Regions Popped: " + regionsToRemove.size() +
                ", Multiplier: " + multiplier +
                ", Points Earned: " + pointsEarned);
    }

    @Override
    public void remove(Shape region) {
        // Clears all cells within the specified region.
        for (Cell cell : region) {
            grid[cell.x()][cell.y()] = false;
        }
    }

    @Override
    public boolean isComplete(Shape region) {
        // Checks if all cells in the region are occupied.
        for (Cell cell : region) {
            if (!grid[cell.x()][cell.y()]) {
                return false; // If any cell is unoccupied, the region is incomplete.
            }
        }
        return true;
    }

    @Override
    public boolean isGameOver(List<Shape> palettePieces) {
        // Determines if the game is over by verifying if any palette piece can be placed.
        for (Shape shape : palettePieces) {
            if (canPlaceAnywhere(shape)) {
                return false; // Game is not over if at least one piece can be placed.
            }
        }
        return true; // Game over if no piece can be placed.
    }

    private boolean canPlaceAnywhere(Shape shape) {
        // Checks if the shape can be placed anywhere on the grid.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Piece piece = new Piece(shape, new Cell(x, y));
                if (canPlace(piece)) {
                    return true; // Valid placement found.
                }
            }
        }
        return false; // No valid placement found.
    }

    @Override
    public List<Shape> getPoppableRegions(Piece piece) {
        // Identifies all regions that would be cleared if the piece is placed.
        List<Shape> poppable = new ArrayList<>();
        List<Cell> cellsToAdd = piece.cells(); // Get the cells the piece would occupy.
        for (Shape region : regions) {
            if (wouldBeComplete(region, cellsToAdd)) {
                poppable.add(region); // Add region if it would become complete.
            }
        }
        return poppable;
    }

    @Override
    public Set<Cell> getOccupiedCells() {
        // Collects all currently occupied cells from the grid.
        Set<Cell> occupiedCells = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y]) {
                    occupiedCells.add(new Cell(x, y));
                }
            }
        }
        return occupiedCells;
    }

    private boolean wouldBeComplete(Shape region, List<Cell> toAdd) {
        // Checks if adding the given cells would complete the region.
        Set<Cell> simulatedOccupied = getOccupiedCells();
        simulatedOccupied.addAll(toAdd); // Simulate adding the new cells.
        for (Cell cell : region) {
            if (!simulatedOccupied.contains(cell)) {
                return false; // Region incomplete if any required cell is missing.
            }
        }
        return true;
    }

    // Play sound effects based on regions popped
    private void playSoundEffect(List<Shape> regions) {
        if (regions.size() > 1) {
            SoundPlayer.playSound("MultipleSectionsPopped.wav");
        } else if (!regions.isEmpty()) {
            SoundPlayer.playSound("SectionPopped.wav");
        }
    }
}