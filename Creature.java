/**
 * An abstract class representing a generic creature in an ecosystem. 
 * Provides shared attributes and abstract methods for specific creature behaviors.
 */
public abstract class Creature {
    private String color; // The creature's color.
    private boolean hasPower; // Whether the creature has a special power.
    private boolean hasGenie; // Whether the creature has a genie.

    // Abstract method for creature movement, implemented by subclasses.
    public abstract void move(Creature[] ecosystem, int position);

    // Abstract method defining how the creature breeds, implemented by subclasses.
    public abstract Creature breed();

    // Abstract method returning the type of the creature.
    public abstract String getType();

    public String getColor() {
        return color; // Returns the creature's color.
    }

    public void setColor(String color) {
        this.color = color; // Sets the creature's color.
    }

    public boolean hasPower() {
        return hasPower; // Checks if the creature has a power.
    }

    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower; // Updates the creature's power status.
    }

    public boolean hasGenie() {
        return hasGenie; // Checks if the creature has a genie.
    }

    public void setHasGenie(boolean hasGenie) {
        this.hasGenie = hasGenie; // Updates the creature's genie status.
    }

    // Abstract method to display details of the creature.
    public abstract String display();

    @Override
    public String toString() {
        // Converts the creature's attributes into a readable string format.
        return "color: " + color + " has power: " + hasPower + " has genie: " + hasGenie;
    }
}
