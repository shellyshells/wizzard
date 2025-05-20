// Choice.java
package model;

/**
 * Represents a choice the player can make in the story
 */
public class Choice {
    private String text;
    private int targetNodeId;
    private String requiredSkill;
    private boolean triggersEncounter;
    private String requiredItem; // Item needed to select this choice, if any

    /**
     * Constructor for a choice
     * 
     * @param text The text describing the choice
     * @param targetNodeId The ID of the node this choice leads to
     */
    public Choice(String text, int targetNodeId) {
        this.text = text;
        this.targetNodeId = targetNodeId;
        this.triggersEncounter = false;
        this.requiredItem = null;
    }

    /**
     * Constructor with encounter option
     * 
     * @param text The text describing the choice
     * @param targetNodeId The ID of the node this choice leads to
     * @param triggersEncounter Whether this choice triggers an encounter
     */
    public Choice(String text, int targetNodeId, boolean triggersEncounter) {
        this.text = text;
        this.targetNodeId = targetNodeId;
        this.triggersEncounter = triggersEncounter;
        this.requiredItem = null;
    }
    
    /**
     * Constructor with item requirement
     * 
     * @param text The text describing the choice
     * @param targetNodeId The ID of the node this choice leads to
     * @param requiredItem The item required to select this choice
     */
    public Choice(String text, int targetNodeId, String requiredItem) {
        this.text = text;
        this.targetNodeId = targetNodeId;
        this.triggersEncounter = false;
        this.requiredItem = requiredItem;
    }

    /**
     * Constructor with skill requirement
     * 
     * @param text The text describing the choice
     * @param targetNodeId The ID of the node this choice leads to
     * @param requiredSkill The skill required to select this choice
     */
    public Choice(String text, int targetNodeId, String requiredSkill) {
        this.text = text;
        this.targetNodeId = targetNodeId;
        this.requiredSkill = requiredSkill;
        this.triggersEncounter = false;
        this.requiredItem = null;
    }

    // Getters
    public String getText() {
        return text;
    }

    public int getTargetNodeId() {
        return targetNodeId;
    }

    public boolean triggersEncounter() {
        return triggersEncounter;
    }
    
    public String getRequiredItem() {
        return requiredItem;
    }
    
    public boolean hasItemRequirement() {
        return requiredItem != null && !requiredItem.isEmpty();
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    @Override
    public String toString() {
        return text;
    }
}