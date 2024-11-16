import javax.swing.JFrame;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Ecosystem {
    private Creature[] ecosystem;
    private Random rand = new Random();
    private EcosystemGrid grid;
    
    private static final List<String> ALLOWED_COLORS = Arrays.asList(
        "red", "orange", "pink", "black", "gray", "cyan", "magenta"
    );

    private Mermaid initialMermaid;
    private Unicorn initialUnicorn;

    public Ecosystem(int size) {
        this.ecosystem = new Creature[size];
        populateEcosystem();
    }

    public void populateEcosystem() {
        Scanner scanner = new Scanner(System.in);
        List<String> availableColors = new ArrayList<>(ALLOWED_COLORS);

        System.out.print("Enter Mermaid's name: ");
        String mermaidName = scanner.nextLine();

        System.out.println("Available colors for Mermaid: " + availableColors);
        System.out.print("Enter Mermaid's color: ");
        String mermaidColor = chooseValidColor(scanner, availableColors);
        availableColors.remove(mermaidColor);

        System.out.print("Enter Unicorn's name: ");
        String unicornName = scanner.nextLine();

        System.out.println("Available colors for Unicorn: " + availableColors);
        System.out.print("Enter Unicorn's color: ");
        String unicornColor = chooseValidColor(scanner, availableColors);

        int mermaidPosition = rand.nextInt(ecosystem.length - 1);
        int unicornPosition;
        do {
            unicornPosition = rand.nextInt(ecosystem.length - 1);
        } while (unicornPosition == mermaidPosition);

        initialMermaid = new Mermaid(mermaidColor, mermaidName);
        initialUnicorn = new Unicorn(unicornColor, unicornName);
        ecosystem[mermaidPosition] = initialMermaid;
        ecosystem[unicornPosition] = initialUnicorn;

        System.out.println("Initial setup:");
        System.out.println("Mermaid " + mermaidName + " placed at position " + mermaidPosition + " with color " + mermaidColor);
        System.out.println("Unicorn " + unicornName + " placed at position " + unicornPosition + " with color " + unicornColor);
    }

    public String chooseValidColor(Scanner scanner, List<String> availableColors) {
        String color;
        while (true) {
            color = scanner.nextLine().toLowerCase();
            if (availableColors.contains(color)) {
                break;
            } else {
                System.out.println("Invalid color. Please choose from: " + availableColors);
                System.out.print("Enter a valid color: ");
            }
        }
        return color;
    }

    public void simulate() {
        JFrame frame = new JFrame("Ecosystem Simulation");
        grid = new EcosystemGrid(ecosystem);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(grid);
        frame.pack();
        frame.setVisible(true);

        int step = 0;
        while (true) {
            System.out.println("\n---- Simulation Step " + (step + 1) + " ----");

            // Process Mermaid
            if (step > 0) {
                processCreature(initialMermaid, "Mermaid");
            } else {
                System.out.println("Mermaid at initial position remains in normal state.");
            }

            // Process Unicorn
            if (step > 0) {
                processCreature(initialUnicorn, "Unicorn");
            } else {
                System.out.println("Unicorn at initial position remains in normal state.");
            }

            grid.updateEcosystem(ecosystem);
            
            String winner = checkForWinner();
            if (winner != null) {
                grid.updateEcosystem(ecosystem);
                System.out.println("\nThe " + winner + " wins by occupying all cells!");
                break;
            }
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            step++;
        }
        System.out.println("---- Simulation Complete ----");
    }

    public void processCreature(Creature creature, String creatureType) {
        int position = findCreaturePosition(creature);
        if (position != -1) {
            if (rand.nextBoolean()) {
                creature.setHasGenie(true);
                creature.setHasPower(false);
                System.out.println(creatureType + " at position " + position + " gains Genie power and occupies three cells.");
                applyGeniePower(position, creature);
            } else if (rand.nextBoolean()) {
                creature.setHasPower(true);
                creature.setHasGenie(false);
                System.out.println(creatureType + " at position " + position + " gains Power-up and changes color.");
            } else {
                creature.setHasGenie(false);
                creature.setHasPower(false);
                System.out.println(creatureType + " at position " + position + " returns to normal state.");
            }

            if (!creature.hasGenie()) {
                int oldPosition = position;
                ecosystem[position] = null;
                creature.move(ecosystem, position);
                int newPosition = findCreaturePosition(creature);
                if (newPosition == -1) {
                    ecosystem[oldPosition] = creature;
                } else {
                    System.out.println("  Moves from position " + oldPosition + " to position " + newPosition + ".");
                }
            }
        }
    }

    public void applyGeniePower(int position, Creature creature) {
        int left = Math.max(0, position - 1);
        int right = Math.min(ecosystem.length - 1, position + 1);
        ecosystem[position] = creature;

        if (left != position) ecosystem[left] = creature;
        if (right != position) ecosystem[right] = creature;
    }

    public int findCreaturePosition(Creature creature) {
        for (int i = 0; i < ecosystem.length; i++) {
            if (ecosystem[i] != null && ecosystem[i].equals(creature)) {
                return i;
            }
        }
        return -1;
    }

    public String checkForWinner() {
        int mermaidCount = 0;
        int unicornCount = 0;

        for (Creature cell : ecosystem) {
            if (cell instanceof Mermaid) {
                mermaidCount++;
            } else if (cell instanceof Unicorn) {
                unicornCount++;
            } else {
                return null;
            }
        }

        if (mermaidCount == ecosystem.length) return "Mermaid";
        if (unicornCount == ecosystem.length) return "Unicorn";

        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
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

        Ecosystem ecosystem = new Ecosystem(size);
        ecosystem.simulate();
    }
}
