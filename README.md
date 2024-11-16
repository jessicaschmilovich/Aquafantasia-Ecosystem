# Aquafantasia-Ecosystem

The Aquafantasia Ecosystem is a Java-based simulation that models an underwater world where mermaids and unicorns interact, breed, and gain power-ups like golden tridents, purple horns, and genie powers. The simulation uses Java Swing for graphics implementation and incorporates object-oriented principles such as inheritance and abstraction to manage creature behaviors, randomized movements, and dynamic interactions within a grid environment. The goal of the simulation is for one creature type to occupy the entire grid, claiming dominance.

**Features:**  

Grid-based gameplay:
- A dynamic grid represents the underwater realm, visually updated in real-time using Java Swing. Players personalize their mermaids and unicorns by assigning colors from red, orange, pink, black, gray, cyan, or magenta.  

Creature interactions:
- Breeding: If two mermaids or unicorns of the same type occupy adjacent cells while fighting, they breed to create another creature of their type.  
- Mermaids naturally have an advantage in the aquatic environment, often overpowering unicorns unless countered by power-ups.  

Power-ups:
- Poseidon's trident (gold): Enhances a mermaid's strength, allowing it to overcome stronger opponents.
- Purple horn: Grants unicorns an edge, boosting their ability to dominate battles.
- Genie bottle: Enables a creature to occupy three grid cells simultaneously, turning mermaid cells green and unicorn cells blue, overriding existing occupants.  

Randomized events:
- Power-ups and battles are driven by randomization, ensuring each simulation is unpredictable and unique.  

**Project Structure:**  

Creature.java: Abstract class defining shared attributes and methods for mermaids and unicorns, such as movement and power-up interactions.  

Ecosystem.java: Oversees the simulation logic, including initialization, user input for creature customization, and the main gameplay loop.  

EcosystemGrid.java: Manages the graphical representation of the grid using Java Swing, dynamically updating creature positions and grid states.  

Mermaid.java: Implements mermaid-specific behaviors, including the ability to wield Poseidon's trident and dominate underwater battles.  

Unicorn.java: Defines unicorn-specific mechanics, such as utilizing the purple horn to counter the mermaid's natural advantage.  

Genie.java: Handles the logic for the genie bottle power-up, allowing creatures to occupy three cells at once.  
