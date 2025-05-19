// Choice.java
package model;

/**
 * Represents a choice the player can make in the story
 */
public class Choice {
    private String text;
    private int destinationNodeId;
    private boolean triggersEncounter;
    private String requiredItem; // Item needed to select this choice, if any

    /**
     * Constructor for a choice
     * 
     * @param text The text describing the choice
     * @param destinationNodeId The ID of the node this choice leads to
     */
    public Choice(String text, int destinationNodeId) {
        this.text = text;
        this.destinationNodeId = destinationNodeId;
        this.triggersEncounter = false;
        this.requiredItem = null;
    }

    /**
     * Constructor with encounter option
     * 
     * @param text The text describing the choice
     * @param destinationNodeId The ID of the node this choice leads to
     * @param triggersEncounter Whether this choice triggers an encounter
     */
    public Choice(String text, int destinationNodeId, boolean triggersEncounter) {
        this.text = text;
        this.destinationNodeId = destinationNodeId;
        this.triggersEncounter = triggersEncounter;
        this.requiredItem = null;
    }
    
    /**
     * Constructor with item requirement
     * 
     * @param text The text describing the choice
     * @param destinationNodeId The ID of the node this choice leads to
     * @param requiredItem The item required to select this choice
     */
    public Choice(String text, int destinationNodeId, String requiredItem) {
        this.text = text;
        this.destinationNodeId = destinationNodeId;
        this.triggersEncounter = false;
        this.requiredItem = requiredItem;
    }

    // Getters
    public String getText() {
        return text;
    }

    public int getDestinationNodeId() {
        return destinationNodeId;
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

    @Override
    public String toString() {
        return text + " (leads to node " + destinationNodeId + ")";
    }
}