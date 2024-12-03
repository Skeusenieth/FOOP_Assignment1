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
        return score; // Returns the current game score.
    }

    @Override
    public boolean canPlace(Piece piece) {
        // Validates whether the piece can be placed without overlap or going out of bounds.
        for (Cell cell : piece.cells()) {
            if (cell.x() < 0 || cell.x() >= width || cell.y() < 0 || cell.y() >= height || grid[cell.x()][cell.y()]) {
                return false; // Invalid placement if out of bounds or overlaps occupied cells.
            }
        }
        return true;
    }

    @Override
    public void place(Piece piece) {
        // Places the piece on the grid and checks for complete regions to clear.
        if (!canPlace(piece)) {
            throw new IllegalArgumentException("Piece cannot be placed.");
        }

        for (Cell cell : piece.cells()) {
            grid[cell.x()][cell.y()] = true; // Mark cells as occupied.
        }

        // Check for complete regions and update the score.
        for (Shape region : regions) {
            if (isComplete(region)) {
                remove(region); // Clear the region.
                score += region.size(); // Increase the score based on region size.
            }
        }
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
}