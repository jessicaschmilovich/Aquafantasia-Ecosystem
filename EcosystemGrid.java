import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EcosystemGrid extends JPanel {
    private static final int CELL_SIZE = 40;
    private Creature[] ecosystem;

    public EcosystemGrid(Creature[] ecosystem) {
        this.ecosystem = ecosystem;
        setPreferredSize(new Dimension(ecosystem.length * CELL_SIZE, CELL_SIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < ecosystem.length; i++) {
            Creature creature = ecosystem[i];

            if (creature instanceof Mermaid) {
                if (creature.hasGenie()) {
                    g.setColor(Color.GREEN);
                } else if (creature.hasPower()) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(parseColor(((Mermaid) creature).getColor()));
                }
            } else if (creature instanceof Unicorn) {
                if (creature.hasGenie()) {
                    g.setColor(Color.BLUE);
                } else if (creature.hasPower()) {
                    g.setColor(new Color(128, 0, 128)); // purple for unicorn power-up
                } else {
                    g.setColor(parseColor(((Unicorn) creature).getColor()));
                }
            } else {
                g.setColor(Color.WHITE);
            }

            g.fillRect(i * CELL_SIZE, 0, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(i * CELL_SIZE, 0, CELL_SIZE, CELL_SIZE);
        }
    }

    public Color parseColor(String color) {
        // Define basic colors and custom "gold" color
        switch (color.toLowerCase()) {
            case "red": return Color.RED;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
            case "purple": return new Color(128, 0, 128);
            case "black": return Color.BLACK;
            case "gray": return Color.GRAY;
            case "cyan": return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            case "gold": return new Color(255, 215, 0); // Custom gold color
            default:
                System.out.println("Unknown color: '" + color + "'. Defaulting to light gray.");
                return Color.LIGHT_GRAY;
        }
    }

    public void updateEcosystem(Creature[] newEcosystem) {
        this.ecosystem = newEcosystem;
        repaint();
    }
}