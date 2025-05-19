// Entity.java - Updated with combat support
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean isCombatEntity; // Whether this entity engages in combat
    private String combatType; // The type of combat this entity specializes in
    private Map<String, Integer> resistances; // Resistance to different types of attacks
    private List<String> specialAbilities; // List of special abilities the entity can use

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
        this.isCombatEntity = false;
        this.combatType = "physical";
        this.resistances = new HashMap<>();
        this.specialAbilities = new ArrayList<>();
        
        // Initialize default resistances
        initializeDefaultResistances();
    }

    private void initializeDefaultResistances() {
        resistances.put("physical", 0);
        resistances.put("mystical", 0);
        resistances.put("shadow", 0);
        resistances.put("prophetic", 0);
        resistances.put("charismatic", 0);
        resistances.put("knowledge", 0);
        resistances.put("perceptive", 0);
    }

    /**
     * Constructor without description
     */
    public Entity(String name, int influence, int energy) {
        this(name, influence, energy, "An entity you encountered during your journey.");
    }
    
    /**
     * Combat-specific constructor
     * 
     * @param name The entity's name
     * @param influence The entity's influence level
     * @param energy The entity's energy level
     * @param description A description of the entity
     * @param combatType The type of combat this entity specializes in (physical, mystical, shadow)
     */
    public Entity(String name, int influence, int energy, String description, String combatType) {
        this(name, influence, energy, description);
        this.isCombatEntity = true;
        this.combatType = combatType;
        
        // Set resistances based on combat type
        setCombatTypeResistances(combatType);
        
        // Add special abilities based on combat type
        addCombatTypeAbilities(combatType);
    }
    
    private void setCombatTypeResistances(String combatType) {
        // Reset resistances
        initializeDefaultResistances();
        
        // Set primary resistance
        resistances.put(combatType, 2);
        
        // Set secondary resistance based on type
        switch (combatType.toLowerCase()) {
            case "physical":
                resistances.put("perceptive", 1);
                break;
            case "mystical":
                resistances.put("prophetic", 1);
                break;
            case "shadow":
                resistances.put("charismatic", 1);
                break;
            case "prophet":
                resistances.put("knowledge", 1);
                break;
        }
    }
    
    private void addCombatTypeAbilities(String combatType) {
        switch (combatType.toLowerCase()) {
            case "physical":
                specialAbilities.add("brutal_strike");
                specialAbilities.add("defensive_stance");
                break;
            case "mystical":
                specialAbilities.add("arcane_shield");
                specialAbilities.add("energy_blast");
                break;
            case "shadow":
                specialAbilities.add("shadow_step");
                specialAbilities.add("dark_embrace");
                break;
            case "prophet":
                specialAbilities.add("future_sight");
                specialAbilities.add("reality_bend");
                break;
        }
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
        // Combat entities have different resistance vs. standard entities
        int actualAmount;
        
        if (isCombatEntity) {
            // Apply resistance based on attack type
            String attackType = determineAttackType(amount);
            int resistance = resistances.getOrDefault(attackType, 0);
            actualAmount = Math.max(1, amount - resistance);
        } else {
            // Standard entities have higher resistance (halved effect)
            actualAmount = Math.max(1, amount / 2);
        }
        
        this.energy = Math.max(0, this.energy - actualAmount);
        System.out.println(name + " suffers " + actualAmount + " points of energy loss!");
    }
    
    private String determineAttackType(int damage) {
        // This would be called by the combat system to determine the type of attack
        // For now, we'll use a simple heuristic based on damage amount
        if (damage >= 5) return "physical";
        if (damage >= 4) return "mystical";
        if (damage >= 3) return "shadow";
        return "physical";
    }

    /**
     * Checks if the entity has been overcome
     * 
     * @return true if energy is at 0
     */
    public boolean isOvercome() {
        return energy <= 0;
    }
    
    /**
     * Sets whether this is a combat entity
     * @param isCombatEntity Whether entity engages in combat
     */
    public void setCombatEntity(boolean isCombatEntity) {
        this.isCombatEntity = isCombatEntity;
    }
    
    /**
     * Checks if this is a combat entity
     * @return true if combat entity
     */
    public boolean isCombatEntity() {
        return isCombatEntity;
    }
    
    /**
     * Gets the combat type of this entity
     * @return The combat type (physical, mystical, shadow)
     */
    public String getCombatType() {
        return combatType;
    }
    
    /**
     * Sets the combat type
     * @param combatType The combat type
     */
    public void setCombatType(String combatType) {
        this.combatType = combatType;
    }
    
    /**
     * Gets a special ability description based on combat type
     * @return The special ability description
     */
    public String getSpecialAbilityDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append("Special abilities: ");
        
        for (String ability : specialAbilities) {
            desc.append("\n- ").append(getAbilityDescription(ability));
        }
        
        return desc.toString();
    }
    
    private String getAbilityDescription(String ability) {
        switch (ability) {
            case "brutal_strike":
                return "A powerful physical attack that ignores some defense";
            case "defensive_stance":
                return "Takes a defensive position, reducing incoming damage";
            case "arcane_shield":
                return "Creates a magical barrier that absorbs damage";
            case "energy_blast":
                return "Releases a burst of mystical energy";
            case "shadow_step":
                return "Moves through shadows to avoid attacks";
            case "dark_embrace":
                return "Wraps the target in shadow, reducing their effectiveness";
            case "future_sight":
                return "Predicts and counters the next attack";
            case "reality_bend":
                return "Temporarily alters reality to gain an advantage";
            default:
                return "Unknown ability";
        }
    }

    public List<String> getSpecialAbilities() {
        return new ArrayList<>(specialAbilities);
    }
    
    public int getResistance(String attackType) {
        return resistances.getOrDefault(attackType, 0);
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