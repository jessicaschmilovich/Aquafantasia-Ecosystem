public abstract class Creature {
    private String color;
    private boolean hasPower;
    private boolean hasGenie;

    public abstract void move(Creature[] ecosystem, int position);

    public abstract Creature breed();
    
    public abstract String getType();

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean hasPower() {
        return hasPower;
    }

    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public boolean hasGenie() {
        return hasGenie;
    }

    public void setHasGenie(boolean hasGenie) {
        this.hasGenie = hasGenie;
    }

    public abstract String display();
    
    public String toString()
    {
      return "color: " + color + " has power: " + hasPower + " has genie: " + hasGenie;
    }
}