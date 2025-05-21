// Encounter.java
package model;

/**
 * Represents an encounter between the player and an entity
 */
public class Encounter {
    private Character character;
    private Entity entity;
    private String encounterType; // conversation, vision, conflict
    private boolean isResolved;
    private String[] dialogueOptions;
    
    /**
     * Creates an encounter between a character and an entity
     * @param character The player's character
     * @param entity The entity to encounter
     */
    public Encounter(Character character, Entity entity) {
        this.character = character;
        this.entity = entity;
        this.encounterType = "conversation";
        this.isResolved = false;
        this.dialogueOptions = new String[0];
    }
    
    /**
     * Creates an encounter with a specified type
     * @param character The player's character
     * @param entity The entity to encounter
     * @param encounterType The type of encounter
     */
    public Encounter(Character character, Entity entity, String encounterType) {
        this.character = character;
        this.entity = entity;
        this.encounterType = encounterType;
        this.isResolved = false;
        this.dialogueOptions = new String[0];
    }
    
    /**
     * Executes a round of the encounter
     * @param action The action taken by the player ("persuade", "insight", "knowledge")
     * @return true if the player succeeds in the action
     */
    public boolean executeRound(String action) {
        Character player = getCharacter();
        
        // Apply different modifiers based on action
        int playerModifier = 0;
        int entityModifier = 0;
        
        if ("persuade".equals(action)) {
            playerModifier = player.getAttribute("charisma");
            entityModifier = 0;
        } else if ("insight".equals(action)) {
            playerModifier = player.getAttribute("intuition");
            entityModifier = -2; // Entity is less effective against insight
        } else if ("knowledge".equals(action)) {
            playerModifier = player.getAttribute("knowledge");
            entityModifier = -1;
        }
        
        // Calculate scores for player and entity
        int playerScore = rollDice() + playerModifier;
        int entityScore = entity.calculatePersuasionPower() + entityModifier;
        
        // Bonus for vision encounters
        if ("vision".equals(encounterType) && player.hasItem("Oracle's Eye")) {
            playerScore += 3;
        }
        
        // Player succeeds if their score is higher
        return playerScore > entityScore;
    }
    
    /**
     * Rolls 2d6 (two six-sided dice)
     * @return Result of the roll (2-12)
     */
    private int rollDice() {
        return (int)(Math.random() * 6) + 1 + (int)(Math.random() * 6) + 1;
    }
    
    // Getters and setters
    public Character getCharacter() {
        return character;
    }
    
    public Entity getEntity() {
        return entity;
    }
    
    public String getEncounterType() {
        return encounterType;
    }
    
    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }
    
    public boolean isResolved() {
        return isResolved;
    }
    
    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }
    
    public void setDialogueOptions(String[] options) {
        this.dialogueOptions = options;
    }
    
    public String[] getDialogueOptions() {
        return dialogueOptions;
    }
    
    @Override
    public String toString() {
        return "Encounter with " + entity.getName() + " (" + encounterType + ")";
    }
}