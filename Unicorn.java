import java.util.Random;

public class Unicorn extends Creature {
    private static final Random rand = new Random(); // Random generator for movement and placement.
    private String name; // The name of the Unicorn.

    // Constructor to initialize a Unicorn with a specific color and name.
    public Unicorn(String color, String name) {
        setColor(color); // Assign the initial user-defined color.
        this.name = name; // Assign the Unicorn's name.
    }

    // Returns the type of this creature.
    public String getType() {
        return "Unicorn";
    }

    @Override
    public void move(Creature[] ecosystem, int position) {
        // If the Unicorn has Genie power, apply it to occupy multiple cells.
        if (hasGenie()) {
            Genie.applyGeniePower(ecosystem, position, this);
            return; // No further action after applying Genie power.
        }

        // Generate a random move direction: -1 (left), 0 (stay), or +1 (right).
        int moveDirection = rand.nextInt(3) - 1;
        int targetPosition = position + moveDirection;

        // Ensure the target position is within the ecosystem's bounds.
        if (targetPosition >= 0 && targetPosition < ecosystem.length) {
            Creature targetCreature = ecosystem[targetPosition];

            if (targetCreature == null) {
                // Move to the empty spot.
                ecosystem[targetPosition] = this;
                ecosystem[position] = null;
            } else if (targetCreature instanceof Unicorn) {
                // Breed if the target is another Unicorn.
                ecosystem[position] = null; // Vacate the current position.
                Creature newUnicorn = this.breed();
                placeInEmptyCell(ecosystem, newUnicorn); // Place offspring in an empty cell.
            } else if (targetCreature instanceof Mermaid) {
                // Handle interaction with a Mermaid.
                Mermaid mermaid = (Mermaid) targetCreature;
                if (!this.hasPower() && !mermaid.hasPower()) {
                    // Both have no power; remove this Unicorn.
                    ecosystem[position] = null;
                } else if (this.hasPower() && !mermaid.hasPower()) {
                    // This Unicorn has power; replace the Mermaid.
                    ecosystem[targetPosition] = this;
                    ecosystem[position] = null;
                }
            }
        }
    }

    @Override
    public Creature breed() {
        // Create a new Unicorn with the same color and name.
        return new Unicorn(getColor(), name);
    }

    // Grants the Unicorn a Power-up and changes its color to purple.
    public void gainPower() {
        setColor("purple"); // Indicate power-up with a unique color.
        setHasPower(true); // Mark the Unicorn as powered up.
    }

    @Override
    public String display() {
        // Display "U*" if the Unicorn has a Power-up, otherwise "U".
        return hasPower() ? "U*" : "U";
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
        // Provide a string representation of the Unicorn's attributes.
        return "color: " + getColor() + " name: " + name + 
               " has power: " + hasPower() + 
               " has genie: " + hasGenie();
    }
}
