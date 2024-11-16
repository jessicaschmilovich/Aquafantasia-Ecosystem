public class Genie {
    
    // Applies Genie power, allowing a creature to occupy up to three cells in the ecosystem.
    public static void applyGeniePower(Creature[] ecosystem, int position, Creature creature) {
        int ecosystemSize = ecosystem.length; // Get the total number of cells in the ecosystem.
        int start, end; // Define the range of cells to be occupied.

        if (position == 0) {
            // Special case: Creature is at the start of the ecosystem.
            start = 0;
            end = Math.min(2, ecosystemSize - 1); // Occupy up to three cells, ensuring no overflow.
        } else if (position == ecosystemSize - 1) {
            // Special case: Creature is at the end of the ecosystem.
            start = Math.max(0, ecosystemSize - 3); // Occupy the last three cells, ensuring no underflow.
            end = ecosystemSize - 1;
        } else {
            // General case: Creature is somewhere in the middle of the ecosystem.
            start = Math.max(0, position - 1); // Occupy one cell to the left.
            end = Math.min(ecosystemSize - 1, position + 1); // Occupy one cell to the right.
        }

        // Set all cells in the range [start, end] to the given creature.
        for (int i = start; i <= end; i++) {
            ecosystem[i] = creature;
        }
    }
}
