// StoryNode.java
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a node in the story/narrative of the visual novel
 */
public class StoryNode {
    private int id;
    private String title;
    private String text;
    private List<Choice> availableChoices;
    private boolean isEnding;
    private Entity entity; // For nodes with character encounters
    private Map<String, Integer> requiredAttributes; // Attributes needed to unlock certain choices
    private String backgroundImage; // Path to background image for this node

    /**
     * Constructor for a story node
     * 
     * @param id The unique identifier for this node
     * @param title The title of the node
     * @param text The narrative text content
     * @param isEnding Whether this node is an ending
     */
    public StoryNode(int id, String title, String text, boolean isEnding) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.availableChoices = new ArrayList<>();
        this.isEnding = isEnding;
        this.requiredAttributes = new HashMap<>();
    }
    
    /**
     * Simple constructor without specifying if it's an ending
     */
    public StoryNode(int id, String title, String text) {
        this(id, title, text, false);
    }

    /**
     * Adds a possible choice for this story node
     * 
     * @param choice The choice to add
     */
    public void addChoice(Choice choice) {
        availableChoices.add(choice);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<Choice> getAvailableChoices() {
        return availableChoices;
    }

    public boolean isEnding() {
        return isEnding;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean hasEntity() {
        return entity != null;
    }
    
    public void setBackgroundImage(String path) {
        this.backgroundImage = path;
    }
    
    public String getBackgroundImage() {
        return backgroundImage;
    }
    
    /**
     * Adds a required attribute to access this node
     * @param attribute The attribute name
     * @param value The minimum value required
     */
    public void addRequiredAttribute(String attribute, int value) {
        requiredAttributes.put(attribute, value);
    }
    
    /**
     * Checks if a character meets all required attributes
     * @param character The character to check
     * @return True if character meets requirements
     */
    public boolean checkRequirements(Character character) {
        for (Map.Entry<String, Integer> entry : requiredAttributes.entrySet()) {
            if (character.getAttribute(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node " + id + ": " + title + " (Ending: " + isEnding + ")";
    }
}