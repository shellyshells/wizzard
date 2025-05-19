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
        ATTACK_DAMAGE_VALUES.put("light_beam", 4);  // New light-based attack
        ATTACK_DAMAGE_VALUES.put("nature_bond", 3); // New nature-based attack
        ATTACK_DAMAGE_VALUES.put("healing_touch", 2); // New healing-based attack
    }
    
    // Item effects and their descriptions
    private static final Map<String, String> ITEM_EFFECTS = new HashMap<>();
    static {
        ITEM_EFFECTS.put("Prophet's Staff", "Enhances mystical abilities and wisdom-based attacks");
        ITEM_EFFECTS.put("Oracle's Eye", "Improves prophetic insights and intuition-based actions");
        ITEM_EFFECTS.put("Vision Incense", "Temporarily boosts perception and prophetic abilities");
        ITEM_EFFECTS.put("Ancient Runes", "Enhances mystical knowledge and arcane power");
        ITEM_EFFECTS.put("Ancient Scroll", "Improves knowledge-based attacks and wisdom");
        ITEM_EFFECTS.put("Mystical Crystal", "Amplifies mystical energy and arcane abilities");
        ITEM_EFFECTS.put("Protection Charm", "Shields from fatal damage once");
        ITEM_EFFECTS.put("Sacred Water", "Restores energy and purifies negative effects");
        ITEM_EFFECTS.put("Shadow Crystal", "Enhances shadow-based abilities and stealth");
        ITEM_EFFECTS.put("Dark Essence", "Strengthens shadow attacks and resistance");
        ITEM_EFFECTS.put("Void Shard", "Allows brief phase through reality");
        ITEM_EFFECTS.put("Night's Whisper", "Improves stealth and shadow manipulation");
        ITEM_EFFECTS.put("Eclipse Fragment", "Temporarily weakens enemy's perception");
        ITEM_EFFECTS.put("Arcane Dust", "Enhances mystical abilities temporarily");
        ITEM_EFFECTS.put("Spell Fragment", "Improves spell effectiveness");
        ITEM_EFFECTS.put("Mana Essence", "Restores mystical energy");
        ITEM_EFFECTS.put("Rune Shard", "Strengthens runic abilities");
        ITEM_EFFECTS.put("Future Fragment", "Grants brief glimpse of enemy's next move");
        ITEM_EFFECTS.put("Time Crystal", "Allows brief manipulation of combat timing");
        ITEM_EFFECTS.put("Destiny Shard", "Increases chance of critical hits");
        ITEM_EFFECTS.put("Scholar's Tome", "Improves knowledge-based attacks and wisdom");
        ITEM_EFFECTS.put("Wisdom Fragment", "Temporarily boosts wisdom and insight");
        ITEM_EFFECTS.put("Knowledge Crystal", "Enhances understanding of enemy weaknesses");
        ITEM_EFFECTS.put("Memory Shard", "Allows recall of previous combat patterns");
        ITEM_EFFECTS.put("Healer's Potion", "Restores significant energy");
        ITEM_EFFECTS.put("Strength Elixir", "Temporarily boosts physical attacks");
        ITEM_EFFECTS.put("Vitality Crystal", "Enhances natural healing");
        ITEM_EFFECTS.put("Endurance Shard", "Increases energy capacity temporarily");
        ITEM_EFFECTS.put("Shadow Sight", "Reveals hidden enemies and weak points");
        ITEM_EFFECTS.put("Arcane Insight", "Improves understanding of magical attacks");
        ITEM_EFFECTS.put("Future Glimpse", "Predicts enemy's next three moves");
        ITEM_EFFECTS.put("Ancient Knowledge", "Reveals enemy's weaknesses and patterns");
        ITEM_EFFECTS.put("Charm of Persuasion", "Enhances social and charismatic abilities");
        ITEM_EFFECTS.put("Voice of Authority", "Strengthens command over mystical forces");
        ITEM_EFFECTS.put("Silver Tongue", "Improves negotiation and social combat");
        ITEM_EFFECTS.put("Diplomatic Grace", "Enhances peaceful resolution chances");
        ITEM_EFFECTS.put("Inspiring Presence", "Boosts morale and combat effectiveness");
        
        // New Light Items
        ITEM_EFFECTS.put("Radiant Crystal", "Channels pure light energy");
        ITEM_EFFECTS.put("Sun's Blessing", "Brings warmth and clarity");
        ITEM_EFFECTS.put("Dawn Fragment", "Heralds new beginnings");
        ITEM_EFFECTS.put("Light's Grace", "Illuminates the path forward");
        
        // New Nature Items
        ITEM_EFFECTS.put("Ancient Seed", "Connects with natural forces");
        ITEM_EFFECTS.put("Forest Heart", "Harmonizes with life energy");
        ITEM_EFFECTS.put("Earth's Gift", "Grounds and stabilizes");
        ITEM_EFFECTS.put("Life Essence", "Nurtures and grows");
        
        // New Healing Items
        ITEM_EFFECTS.put("Healing Light", "Restores and rejuvenates");
        ITEM_EFFECTS.put("Peace Crystal", "Brings harmony and balance");
        ITEM_EFFECTS.put("Hope Shard", "Inspires and uplifts");
        ITEM_EFFECTS.put("Compassion's Touch", "Heals through understanding");
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
        
        // Log active item effects
        logActiveItemEffects();
        
        // Initialize cooldowns for special attacks
        cooldowns.put("mystic_blast", 0);
        cooldowns.put("prophetic_insight", 0);
    }
    
    private void logActiveItemEffects() {
        for (String item : player.getInventory()) {
            String effect = ITEM_EFFECTS.get(item);
            if (effect != null) {
                combatLog.add("Your " + item + " is active: " + effect);
            }
        }
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
        
        // Special actions based on attributes and items
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
        
        // New light-based actions
        if (player.getAttribute("wisdom") >= 8 || player.hasItem("Radiant Crystal")) {
            actions.add("light_beam");
        }
        
        // New nature-based actions
        if (player.getAttribute("intuition") >= 8 || player.hasItem("Ancient Seed")) {
            actions.add("nature_bond");
        }
        
        // New healing-based actions
        if (player.getAttribute("charisma") >= 8 || player.hasItem("Healing Light")) {
            actions.add("healing_touch");
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
            case "light_beam": return "Light Beam";
            case "nature_bond": return "Nature Bond";
            case "healing_touch": return "Healing Touch";
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
            case "light_beam": 
                return "Channel pure light energy to damage and purify. Has a 2-turn cooldown.";
            case "nature_bond": 
                return "Connect with natural forces to strengthen and protect. Has a 3-turn cooldown.";
            case "healing_touch": 
                return "Use healing energy to restore and rejuvenate. Has a 2-turn cooldown.";
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
            
            // Check for various protection items
            if (player.hasItem("Protection Charm")) {
                combatLog.add("Your Protection Charm activates, shielding you from defeat!");
                combatLog.add("The charm crumbles to dust as its magic is spent.");
                player.removeItem("Protection Charm");
                player.modifyAttribute("energy", 5);
                return false;
            }
            
            if (player.hasItem("Sacred Water")) {
                combatLog.add("Your Sacred Water purifies the negative energy!");
                combatLog.add("The water evaporates as it heals you.");
                player.removeItem("Sacred Water");
                player.modifyAttribute("energy", 8);
                return false;
            }
            
            if (player.hasItem("Healer's Potion")) {
                combatLog.add("Your Healer's Potion restores your vitality!");
                player.removeItem("Healer's Potion");
                player.modifyAttribute("energy", 10);
                return false;
            }
            
            if (player.hasItem("Vitality Crystal")) {
                combatLog.add("Your Vitality Crystal channels healing energy!");
                player.removeItem("Vitality Crystal");
                player.modifyAttribute("energy", 6);
                return false;
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
        int itemBonus = 0;
        
        switch (actionId) {
            case "quick_attack":
                baseChance = 16;
                if (player.hasItem("Endurance Shard")) {
                    itemBonus += 2;
                }
                break;
            case "focused_strike":
                baseChance = 12;
                if (player.hasItem("Strength Elixir")) {
                    itemBonus += 2;
                }
                break;
            case "mystic_blast":
                baseChance = 14;
                if (player.hasItem("Mystical Crystal")) {
                    itemBonus += 2;
                }
                break;
            case "charismatic_approach":
                baseChance = 15;
                if (player.hasItem("Charm of Persuasion")) {
                    itemBonus += 2;
                }
                break;
            case "knowledge_strike":
                baseChance = 13;
                if (player.hasItem("Scholar's Tome")) {
                    itemBonus += 2;
                }
                break;
            case "perceptive_counter":
                baseChance = 17;
                if (player.hasItem("Shadow Sight")) {
                    itemBonus += 2;
                }
                break;
            case "light_beam":
                baseChance = 14;
                if (player.hasItem("Radiant Crystal")) {
                    itemBonus += 2;
                }
                break;
            case "nature_bond":
                baseChance = 15;
                if (player.hasItem("Ancient Seed")) {
                    itemBonus += 2;
                }
                break;
            case "healing_touch":
                baseChance = 16;
                if (player.hasItem("Healing Light")) {
                    itemBonus += 2;
                }
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
            case "light_beam":
                attributeBonus = player.getAttribute("wisdom") / 2;
                break;
            case "nature_bond":
                attributeBonus = player.getAttribute("intuition") / 2;
                break;
            case "healing_touch":
                attributeBonus = player.getAttribute("charisma") / 2;
                break;
        }
        
        // Apply item bonuses for hit chance
        if (player.hasItem("Future Glimpse")) {
            itemBonus += 2;
        }
        if (player.hasItem("Ancient Knowledge")) {
            itemBonus += 1;
        }
        
        // Roll d20 + attribute bonus + item bonus vs target number
        return random.nextInt(20) + attributeBonus + itemBonus >= baseChance;
    }
    
    /**
     * Calculates damage for an attack based on the action and player attributes
     * @param actionId The action ID
     * @return The amount of damage
     */
    private int calculateDamage(String actionId) {
        int baseDamage = ATTACK_DAMAGE_VALUES.getOrDefault(actionId, 1);
        int attributeBonus = 0;
        int itemBonus = 0;
        
        // Apply attribute bonuses based on attack type
        switch (actionId) {
            case "quick_attack":
                attributeBonus = player.getAttribute("perception") / 3;
                if (player.hasItem("Endurance Shard")) {
                    itemBonus += 2;
                    combatLog.add("Your Endurance Shard enhances your quick attack!");
                }
                break;
            case "focused_strike":
                attributeBonus = player.getAttribute("wisdom") / 3;
                if (player.hasItem("Strength Elixir")) {
                    itemBonus += 3;
                    combatLog.add("Your Strength Elixir empowers your focused strike!");
                }
                break;
            case "mystic_blast":
                attributeBonus = player.getAttribute("intuition") / 2;
                if (player.hasItem("Mystical Crystal")) {
                    itemBonus += 2;
                    combatLog.add("Your Mystical Crystal amplifies the blast!");
                }
                if (player.hasItem("Arcane Dust")) {
                    itemBonus += 1;
                    combatLog.add("Arcane Dust enhances your mystical power!");
                }
                break;
            case "charismatic_approach":
                attributeBonus = player.getAttribute("charisma") / 2;
                if (player.hasItem("Charm of Persuasion")) {
                    itemBonus += 2;
                    combatLog.add("Your Charm of Persuasion enhances your approach!");
                }
                if (player.hasItem("Silver Tongue")) {
                    itemBonus += 1;
                    combatLog.add("Your Silver Tongue makes your words more compelling!");
                }
                break;
            case "knowledge_strike":
                attributeBonus = player.getAttribute("knowledge") / 2;
                if (player.hasItem("Scholar's Tome")) {
                    itemBonus += 2;
                    combatLog.add("Your Scholar's Tome reveals a critical weakness!");
                }
                if (player.hasItem("Knowledge Crystal")) {
                    itemBonus += 1;
                    combatLog.add("Your Knowledge Crystal helps you exploit weaknesses!");
                }
                break;
            case "perceptive_counter":
                attributeBonus = player.getAttribute("perception") / 2;
                if (player.hasItem("Shadow Sight")) {
                    itemBonus += 2;
                    combatLog.add("Your Shadow Sight helps you spot the perfect opening!");
                }
                if (player.hasItem("Future Glimpse")) {
                    itemBonus += 1;
                    combatLog.add("Your Future Glimpse guides your counter-attack!");
                }
                break;
            case "light_beam":
                attributeBonus = player.getAttribute("wisdom") / 2;
                if (player.hasItem("Radiant Crystal")) {
                    itemBonus += 2;
                    combatLog.add("Your Radiant Crystal amplifies the light!");
                }
                if (player.hasItem("Sun's Blessing")) {
                    itemBonus += 1;
                    combatLog.add("The Sun's Blessing strengthens your beam!");
                }
                break;
            case "nature_bond":
                attributeBonus = player.getAttribute("intuition") / 2;
                if (player.hasItem("Ancient Seed")) {
                    itemBonus += 2;
                    combatLog.add("The Ancient Seed connects you with nature's power!");
                }
                if (player.hasItem("Forest Heart")) {
                    itemBonus += 1;
                    combatLog.add("The Forest Heart harmonizes with your energy!");
                }
                break;
            case "healing_touch":
                attributeBonus = player.getAttribute("charisma") / 2;
                if (player.hasItem("Healing Light")) {
                    itemBonus += 2;
                    combatLog.add("The Healing Light restores your vitality!");
                }
                if (player.hasItem("Peace Crystal")) {
                    itemBonus += 1;
                    combatLog.add("The Peace Crystal brings balance to your energy!");
                }
                break;
        }
        
        // Apply general item bonuses
        if (player.hasItem("Destiny Shard") && Math.random() < 0.2) {
            itemBonus += 3;
            combatLog.add("Your Destiny Shard guides your attack to a critical point!");
        }
        
        // Small random factor
        int randomFactor = random.nextInt(3);
        
        return Math.max(1, baseDamage + attributeBonus + itemBonus + randomFactor);
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