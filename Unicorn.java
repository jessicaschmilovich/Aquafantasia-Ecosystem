import java.util.Random;

public class Unicorn extends Creature {
    private static final Random rand = new Random();
    private String name;

    public Unicorn(String color, String name) {
        setColor(color);  // Set initial user-defined color
        this.name = name;
    }
    
    public String getType()
    {
      return "Unicorn";
    }

    @Override
    public void move(Creature[] ecosystem, int position) {
        if (hasGenie()) {
            Genie.applyGeniePower(ecosystem, position, this);
            return;
        }

        // Random movement: -1 (left), 0 (stay), or +1 (right)
        int moveDirection = rand.nextInt(3) - 1;
        int targetPosition = position + moveDirection;

        // Ensure target position is within bounds
        if (targetPosition >= 0 && targetPosition < ecosystem.length) {
            Creature targetCreature = ecosystem[targetPosition];
            if (targetCreature == null) { // Move to empty spot
                ecosystem[targetPosition] = this;
                ecosystem[position] = null;
            } else if (targetCreature instanceof Unicorn) {
                // Attempt breeding if target is another Unicorn
                ecosystem[position] = null;
                Creature newUnicorn = this.breed();
                placeInEmptyCell(ecosystem, newUnicorn);
            } else if (targetCreature instanceof Mermaid) {
                Mermaid mermaid = (Mermaid) targetCreature;
                if (!this.hasPower() && !mermaid.hasPower()) {
                    ecosystem[position] = null;
                } else if (this.hasPower() && !mermaid.hasPower()) {
                    ecosystem[targetPosition] = this;
                    ecosystem[position] = null;
                }
            }
        }
    }

    @Override
    public Creature breed() {
        return new Unicorn(getColor(), name);  // Breed a new Unicorn with the current color
    }

    public void gainPower() {
        setColor("purple");  // Set color to purple for Power-up
        setHasPower(true);
    }

    @Override
    public String display() {
        return hasPower() ? "U*" : "U";  // Display "U*" if Unicorn has Power-up
    }

    // Helper method to place a new creature in an empty cell in the ecosystem
    public void placeInEmptyCell(Creature[] ecosystem, Creature creature) {
        int emptyPos;
        do {
            emptyPos = rand.nextInt(ecosystem.length);
        } while (ecosystem[emptyPos] != null);
        ecosystem[emptyPos] = creature;
    }
    
    public String toString()
    {
      return "color: " + getColor() + " name: " + name + " has power: " + hasPower() + " has genie: " + hasGenie();
    }
}