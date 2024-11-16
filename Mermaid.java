import java.util.Random;

public class Mermaid extends Creature {
    private static final Random rand = new Random();
    private String name;

    public Mermaid(String color, String name) {
        setColor(color);
        this.name = name;
    }
    
    public String getType()
    {
      return "Mermaid";
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
            } else if (targetCreature instanceof Mermaid) {
                // Attempt breeding if target is another Mermaid
                ecosystem[position] = null;
                Creature newMermaid = this.breed();
                placeInEmptyCell(ecosystem, newMermaid);
            } else if (targetCreature instanceof Unicorn) {
                Unicorn unicorn = (Unicorn) targetCreature;
                if (!unicorn.hasPower() || this.hasPower()) {
                    ecosystem[targetPosition] = this;
                    ecosystem[position] = null;
                }
            }
        }
    }

    @Override
    public Creature breed() {
        return new Mermaid(getColor(), name);
    }

    public void gainPower() {
        setColor("gold");
        setHasPower(true);
    }

    @Override
    public String display() {
        return hasPower() ? "M*" : "M";
    }

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