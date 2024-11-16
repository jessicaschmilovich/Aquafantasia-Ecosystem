public class Genie {
    public static void applyGeniePower(Creature[] ecosystem, int position, Creature creature) {
        int ecosystemSize = ecosystem.length;
        int start, end;
    
        if (position == 0) {
            // At the start of the ecosystem
            start = 0;
            end = Math.min(2, ecosystemSize - 1);
        } else if (position == ecosystemSize - 1) {
            // At the end of the ecosystem
            start = Math.max(0, ecosystemSize - 3);
            end = ecosystemSize - 1;
        } else {
            // General case: occupy three cells centered on the position
            start = Math.max(0, position - 1);
            end = Math.min(ecosystemSize - 1, position + 1);
        }
    
        // Overwrite cells from start to end with the creature
        for (int i = start; i <= end; i++) {
            ecosystem[i] = creature;
        }
    }         
}