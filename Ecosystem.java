import javax.swing.JFrame;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Ecosystem {
    private Creature[] ecosystem; // Array representing the ecosystem grid.
    private Random rand = new Random(); // Used for random positioning and events.
    private EcosystemGrid grid; // GUI component for visualizing the ecosystem.

    // List of colors that can be assigned to creatures.
    private static final List<String> ALLOWED_COLORS = Arrays.asList(
        "red", "orange", "pink", "black", "gray", "cyan", "magenta"
    );

    private Mermaid initialMermaid; // Reference to the initial Mermaid object.
    private Unicorn initialUnicorn; // Reference to the initial Unicorn object.

    // Constructor to initialize the ecosystem with a specified size.
    public Ecosystem(int size) {
        this.ecosystem = new Creature[size]; // Create an array to represent the ecosystem.
        populateEcosystem(); // Populate the ecosystem with Mermaid and Unicorn.
    }

    // Populates the ecosystem by assigning a Mermaid and a Unicorn to random positions.
    public void populateEcosystem() {
        Scanner scanner = new Scanner(System.in);
        List<String> availableColors = new ArrayList<>(ALLOWED_COLORS); // Clone list of allowed colors.

        // Prompt the user to enter details for the Mermaid.
        System.out.print("Enter Mermaid's name: ");
        String mermaidName = scanner.nextLine();
        System.out.println("Available colors for Mermaid: " + availableColors);
        System.out.print("Enter Mermaid's color: ");
        String mermaidColor = chooseValidColor(scanner, availableColors);
        availableColors.remove(mermaidColor); // Ensure Unicorn can't use the same color.

        // Prompt the user to enter details for the Unicorn.
        System.out.print("Enter Unicorn's name: ");
        String unicornName = scanner.nextLine();
        System.out.println("Available colors for Unicorn: " + availableColors);
        System.out.print("Enter Unicorn's color: ");
        String unicornColor = chooseValidColor(scanner, availableColors);

        // Assign random positions to the Mermaid and Unicorn, ensuring they don't overlap.
        int mermaidPosition = rand.nextInt(ecosystem.length - 1);
        int unicornPosition;
        do {
            unicornPosition = rand.nextInt(ecosystem.length - 1);
        } while (unicornPosition == mermaidPosition);

        // Initialize and place the creatures in the ecosystem array.
        initialMermaid = new Mermaid(mermaidColor, mermaidName);
        initialUnicorn = new Unicorn(unicornColor, unicornName);
        ecosystem[mermaidPosition] = initialMermaid;
        ecosystem[unicornPosition] = initialUnicorn;

        // Display the initial setup for user feedback.
        System.out.println("Initial setup:");
        System.out.println("Mermaid " + mermaidName + " placed at position " + mermaidPosition + " with color " + mermaidColor);
        System.out.println("Unicorn " + unicornName + " placed at position " + unicornPosition + " with color " + unicornColor);
    }

    // Ensures that the color chosen by the user is valid.
    public String chooseValidColor(Scanner scanner, List<String> availableColors) {
        String color;
        while (true) {
            color = scanner.nextLine().toLowerCase(); // Convert input to lowercase for consistency.
            if (availableColors.contains(color)) {
                break; // Exit loop if the color is valid.
            } else {
                System.out.println("Invalid color. Please choose from: " + availableColors);
                System.out.print("Enter a valid color: ");
            }
        }
        return color;
    }

    // Starts the simulation of the ecosystem.
    public void simulate() {
        // Set up a JFrame to visualize the ecosystem.
        JFrame frame = new JFrame("Ecosystem Simulation");
        grid = new EcosystemGrid(ecosystem); // Initialize grid with the ecosystem array.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(grid); // Add the grid to the frame.
        frame.pack();
        frame.setVisible(true);

        int step = 0; // Counter for simulation steps.
        while (true) {
            System.out.println("\n---- Simulation Step " + (step + 1) + " ----");

            // Perform actions for the Mermaid and Unicorn starting from step 1.
            if (step > 0) {
                processCreature(initialMermaid, "Mermaid");
            } else {
                System.out.println("Mermaid at initial position remains in normal state.");
            }

            if (step > 0) {
                processCreature(initialUnicorn, "Unicorn");
            } else {
                System.out.println("Unicorn at initial position remains in normal state.");
            }

            // Update the grid to reflect changes in the ecosystem.
            grid.updateEcosystem(ecosystem);

            // Check for a winner after each step.
            String winner = checkForWinner();
            if (winner != null) {
                grid.updateEcosystem(ecosystem); // Final grid update before ending simulation.
                System.out.println("\nThe " + winner + " wins by occupying all cells!");
                break;
            }

            // Pause for a brief moment to simulate real-time progression.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            step++;
        }
        System.out.println("---- Simulation Complete ----");
    }

    // Processes the state of a creature at its current position.
    public void processCreature(Creature creature, String creatureType) {
        int position = findCreaturePosition(creature); // Find the creature's position in the ecosystem.
        if (position != -1) {
            // Randomly assign Genie power, Power-up, or Normal state to the creature.
            if (rand.nextBoolean()) {
                creature.setHasGenie(true);
                creature.setHasPower(false);
                System.out.println(creatureType + " at position " + position + " gains Genie power and occupies three cells.");
                applyGeniePower(position, creature); // Apply Genie power's effect.
            } else if (rand.nextBoolean()) {
                creature.setHasPower(true);
                creature.setHasGenie(false);
                System.out.println(creatureType + " at position " + position + " gains Power-up and changes color.");
            } else {
                creature.setHasGenie(false);
                creature.setHasPower(false);
                System.out.println(creatureType + " at position " + position + " returns to normal state.");
            }

            // Move the creature if it doesn't have Genie power.
            if (!creature.hasGenie()) {
                int oldPosition = position;
                ecosystem[position] = null; // Clear the current position.
                creature.move(ecosystem, position); // Attempt to move the creature.
                int newPosition = findCreaturePosition(creature);
                if (newPosition == -1) {
                    ecosystem[oldPosition] = creature; // Restore to old position if movement fails.
                } else {
                    System.out.println("  Moves from position " + oldPosition + " to position " + newPosition + ".");
                }
            }
        }
    }

    // Applies Genie power to occupy the current and adjacent cells.
    public void applyGeniePower(int position, Creature creature) {
        int left = Math.max(0, position - 1); // Ensure left bound is within array.
        int right = Math.min(ecosystem.length - 1, position + 1); // Ensure right bound is within array.
        ecosystem[position] = creature; // Assign creature to its current position.
        if (left != position) ecosystem[left] = creature; // Assign to left position if valid.
        if (right != position) ecosystem[right] = creature; // Assign to right position if valid.
    }

    // Finds the current position of a given creature in the ecosystem.
    public int findCreaturePosition(Creature creature) {
        for (int i = 0; i < ecosystem.length; i++) {
            if (ecosystem[i] != null && ecosystem[i].equals(creature)) {
                return i; // Return the index where the creature is found.
            }
        }
        return -1; // Return -1 if the creature is not found.
    }

    // Checks if all cells are occupied by Mermaids or Unicorns.
    public String checkForWinner() {
        int mermaidCount = 0;
        int unicornCount = 0;

        for (Creature cell : ecosystem) {
            if (cell instanceof Mermaid) {
                mermaidCount++;
            } else if (cell instanceof Unicorn) {
                unicornCount++;
            } else {
                return null; // If any cell is empty, there is no winner.
            }
        }

        if (mermaidCount == ecosystem.length) return "Mermaid"; // Mermaid wins.
        if (unicornCount == ecosystem.length) return "Unicorn"; // Unicorn wins.

        return null; // No winner yet.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ensure the user enters a valid size for the ecosystem.
        int size;
        while (true) {
            System.out.print("Enter the number of cells in the ecosystem (must be greater than or equal to 6): ");
            size = scanner.nextInt();
            scanner.nextLine();
            if (size >= 6) {
                break;
            } else {
                System.out.println("Amount invalid. Please try again.");
            }
        }

        Ecosystem ecosystem = new Ecosystem(size); // Create a new ecosystem instance.
        ecosystem.simulate(); // Start the simulation.
    }
}
