package blocks;

import blocks.BlockShapes.*;
import java.util.*;
import java.util.stream.Collectors;

public class ModelSet extends StateSet implements ModelInterface {
    Set<Cell> locations = new HashSet<>(); // All valid grid locations.
    List<Shape> regions = new RegionHelper().allRegions(); // Predefined regions for validation.

    // Constructor initializes the regions.
    public ModelSet() {
        super();
        initialiseLocations(); // Initialize all valid grid locations.
    }

    @Override
    public int getScore() {
        return score; // Returns the current score.
    }

    private void initialiseLocations() {
        // Populate the `locations` set with all valid grid cells.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                locations.add(new Cell(i, j));
            }
        }
    }

    @Override
    public boolean canPlace(Piece piece) {
        // Checks if all cells in the piece are within bounds (`locations`) and not already occupied.
        return piece.cells().stream()
                .allMatch(cell -> locations.contains(cell) && !occupiedCells.contains(cell));
    }

    @Override
    public void place(Piece piece) {
        // Places the piece if it is valid and updates the occupied cells.
        if (!canPlace(piece)) {
            throw new IllegalArgumentException("Piece cannot be placed.");
        }

        occupiedCells.addAll(piece.cells()); // Mark all piece cells as occupied.

        // Check for complete regions and remove them.
        for (Shape region : regions) {
            if (isComplete(region)) {
                remove(region); // Clear the region.
                score += region.size(); // Increment score based on region size.
            }
        }
    }

    @Override
    public void remove(Shape region) {
        // Removes the cells of the given region from the `occupiedCells` set.
        occupiedCells.removeAll(region);
    }

    @Override
    public boolean isComplete(Shape region) {
        // Checks if all cells in the region are occupied.
        return region.stream().allMatch(occupiedCells::contains);
    }

    @Override
    public boolean isGameOver(List<Shape> palettePieces) {
        // Determines if the game is over by checking if any palette piece can be placed.
        return palettePieces.stream().noneMatch(this::canPlaceAnywhere);
    }

    public boolean canPlaceAnywhere(Shape shape) {
        // Checks if the shape can be placed anywhere on the grid.
        return locations.stream().anyMatch(loc -> canPlace(new Piece(shape, loc)));
    }

    @Override
    public List<Shape> getPoppableRegions(Piece piece) {
        // Identifies regions that would be cleared if the piece is placed.
        Set<Cell> simulatedOccupied = new HashSet<>(occupiedCells);
        simulatedOccupied.addAll(piece.cells()); // Simulate adding the piece.

        return regions.stream()
                .filter(region -> region.stream().allMatch(simulatedOccupied::contains)) // Check completeness.
                .collect(Collectors.toList()); // Collect all poppable regions.
    }

    @Override
    public Set<Cell> getOccupiedCells() {
        // Returns the set of currently occupied cells.
        return new HashSet<>(occupiedCells);
    }
}