// Story.java
package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a complete story/narrative with all its nodes and branches
 */
public class Story {
    private String title;
    private String description;
    private Map<Integer, StoryNode> nodes;
    private int startingNodeId;

    /**
     * Constructor
     * 
     * @param title The story title
     * @param description A brief description of the story
     */
    public Story(String title, String description) {
        this.title = title;
        this.description = description;
        this.nodes = new HashMap<>();
        this.startingNodeId = 1; // Default starting node
    }

    /**
     * Constructor with just a title
     * 
     * @param title The story title
     */
    public Story(String title) {
        this(title, "");
    }

    /**
     * Adds a node to the story
     * 
     * @param node The node to add
     */
    public void addNode(StoryNode node) {
        nodes.put(node.getId(), node);
    }

    /**
     * Gets the starting node of the story
     * 
     * @return The starting node
     */
    public StoryNode getStartingNode() {
        return nodes.get(startingNodeId);
    }

    /**
     * Gets all nodes in the story
     * 
     * @return A map of nodes with their IDs as keys
     */
    public Map<Integer, StoryNode> getNodes() {
        return nodes;
    }

    /**
     * Gets a node by its ID
     * 
     * @param id The node ID
     * @return The node with the given ID or null if not found
     */
    public StoryNode getNodeById(int id) {
        return nodes.get(id);
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setStartingNodeId(int startingNodeId) {
        this.startingNodeId = startingNodeId;
    }
}