package blocks;

import blocks.BlockShapes.*;
import java.util.*;
import java.util.stream.Collectors;

public class ModelSet extends StateSet implements ModelInterface {
    final Set<Cell> locations = new HashSet<>(); // All valid grid locations.
    final List<Shape> regions = new RegionHelper().allRegions(); // Predefined regions for validation.
    private int score = 0; // Tracks the score.
    private int streak = 0; // Tracks the streak.
    // Constructor initializes the regions.
    public ModelSet() {
        super();
        initialiseLocations(); // Initialize all valid grid locations.
    }

    @Override
    public int getScore() {
        System.out.println("Current Score: " + score);
        System.out.println("Current Streak: " + streak);
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
        markCellsAsOccupied(piece);

        List<Shape> completeRegions = collectCompleteRegions();
        int pointsEarned = calculatePoints(completeRegions);

        pointsEarned *= streak + 1; // Apply streak multiplier.
        score += pointsEarned; // Update the total score.

        if (!completeRegions.isEmpty()) {
            streak++; // Increase streak if regions were popped.
        } else {
            streak = 0; // Reset streak if no regions were popped.
        }

        playSoundEffect(completeRegions);
        logPlacementDetails(completeRegions, pointsEarned);
    }

    // Mark all piece cells as occupied
    private void markCellsAsOccupied(Piece piece) {
        occupiedCells.addAll(piece.cells());
    }

    // Collect all regions that are complete
    private List<Shape> collectCompleteRegions() {
        List<Shape> completeRegions = new ArrayList<>();
        for (Shape region : regions) {
            if (isComplete(region)) {
                completeRegions.add(region);
            }
        }
        return completeRegions;
    }

    // Calculate points based on the complete regions
    private int calculatePoints(List<Shape> completeRegions) {
        int multiplier = Math.max(1, completeRegions.size());
        int points = 0;

        for (Shape region : completeRegions) {
            remove(region);
            points += region.size();
        }
        return points * multiplier;
    }

    // Play sound effect based on regions popped
    private void playSoundEffect(List<Shape> regions) {
        if (regions.size() > 1) {
            SoundPlayer.playSound("MultipleSectionsPopped.wav");
        } else if (!regions.isEmpty()) {
            SoundPlayer.playSound("SectionPopped.wav");
        }
    }

    // Log placement details for debugging
    private void logPlacementDetails(List<Shape> regions, int pointsEarned) {

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
