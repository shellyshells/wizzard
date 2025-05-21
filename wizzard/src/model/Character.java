// Character.java
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the player's character with attributes, inventory and skills
 */
public class Character {
    private String name;
    private Map<String, Integer> attributes;
    private List<String> inventory;
    private static final Map<String, String> ITEM_ICONS = new HashMap<>();

    // Initialize item icons
    static {
        ITEM_ICONS.put("Ancient Scroll", "📜");
        ITEM_ICONS.put("Mystical Crystal", "💎");
        ITEM_ICONS.put("Enchanted Amulet", "🔮");
        ITEM_ICONS.put("Healer's Potion", "🧪");
        ITEM_ICONS.put("Sacred Water", "🏺");
        ITEM_ICONS.put("Prophet's Staff", "🪄");
        ITEM_ICONS.put("Oracle's Eye", "👁️");
        ITEM_ICONS.put("Protection Charm", "🧿");
        ITEM_ICONS.put("Ancient Runes", "🪨");
        ITEM_ICONS.put("Vision Incense", "🧯");
    }

    /**
     * Constructor for a character
     * 
     * @param name The character's name
     */
    public Character(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
        this.inventory = new ArrayList<>();

        // Initialize base attributes for the prophet character
        attributes.put("wisdom", 10);
        attributes.put("intuition", 7);
        attributes.put("charisma", 8);
        attributes.put("perception", 7);
        attributes.put("knowledge", 6);
        attributes.put("energy", 20);

        // Add starting items to inventory
        inventory.add("Ancient Scroll");
        inventory.add("Mystical Crystal");
        inventory.add("Healer's Potion");
        inventory.add("Protection Charm");
    }

    /**
     * Adds an item to the inventory
     * @param item Name of the item to add
     */
    public void addItem(String item) {
        if (!inventory.contains(item)) {
            inventory.add(item);
        }
    }

    /**
     * Removes an item from the inventory
     * @param item The item to remove
     * @return true if the item was removed, false if it wasn't present
     */
    public boolean removeItem(String item) {
        return inventory.remove(item);
    }

    /**
     * Checks if the character has a specific item
     */
    public boolean hasItem(String item) {
        return inventory.contains(item);
    }

    /**
     * Modifies an attribute value
     * @param name Name of the attribute
     * @param value Value to add (can be negative)
     */
    public void modifyAttribute(String name, int value) {
        if (attributes.containsKey(name)) {
            int newValue = attributes.get(name) + value;
            // Ensure no negative attributes
            attributes.put(name, Math.max(0, newValue));
        } else {
            // If the attribute doesn't exist yet, create it
            attributes.put(name, Math.max(0, value));
        }
    }

    /**
     * Gets the value of an attribute
     */
    public int getAttribute(String name) {
        return attributes.getOrDefault(name, 0);
    }

    /**
     * Gets the icon associated with an item
     */
    public static String getItemIcon(String item) {
        return ITEM_ICONS.getOrDefault(item, "📦");
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInventory() {
        return new ArrayList<>(inventory);
    }

    public Map<String, Integer> getAttributes() {
        return new HashMap<>(attributes);
    }
}