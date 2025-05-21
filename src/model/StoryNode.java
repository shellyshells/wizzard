// StoryNode.java - Updated with combat support
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
    private String content;
    private List<Choice> choices;
    private boolean isEndNode;
    private Entity entity; // For nodes with character encounters
    private Map<String, Integer> requiredAttributes; // Attributes needed to unlock certain choices
    private String backgroundImage; // Path to background image for this node
    private boolean combatTriggered; // Whether combat should be triggered at this node

    /**
     * Constructor for a story node
     * 
     * @param id The unique identifier for this node
     * @param title The title of the node
     * @param content The narrative text content
     * @param isEndNode Whether this node is an ending
     */
    public StoryNode(int id, String title, String content, boolean isEndNode) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.choices = new ArrayList<>();
        this.isEndNode = isEndNode;
        this.requiredAttributes = new HashMap<>();
        this.combatTriggered = false;
    }
    
    /**
     * Simple constructor without specifying if it's an ending
     */
    public StoryNode(int id, String title, String content) {
        this(id, title, content, false);
    }

    /**
     * Adds a possible choice for this story node
     * 
     * @param choice The choice to add
     */
    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public boolean isEndNode() {
        return isEndNode;
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
    
    /**
     * Sets whether combat is triggered at this node
     * @param triggered Whether combat is triggered
     */
    public void setCombatTriggered(boolean triggered) {
        this.combatTriggered = triggered;
    }
    
    /**
     * Checks if combat is triggered at this node
     * @return true if combat is triggered
     */
    public boolean isCombatTriggered() {
        return combatTriggered;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setEndNode(boolean endNode) {
        isEndNode = endNode;
    }

    @Override
    public String toString() {
        return title;
    }
}