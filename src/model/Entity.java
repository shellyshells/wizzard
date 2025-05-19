// Entity.java
package model;

/**
 * Represents an entity (person, creature, etc.) in the story
 */
public class Entity {
    private String name;
    private int influence;
    private int energy;
    private int energyMax;
    private String description;
    private String relationship; // Friendly, neutral, hostile
    private String dialogueStyle; // How this entity talks

    /**
     * Constructor with all parameters
     * 
     * @param name The entity's name
     * @param influence The entity's influence level
     * @param energy The entity's energy level
     * @param description A description of the entity
     */
    public Entity(String name, int influence, int energy, String description) {
        this.name = name;
        this.influence = influence;
        this.energy = energy;
        this.energyMax = energy;
        this.description = description;
        this.relationship = "neutral";
        this.dialogueStyle = "normal";
    }

    /**
     * Constructor without description
     */
    public Entity(String name, int influence, int energy) {
        this(name, influence, energy, "An entity you encountered during your journey.");
    }

    /**
     * Calculates the entity's persuasion power (influence + dice roll)
     * 
     * @return The persuasion power
     */
    public int calculatePersuasionPower() {
        return influence + (int)(Math.random() * 6) + 1 + (int)(Math.random() * 6) + 1;
    }

    /**
     * Reduces the entity's energy when affected by the player's actions
     * 
     * @param amount Amount of energy to reduce
     */
    public void reduceEnergy(int amount) {
        // Entities have a resistance to energy loss (halved effect)
        int actualAmount = Math.max(1, amount / 2);
        this.energy = Math.max(0, this.energy - actualAmount);
        System.out.println("The entity suffers " + actualAmount + " points of energy loss!");
    }

    /**
     * Checks if the entity has been overcome
     * 
     * @return true if energy is at 0
     */
    public boolean isOvercome() {
        return energy <= 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getInfluence() {
        return influence;
    }

    public int getEnergy() {
        return energy;
    }

    public int getEnergyMax() {
        return energyMax;
    }

    public String getDescription() {
        return description;
    }
    
    public String getRelationship() {
        return relationship;
    }
    
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    public String getDialogueStyle() {
        return dialogueStyle;
    }
    
    public void setDialogueStyle(String dialogueStyle) {
        this.dialogueStyle = dialogueStyle;
    }
}