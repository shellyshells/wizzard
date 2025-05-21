// Inventory.java
package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for managing player's inventory with item descriptions and effects
 */
public class Inventory {
    // Maps item names to their descriptions
    private static final Map<String, String> ITEM_DESCRIPTIONS = new HashMap<>();
    
    // Maps item names to their effects when used
    private static final Map<String, String> ITEM_EFFECTS = new HashMap<>();
    
    // Initialize item descriptions
    static {
        ITEM_DESCRIPTIONS.put("Ancient Scroll", "A yellowed parchment containing cryptic prophecies written in an ancient language.");
        ITEM_DESCRIPTIONS.put("Mystical Crystal", "A transparent crystal that glows with an inner light when near sources of magical energy.");
        ITEM_DESCRIPTIONS.put("Enchanted Amulet", "A silver amulet inscribed with protective runes that warm to the touch when danger is near.");
        ITEM_DESCRIPTIONS.put("Healer's Potion", "A small vial containing a sweet-smelling green liquid that can restore vitality.");
        ITEM_DESCRIPTIONS.put("Sacred Water", "Water collected from a sacred spring said to have purifying properties.");
        ITEM_DESCRIPTIONS.put("Prophet's Staff", "A wooden staff carved with spiraling patterns that amplifies prophetic visions.");
        ITEM_DESCRIPTIONS.put("Oracle's Eye", "A small glass orb that reveals hidden truths when gazed through.");
        ITEM_DESCRIPTIONS.put("Protection Charm", "A small cloth bag containing herbs and stones that wards against malevolent entities.");
        ITEM_DESCRIPTIONS.put("Ancient Runes", "A set of stone tablets carved with symbols of an ancient magical language.");
        ITEM_DESCRIPTIONS.put("Vision Incense", "Aromatic incense that, when burned, helps induce prophetic visions.");
    }
    
    // Initialize item effects
    static {
        ITEM_EFFECTS.put("Ancient Scroll", "Provides +2 to Knowledge checks when consulted before an encounter.");
        ITEM_EFFECTS.put("Mystical Crystal", "Reveals hidden magical energies and can be used to store energy.");
        ITEM_EFFECTS.put("Enchanted Amulet", "Grants +2 to all resistance checks against hostile entities.");
        ITEM_EFFECTS.put("Healer's Potion", "Restores 4 points of Energy when consumed.");
        ITEM_EFFECTS.put("Sacred Water", "Cleanses corruption and can restore 2 points of Energy.");
        ITEM_EFFECTS.put("Prophet's Staff", "Enhances prophetic abilities, granting +3 to Vision encounters.");
        ITEM_EFFECTS.put("Oracle's Eye", "Allows you to see through deceptions and illusions.");
        ITEM_EFFECTS.put("Protection Charm", "Provides a one-time protection against a fatal encounter.");
        ITEM_EFFECTS.put("Ancient Runes", "Can be used to decipher ancient languages and magical scripts.");
        ITEM_EFFECTS.put("Vision Incense", "Induces a prophetic vision that may reveal future paths.");
    }
    
    /**
     * Gets a description for an item
     * @param itemName The name of the item
     * @return The item description or a default if not found
     */
    public static String getItemDescription(String itemName) {
        return ITEM_DESCRIPTIONS.getOrDefault(itemName, "An item you acquired on your journey.");
    }
    
    /**
     * Gets the effect for an item
     * @param itemName The name of the item
     * @return The item effect description or a default if not found
     */
    public static String getItemEffect(String itemName) {
        return ITEM_EFFECTS.getOrDefault(itemName, "No special effects.");
    }
    
    /**
     * Uses an item and applies its effect to a character
     * @param character The character using the item
     * @param itemName The name of the item to use
     * @return Description of what happened when the item was used
     */
    public static String useItem(Character character, String itemName) {
        if (!character.hasItem(itemName)) {
            return "You don't have that item.";
        }
        
        String result = "You used the " + itemName + ".";
        
        // Apply effect based on item
        switch (itemName) {
            case "Healer's Potion":
                character.modifyAttribute("energy", 4);
                character.removeItem(itemName);
                result += " You feel revitalized (+4 Energy).";
                break;
                
            case "Sacred Water":
                character.modifyAttribute("energy", 2);
                character.removeItem(itemName);
                result += " You feel cleansed and refreshed (+2 Energy).";
                break;
                
            case "Vision Incense":
                character.modifyAttribute("intuition", 1);
                character.removeItem(itemName);
                result += " The smoke swirls around you, enhancing your intuition (+1 Intuition).";
                break;
                
            case "Protection Charm":
                // Not consumed until needed in an encounter
                result = "The Protection Charm must be worn, not used directly. It will activate automatically when needed.";
                break;
                
            default:
                result = "This item cannot be used directly.";
        }
        
        return result;
    }
}