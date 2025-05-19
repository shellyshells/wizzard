// CombatSystem.java
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Handles combat mechanics between the player and entities
 */
public class CombatSystem {
    private Character player;
    private Entity opponent;
    private Random random = new Random();
    private List<String> combatLog;
    private Map<String, Integer> cooldowns;
    private int turnCount;
    
    // Combat actions and their base damage values
    private static final Map<String, Integer> ATTACK_DAMAGE_VALUES = new HashMap<>();
    
    static {
        ATTACK_DAMAGE_VALUES.put("quick_attack", 2);
        ATTACK_DAMAGE_VALUES.put("focused_strike", 4);
        ATTACK_DAMAGE_VALUES.put("defensive_stance", 0);
        ATTACK_DAMAGE_VALUES.put("mystic_blast", 5);
        ATTACK_DAMAGE_VALUES.put("prophetic_insight", 3);
        ATTACK_DAMAGE_VALUES.put("charismatic_approach", 2);
        ATTACK_DAMAGE_VALUES.put("knowledge_strike", 3);
        ATTACK_DAMAGE_VALUES.put("perceptive_counter", 4);
    }
    
    /**
     * Creates a new combat instance
     * @param player The player character
     * @param opponent The opposing entity
     */
    public CombatSystem(Character player, Entity opponent) {
        this.player = player;
        this.opponent = opponent;
        this.combatLog = new ArrayList<>();
        this.cooldowns = new HashMap<>();
        this.turnCount = 0;
        
        // Initialize combat log
        combatLog.add("Combat begins with " + opponent.getName() + "!");
        combatLog.add(opponent.getDescription());
        
        // Initialize cooldowns for special attacks
        cooldowns.put("mystic_blast", 0);
        cooldowns.put("prophetic_insight", 0);
    }
    
    /**
     * Gets the available combat actions for the player based on character type and items
     * @return List of available action IDs
     */
    public List<String> getAvailableActions() {
        List<String> actions = new ArrayList<>();
        
        // Basic actions available to everyone
        actions.add("quick_attack");
        actions.add("focused_strike");
        actions.add("defensive_stance");
        
        // Special actions based on attributes
        if (player.getAttribute("intuition") >= 8 || player.hasItem("Oracle's Eye")) {
            actions.add("prophetic_insight");
        }
        
        if (player.getAttribute("wisdom") >= 8 || player.hasItem("Prophet's Staff")) {
            actions.add("mystic_blast");
        }
        
        if (player.getAttribute("charisma") >= 8 || player.hasItem("Charm of Persuasion")) {
            actions.add("charismatic_approach");
        }
        
        if (player.getAttribute("knowledge") >= 8 || player.hasItem("Scholar's Tome")) {
            actions.add("knowledge_strike");
        }
        
        if (player.getAttribute("perception") >= 8 || player.hasItem("Seer's Lens")) {
            actions.add("perceptive_counter");
        }
        
        // Filter out actions on cooldown
        actions.removeIf(action -> cooldowns.getOrDefault(action, 0) > 0);
        
        return actions;
    }
    
    /**
     * Gets the display name for a combat action
     * @param actionId The action ID
     * @return The display name
     */
    public static String getActionName(String actionId) {
        switch (actionId) {
            case "quick_attack": return "Quick Attack";
            case "focused_strike": return "Focused Strike";
            case "defensive_stance": return "Defensive Stance";
            case "mystic_blast": return "Mystic Blast";
            case "prophetic_insight": return "Prophetic Insight";
            case "charismatic_approach": return "Charismatic Approach";
            case "knowledge_strike": return "Knowledge Strike";
            case "perceptive_counter": return "Perceptive Counter";
            default: return actionId;
        }
    }
    
    /**
     * Gets the description for a combat action
     * @param actionId The action ID
     * @return The description
     */
    public static String getActionDescription(String actionId) {
        switch (actionId) {
            case "quick_attack": 
                return "A swift attack that deals moderate damage. Higher chance to hit.";
            case "focused_strike": 
                return "A powerful, concentrated attack that deals high damage but may miss.";
            case "defensive_stance": 
                return "Take a defensive position, reducing damage from the next enemy attack by 50%.";
            case "mystic_blast": 
                return "Unleash mystical energy to damage the opponent. Has a 2-turn cooldown.";
            case "prophetic_insight": 
                return "Use prophetic abilities to predict and counter the opponent's next move. Has a 3-turn cooldown.";
            case "charismatic_approach":
                return "Use your charm to confuse and distract the opponent. Has a 2-turn cooldown.";
            case "knowledge_strike":
                return "Exploit your knowledge of the opponent's weaknesses. Has a 3-turn cooldown.";
            case "perceptive_counter":
                return "Use your keen perception to counter-attack. Has a 2-turn cooldown.";
            default: 
                return "Unknown action";
        }
    }
    
    /**
     * Executes a player combat action
     * @param actionId The action ID
     * @return true if combat continues, false if it's over
     */
    public boolean executePlayerAction(String actionId) {
        if (!getAvailableActions().contains(actionId)) {
            combatLog.add("That action is not available right now.");
            return true;
        }
        
        turnCount++;
        combatLog.add("Turn " + turnCount + ": You use " + getActionName(actionId) + ".");
        
        // Handle special actions
        if (actionId.equals("defensive_stance")) {
            combatLog.add("You take a defensive stance, preparing for the next attack.");
            player.modifyAttribute("defense_bonus", 2);
            
            // Execute opponent's turn
            return executeOpponentTurn();
        }
        
        if (actionId.equals("prophetic_insight")) {
            combatLog.add("You channel your prophetic abilities to predict " + opponent.getName() + "'s next move.");
            combatLog.add("The opponent appears momentarily confused, unable to act effectively.");
            
            // Set cooldown
            cooldowns.put("prophetic_insight", 3);
            
            // Skip opponent's turn and return
            return !isCombatOver();
        }
        
        // Calculate hit chance and damage
        boolean hits = calculateHitSuccess(actionId);
        
        if (hits) {
            int damage = calculateDamage(actionId);
            opponent.reduceEnergy(damage);
            
            combatLog.add("Your attack hits for " + damage + " damage!");
            combatLog.add(opponent.getName() + " has " + opponent.getEnergy() + " energy remaining.");
            
            // Set cooldowns for special attacks
            if (actionId.equals("mystic_blast")) {
                cooldowns.put("mystic_blast", 2);
            }
        } else {
            combatLog.add("Your attack misses!");
        }
        
        // Check if combat is over after player's turn
        if (isCombatOver()) {
            return false;
        }
        
        // Execute opponent's turn
        return executeOpponentTurn();
    }
    
    /**
     * Executes the opponent's turn
     * @return true if combat continues, false if it's over
     */
    private boolean executeOpponentTurn() {
        // Update cooldowns
        for (String action : cooldowns.keySet()) {
            int current = cooldowns.get(action);
            if (current > 0) {
                cooldowns.put(action, current - 1);
            }
        }
        
        // Check if player has defensive stance
        boolean playerDefending = player.getAttribute("defense_bonus") > 0;
        
        // Entity chooses an action based on its personality and current state
        String entityAction = chooseEntityAction();
        combatLog.add(opponent.getName() + " uses " + entityAction + ".");
        
        // Calculate hit chance
        int entityInfluence = opponent.getInfluence();
        boolean hits = random.nextInt(20) + entityInfluence > 8;
        
        if (hits) {
            // Base damage is based on entity's influence
            int damage = 1 + random.nextInt(opponent.getInfluence() / 2 + 1);
            
            // Adjust damage if player is defending
            if (playerDefending) {
                damage = Math.max(1, damage / 2);
                combatLog.add("Your defensive stance reduces the damage!");
                player.modifyAttribute("defense_bonus", -2); // Remove defensive bonus
            }
            
            player.modifyAttribute("energy", -damage);
            combatLog.add("The attack hits you for " + damage + " damage!");
            combatLog.add("You have " + player.getAttribute("energy") + " energy remaining.");
        } else {
            combatLog.add("The attack misses you!");
        }
        
        return !isCombatOver();
    }
    
    /**
     * Determines if the combat is over
     * @return true if either the player or opponent has no energy left
     */
    public boolean isCombatOver() {
        if (opponent.isOvercome()) {
            combatLog.add(opponent.getName() + " has been overcome!");
            return true;
        }
        
        if (player.getAttribute("energy") <= 0) {
            combatLog.add("You've been defeated by " + opponent.getName() + ".");
            
            // Check if player has a protection charm to prevent a fatal outcome
            if (player.hasItem("Protection Charm")) {
                combatLog.add("Your Protection Charm activates, shielding you from defeat!");
                combatLog.add("The charm crumbles to dust as its magic is spent.");
                player.removeItem("Protection Charm");
                player.modifyAttribute("energy", 5); // Restore some energy
                return false; // Combat continues
            }
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Determines if the player was victorious
     * @return true if the opponent has been overcome
     */
    public boolean playerVictorious() {
        return opponent.isOvercome();
    }
    
    /**
     * Calculates if an attack hits based on the action and player attributes
     * @param actionId The action ID
     * @return true if the attack hits
     */
    private boolean calculateHitSuccess(String actionId) {
        int baseChance;
        
        switch (actionId) {
            case "quick_attack":
                baseChance = 16; // High chance to hit
                break;
            case "focused_strike":
                baseChance = 12; // Moderate chance to hit
                break;
            case "mystic_blast":
                baseChance = 14; // Good chance to hit
                break;
            case "charismatic_approach":
                baseChance = 15; // Moderate chance to hit
                break;
            case "knowledge_strike":
                baseChance = 13; // Moderate chance to hit
                break;
            case "perceptive_counter":
                baseChance = 17; // Very high chance to hit
                break;
            default:
                baseChance = 15;
        }
        
        // Different attributes affect hit chance for different actions
        int attributeBonus = 0;
        switch (actionId) {
            case "quick_attack":
                attributeBonus = player.getAttribute("perception") / 3;
                break;
            case "focused_strike":
                attributeBonus = player.getAttribute("wisdom") / 3;
                break;
            case "mystic_blast":
                attributeBonus = player.getAttribute("intuition") / 2;
                break;
            case "charismatic_approach":
                attributeBonus = player.getAttribute("charisma") / 2;
                break;
            case "knowledge_strike":
                attributeBonus = player.getAttribute("knowledge") / 2;
                break;
            case "perceptive_counter":
                attributeBonus = player.getAttribute("perception") / 2;
                break;
        }
        
        // Roll d20 + attribute bonus vs target number
        return random.nextInt(20) + attributeBonus >= baseChance;
    }
    
    /**
     * Calculates damage for an attack based on the action and player attributes
     * @param actionId The action ID
     * @return The amount of damage
     */
    private int calculateDamage(String actionId) {
        int baseDamage = ATTACK_DAMAGE_VALUES.getOrDefault(actionId, 1);
        int attributeBonus = 0;
        
        // Apply attribute bonuses based on attack type
        switch (actionId) {
            case "quick_attack":
                attributeBonus = player.getAttribute("perception") / 3;
                break;
            case "focused_strike":
                attributeBonus = player.getAttribute("wisdom") / 3;
                break;
            case "mystic_blast":
                attributeBonus = player.getAttribute("intuition") / 2;
                // Bonus damage if player has Mystical Crystal
                if (player.hasItem("Mystical Crystal")) {
                    attributeBonus += 2;
                    combatLog.add("Your Mystical Crystal amplifies the blast!");
                }
                break;
            case "charismatic_approach":
                attributeBonus = player.getAttribute("charisma") / 2;
                // Bonus if player has Charm of Persuasion
                if (player.hasItem("Charm of Persuasion")) {
                    attributeBonus += 2;
                    combatLog.add("Your Charm of Persuasion enhances your approach!");
                }
                break;
            case "knowledge_strike":
                attributeBonus = player.getAttribute("knowledge") / 2;
                // Bonus if player has Scholar's Tome
                if (player.hasItem("Scholar's Tome")) {
                    attributeBonus += 2;
                    combatLog.add("Your Scholar's Tome reveals a critical weakness!");
                }
                break;
            case "perceptive_counter":
                attributeBonus = player.getAttribute("perception") / 2;
                // Bonus if player has Seer's Lens
                if (player.hasItem("Seer's Lens")) {
                    attributeBonus += 2;
                    combatLog.add("Your Seer's Lens helps you spot the perfect opening!");
                }
                break;
        }
        
        // Small random factor
        int randomFactor = random.nextInt(3);
        
        return Math.max(1, baseDamage + attributeBonus + randomFactor);
    }
    
    /**
     * Chooses an action for the entity based on its personality
     * @return The name of the entity's action
     */
    private String chooseEntityAction() {
        String[] possibleActions = {
            "strikes", "attacks", "lunges", "makes a forceful gesture",
            "attempts to overwhelm you", "focuses its energy"
        };
        
        // Customize based on entity type if desired
        if (opponent.getName().contains("Shadow") || opponent.getName().contains("Wraith")) {
            possibleActions = new String[]{
                "extends shadowy tendrils", "emits a chilling aura", 
                "distorts reality around you", "whispers dark thoughts"
            };
        } else if (opponent.getName().contains("Mystic")) {
            possibleActions = new String[]{
                "weaves an arcane pattern", "speaks a word of power",
                "channels mystical energy", "reads your intentions"
            };
        }
        
        return possibleActions[random.nextInt(possibleActions.length)];
    }
    
    /**
     * Gets the current combat log
     * @return List of combat log entries
     */
    public List<String> getCombatLog() {
        return new ArrayList<>(combatLog);
    }
}