package blocks;

import blocks.BlockShapes.*;
import java.util.*;
import java.util.stream.Collectors;

public class ModelSet extends StateSet implements ModelInterface {
    final Set<Cell> locations = new HashSet<>(); // All valid grid locations.
    final List<Shape> regions = new RegionHelper().allRegions(); // Predefined regions for validation.

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
        // Mark all piece cells as occupied.
        occupiedCells.addAll(piece.cells());

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
        if (regionsToRemove.size() > 1){
            SoundPlayer.playSound("MultipleSectionsPopped.wav");
        }
        else if (!regionsToRemove.isEmpty()) {
            SoundPlayer.playSound("SectionPopped.wav"); // Play a sound effect if points were earned.
        }
        // Optionally: Log the multiplier and points earned for debugging.
        System.out.println("Regions Popped: " + regionsToRemove.size() +
                ", Multiplier: " + multiplier +
                ", Points Earned: " + pointsEarned);
    }

    @Override
    public void remove(Shape region) {
        // Removes the cells of the given region from the `occupiedCells` set.
        region.forEach(occupiedCells::remove);
    }

    @Override
    public boolean isComplete(Shape region) {
        // Checks if all cells in the region are occupied.
        return occupiedCells.containsAll(region);
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
// Refactored and improved for OOP principles
