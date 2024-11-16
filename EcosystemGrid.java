import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EcosystemGrid extends JPanel {
    private static final int CELL_SIZE = 40; // Size of each cell in the grid.
    private Creature[] ecosystem; // Array representing the creatures in the ecosystem.

    // Constructor initializes the ecosystem and sets the grid size based on the ecosystem length.
    public EcosystemGrid(Creature[] ecosystem) {
        this.ecosystem = ecosystem;
        setPreferredSize(new Dimension(ecosystem.length * CELL_SIZE, CELL_SIZE)); // Set panel size.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear previous drawings.

        // Iterate through the ecosystem array to draw each cell.
        for (int i = 0; i < ecosystem.length; i++) {
            Creature creature = ecosystem[i];

            // Determine the cell color based on the creature type and state.
            if (creature instanceof Mermaid) {
                if (creature.hasGenie()) {
                    g.setColor(Color.GREEN); // Green for Genie-powered Mermaid.
                } else if (creature.hasPower()) {
                    g.setColor(Color.YELLOW); // Yellow for Mermaid with power-up.
                } else {
                    g.setColor(parseColor(((Mermaid) creature).getColor())); // Mermaid's base color.
                }
            } else if (creature instanceof Unicorn) {
                if (creature.hasGenie()) {
                    g.setColor(Color.BLUE); // Blue for Genie-powered Unicorn.
                } else if (creature.hasPower()) {
                    g.setColor(new Color(128, 0, 128)); // Purple for Unicorn with power-up.
                } else {
                    g.setColor(parseColor(((Unicorn) creature).getColor())); // Unicorn's base color.
                }
            } else {
                g.setColor(Color.WHITE); // Empty cell is white.
            }

            // Fill the cell with the determined color.
            g.fillRect(i * CELL_SIZE, 0, CELL_SIZE, CELL_SIZE);

            // Draw the cell's border in black.
            g.setColor(Color.BLACK);
            g.drawRect(i * CELL_SIZE, 0, CELL_SIZE, CELL_SIZE);
        }
    }

    // Parses a color name into a Color object. Includes basic and custom colors.
    public Color parseColor(String color) {
        switch (color.toLowerCase()) {
            case "red": return Color.RED;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
            case "purple": return new Color(128, 0, 128); // Custom purple color.
            case "black": return Color.BLACK;
            case "gray": return Color.GRAY;
            case "cyan": return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            case "gold": return new Color(255, 215, 0); // Custom gold color.
            default:
                System.out.println("Unknown color: '" + color + "'. Defaulting to light gray.");
                return Color.LIGHT_GRAY; // Default color for unrecognized inputs.
        }
    }

    // Updates the ecosystem and refreshes the grid display.
    public void updateEcosystem(Creature[] newEcosystem) {
        this.ecosystem = newEcosystem; // Replace the ecosystem with the updated array.
        repaint(); // Trigger a repaint to reflect changes visually.
    }
}
