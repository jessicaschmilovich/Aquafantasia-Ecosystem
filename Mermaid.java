import java.util.Random;

public class Mermaid extends Creature {
    private static final Random rand = new Random(); // Random generator for movement and placement.
    private String name; // The name of the Mermaid.

    // Constructor to initialize a Mermaid with a color and name.
    public Mermaid(String color, String name) {
        setColor(color); // Set the Mermaid's color using the superclass method.
        this.name = name; // Assign the Mermaid's name.
    }

    // Returns the type of this creature.
    public String getType() {
        return "Mermaid";
    }

    @Override
    public void move(Creature[] ecosystem, int position) {
        // If the Mermaid has Genie power, apply it to occupy multiple cells.
        if (hasGenie()) {
            Genie.applyGeniePower(ecosystem, position, this);
            return; // No further action after applying Genie power.
        }

        // Generate a random move direction: -1 (left), 0 (stay), or +1 (right).
        int moveDirection = rand.nextInt(3) - 1;
        int targetPosition = position + moveDirection;

        // Ensure the target position is within bounds of the ecosystem array.
        if (targetPosition >= 0 && targetPosition < ecosystem.length) {
            Creature targetCreature = ecosystem[targetPosition];

            if (targetCreature == null) {
                // Move to the empty cell.
                ecosystem[targetPosition] = this;
                ecosystem[position] = null;
            } else if (targetCreature instanceof Mermaid) {
                // If another Mermaid is at the target, attempt breeding.
                ecosystem[position] = null; // Vacate the current position.
                Creature newMermaid = this.breed();
                placeInEmptyCell(ecosystem, newMermaid); // Place offspring in an empty cell.
            } else if (targetCreature instanceof Unicorn) {
                // Handle interaction with a Unicorn.
                Unicorn unicorn = (Unicorn) targetCreature;
                if (!unicorn.hasPower() || this.hasPower()) {
                    // Move only if the Unicorn doesn't have power or if this Mermaid has power.
                    ecosystem[targetPosition] = this;
                    ecosystem[position] = null;
                }
            }
        }
    }

    @Override
    public Creature breed() {
        // Create a new Mermaid with the same color and name.
        return new Mermaid(getColor(), name);
    }

    // Grants the Mermaid special "gold" power.
    public void gainPower() {
        setColor("gold"); // Change the Mermaid's color to "gold".
        setHasPower(true); // Indicate that the Mermaid now has power.
    }

    @Override
    public String display() {
        // To represent difference in color on grid, display "M*" if the Mermaid has power, otherwise "M".
        return hasPower() ? "M*" : "M";
    }

    // Places the given creature in a random empty cell within the ecosystem.
    public void placeInEmptyCell(Creature[] ecosystem, Creature creature) {
        int emptyPos;
        // Find a random empty position in the ecosystem.
        do {
            emptyPos = rand.nextInt(ecosystem.length);
        } while (ecosystem[emptyPos] != null);

        // Place the creature in the found empty cell.
        ecosystem[emptyPos] = creature;
    }

    @Override
    public String toString() {
        // Provide a string representation of the Mermaid's attributes.
        return "color: " + getColor() + " name: " + name + 
               " has power: " + hasPower() + 
               " has genie: " + hasGenie();
    }
}
