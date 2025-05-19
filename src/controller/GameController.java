// GameController.java - Updated with combat system integration
package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import model.Character;
import model.Choice;
import model.Entity;
import model.StoryNode;

/**
 * Main controller class that manages the game state and progression
 */
public class GameController {
    private model.Story story;
    private Character character;
    private StoryNode currentNode;
    private Map<Integer, Boolean> visitedNodes;
    private Map<Integer, Entity> defeatedEntities;
    private int visionCount = 0; // Tracks number of visions experienced
    private int combatEncounterCount = 0; // Tracks number of combat encounters

    /**
     * Constructor with character type
     * 
     * @param story The story/narrative
     * @param playerName The player's character name
     * @param characterType The type of character (Prophet, Seer, Oracle, Scholar)
     */
    public GameController(model.Story story, String playerName, String characterType) {
        this.story = story;
        this.visitedNodes = new HashMap<>();
        this.defeatedEntities = new HashMap<>();
        
        character = new Character(playerName);

        // Adjust attributes based on character type
        switch (characterType) {
            case "Prophet":
                character.modifyAttribute("wisdom", 2);
                character.modifyAttribute("charisma", 2);
                character.addItem("Prophet's Staff");
                break;
            case "Seer":
                character.modifyAttribute("intuition", 3);
                character.modifyAttribute("perception", 1);
                character.addItem("Oracle's Eye");
                character.addItem("Vision Incense");
                break;
            case "Oracle":
                character.modifyAttribute("wisdom", 2);
                character.modifyAttribute("intuition", 1);
                character.modifyAttribute("charisma", 1);
                character.addItem("Ancient Runes");
                break;
            case "Scholar":
                character.modifyAttribute("knowledge", 3);
                character.modifyAttribute("wisdom", 1);
                character.addItem("Ancient Scroll");
                character.addItem("Mystical Crystal");
                break;
            default:
                // Default attributes
                character.modifyAttribute("wisdom", 2);
                character.modifyAttribute("energy", 2);
                character.addItem("Protection Charm");
                break;
        }

        startGame();
    }

    /**
     * Simple constructor
     * 
     * @param story The story/narrative
     * @param playerName The player's character name
     */
    public GameController(model.Story story, String playerName) {
        this.story = story;
        this.character = new Character(playerName);
        this.visitedNodes = new HashMap<>();
        this.defeatedEntities = new HashMap<>();
        startGame();
    }

    /**
     * Starts or restarts the game
     */
    public void startGame() {
        // Initialize or reset visited nodes
        if (visitedNodes == null) {
            visitedNodes = new HashMap<>();
        } else {
            visitedNodes.clear();
        }

        // Reset to the first node of the story
        currentNode = story.getStartingNode();
        
        // Reset combat counter
        combatEncounterCount = 0;
    }

    /**
     * Processes the player's choice and moves to the next node
     * 
     * @param choiceIndex The index of the chosen option
     * @return The next node, or null if the choice is invalid
     */
    public StoryNode makeChoice(int choiceIndex) {
        if (choiceIndex < 0 || choiceIndex >= currentNode.getAvailableChoices().size()) {
            return null;
        }

        Choice selectedChoice = currentNode.getAvailableChoices().get(choiceIndex);
        
        // Check if this choice requires an item
        if (selectedChoice.hasItemRequirement() && 
            !character.hasItem(selectedChoice.getRequiredItem())) {
            return null; // Cannot make this choice without the required item
        }
        
        // Check if the choice triggers combat
        boolean triggersEncounter = selectedChoice.triggersEncounter();
        
        int nextNodeId = selectedChoice.getDestinationNodeId();
        currentNode = story.getNodeById(nextNodeId);

        markNodeVisited(currentNode.getId());
        
        // Handle any random or triggered combat
        if (shouldTriggerRandomCombat() || (triggersEncounter && currentNode.hasEntity())) {
            currentNode.setCombatTriggered(true);
        }
        
        // Check for prophetic vision probability (10% chance)
        if (Math.random() < 0.1 && visionCount < 3) {
            // Trigger a vision if conditions are met
            triggerPropheticVision();
        }
        
        return currentNode;
    }

    /**
     * Marks a node as visited
     */
    private void markNodeVisited(int nodeId) {
        visitedNodes.put(nodeId, true);
    }

    /**
     * Checks if a node has been visited
     */
    public boolean hasVisitedNode(int nodeId) {
        return visitedNodes.getOrDefault(nodeId, false);
    }
    
    /**
     * Determines if a random combat encounter should trigger
     * @return true if random combat should occur
     */
    private boolean shouldTriggerRandomCombat() {
        // Determine if we should trigger a random combat encounter
        // Only trigger if we haven't had too many combats already
        if (combatEncounterCount >= 5) {
            return false;
        }
        
        // Random chance based on area danger (implemented via story node ID ranges)
        // Higher IDs generally indicate deeper into the story - more danger
        int dangerLevel = Math.min(5, currentNode.getId() / 3);
        double randomCombatChance = 0.15 * dangerLevel;
        
        // Additional chance based on player's attributes
        double attributeBonus = 0;
        attributeBonus += character.getAttribute("intuition") * 0.01;
        attributeBonus += character.getAttribute("perception") * 0.01;
        
        boolean shouldTrigger = Math.random() < (randomCombatChance + attributeBonus);
        
        if (shouldTrigger) {
            combatEncounterCount++;
        }
        
        return shouldTrigger;
    }
    
    /**
     * Triggers a random prophetic vision
     */
    private void triggerPropheticVision() {
        visionCount++;
        // Logic for generating random visions would go here
        // This would be integrated with the UI through an event system
    }
    
    /**
     * Creates a random combat encounter based on the current story context
     * @return The entity to fight
     */
    private Entity createRandomCombatEncounter() {
        String[] enemyTypes = {
            "Shadow Wraith", "Mystic Guardian", "Ancient Prophet", "Corrupted Scholar",
            "Void Walker", "Arcane Construct", "Time Weaver", "Knowledge Keeper"
        };
        
        String[] enemyDescriptions = {
            "A shadowy figure that seems to absorb light around it.",
            "A mystical being wreathed in arcane energy.",
            "An ancient seer who has seen too many futures.",
            "A scholar whose pursuit of knowledge has corrupted them.",
            "A being that exists between the planes of reality.",
            "A magical construct created to guard ancient secrets.",
            "A being that can manipulate the flow of time.",
            "A guardian of forbidden knowledge."
        };
        
        int index = (int)(Math.random() * enemyTypes.length);
        String name = enemyTypes[index];
        String description = enemyDescriptions[index];
        
        // Scale enemy power based on story progress
        int baseInfluence = 5 + (currentNode.getId() / 2);
        int baseEnergy = 10 + (currentNode.getId() / 2);
        
        // Add some randomness
        int influence = baseInfluence + (int)(Math.random() * 5);
        int energy = baseEnergy + (int)(Math.random() * 10);
        
        // Determine combat type based on enemy name
        String combatType = "physical";
        if (name.contains("Shadow") || name.contains("Void")) {
            combatType = "shadow";
        } else if (name.contains("Mystic") || name.contains("Arcane")) {
            combatType = "mystical";
        } else if (name.contains("Prophet") || name.contains("Time")) {
            combatType = "prophet";
        } else if (name.contains("Scholar") || name.contains("Knowledge")) {
            combatType = "knowledge";
        }
        
        return new Entity(name, influence, energy, description, combatType);
    }
    
    /**
     * Handles an encounter between the player and an entity
     * @param action The action taken by the player
     * @return true if the player succeeds in the encounter
     */
    public boolean handleEncounter(String action) {
        if (!currentNode.hasEntity()) {
            return false;
        }
        
        Entity entity = currentNode.getEntity();
        
        // If it's a combat entity, handle combat
        if (entity.isCombatEntity()) {
            return handleCombat();
        }
        
        // Otherwise, handle as a social encounter
        int playerPower = calculatePlayerPower(action);
        int entityPower = entity.calculatePersuasionPower();
        
        boolean success = playerPower > entityPower;
        
        if (success) {
            // Grant rewards for successful social encounter
            grantSocialRewards(entity);
        }
        
        return success;
    }
    
    private void grantSocialRewards(Entity entity) {
        // Random chance for attribute increase
        if (Math.random() < 0.3) {
            String[] attributes = {"charisma", "wisdom", "intuition"};
            String attr = attributes[(int)(Math.random() * attributes.length)];
            character.modifyAttribute(attr, 1);
        }
        
        // Chance for an item based on entity type and player's items
        if (Math.random() < 0.4) {
            String item = determineSocialRewardItem(entity);
            if (item != null) {
                character.addItem(item);
            }
        }
        
        // Special rewards based on player's items
        if (character.hasItem("Ancient Knowledge")) {
            // Gain insight into future encounters
            if (Math.random() < 0.3) {
                character.modifyAttribute("intuition", 1);
            }
        }
        
        if (character.hasItem("Future Glimpse")) {
            // Learn from the encounter
            if (Math.random() < 0.3) {
                character.modifyAttribute("wisdom", 1);
            }
        }
        
        if (character.hasItem("Scholar's Tome")) {
            // Gain knowledge from the interaction
            if (Math.random() < 0.3) {
                character.modifyAttribute("knowledge", 1);
            }
        }
    }
    
    private String determineSocialRewardItem(Entity entity) {
        // Base items based on entity type
        if (entity.getName().contains("Prophet") || entity.getName().contains("Seer")) {
            String[] items = {"Vision Incense", "Future Fragment", "Time Crystal"};
            return items[(int)(Math.random() * items.length)];
        } else if (entity.getName().contains("Scholar") || entity.getName().contains("Sage")) {
            String[] items = {"Ancient Scroll", "Scholar's Tome", "Knowledge Crystal"};
            return items[(int)(Math.random() * items.length)];
        } else if (entity.getName().contains("Mystic")) {
            String[] items = {"Mystical Crystal", "Arcane Dust", "Spell Fragment"};
            return items[(int)(Math.random() * items.length)];
        } else if (entity.getName().contains("Shadow")) {
            String[] items = {"Shadow Crystal", "Dark Essence", "Night's Whisper"};
            return items[(int)(Math.random() * items.length)];
        } else {
            String[] items = {"Protection Charm", "Sacred Water", "Healer's Potion"};
            return items[(int)(Math.random() * items.length)];
        }
    }
    
    private int calculatePlayerPower(String action) {
        int basePower = character.getAttribute("charisma");
        
        // Add bonuses based on other attributes
        basePower += character.getAttribute("wisdom") / 2;
        basePower += character.getAttribute("intuition") / 2;
        
        // Add item bonuses
        if (character.hasItem("Charm of Persuasion")) {
            basePower += 3;
        }
        if (character.hasItem("Silver Tongue")) {
            basePower += 2;
        }
        if (character.hasItem("Diplomatic Grace")) {
            basePower += 2;
        }
        if (character.hasItem("Voice of Authority")) {
            basePower += 2;
        }
        if (character.hasItem("Inspiring Presence")) {
            basePower += 2;
        }
        if (character.hasItem("Ancient Knowledge")) {
            basePower += 1;
        }
        if (character.hasItem("Future Glimpse")) {
            basePower += 1;
        }
        
        // Add random factor
        basePower += (int)(Math.random() * 6) + 1;
        
        return basePower;
    }
    
    /**
     * Handles a combat encounter with the entity in the current node
     * @return true if the player is victorious, false if defeated or retreated
     */
    public boolean handleCombat() {
        if (!currentNode.hasEntity()) {
            return false;
        }
        
        // Combat is handled by the UI layer
        // This method exists as an interface for the UI to call
        // We just mark the entity as a combat entity
        currentNode.getEntity().setCombatEntity(true);
        
        return true; // Combat initiation successful
    }
    
    /**
     * Record the result of combat
     * @param entity The entity that was fought
     * @param victorious Whether the player was victorious
     */
    public void recordCombatResult(Entity entity, boolean victorious) {
        if (victorious) {
            defeatedEntities.put(entity.hashCode(), entity);
            
            // Increment combat stat if tracking
            // character.incrementStat("combats_won");
            
            // Special rewards are handled by the CombatSystem
        }
    }
    
    /**
     * Checks if there is a combat encounter triggered on the current node
     * @return true if combat is triggered
     */
    public boolean hasCombatTriggered() {
        return currentNode.isCombatTriggered() && currentNode.hasEntity() && 
               currentNode.getEntity().isCombatEntity();
    }
    
    /**
     * Gives a random reward to the player
     */
    private void giveRandomReward() {
        String[] possibleItems = {
            "Sacred Water", "Vision Incense", "Protection Charm", "Mystical Crystal"
        };
        
        int randomIndex = (int)(Math.random() * possibleItems.length);
        character.addItem(possibleItems[randomIndex]);
    }

    /**
     * Saves the current game state to a file
     * 
     * @param filePath The path to save the game
     * @return true if save was successful
     */
    public boolean saveGame(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(character.getName() + "\n");
            writer.write(currentNode.getId() + "\n");
            // Save attributes and inventory
            for (Map.Entry<String, Integer> attr : character.getAttributes().entrySet()) {
                writer.write("attribute:" + attr.getKey() + ":" + attr.getValue() + "\n");
            }
            for (String item : character.getInventory()) {
                writer.write("item:" + item + "\n");
            }
            // Save combat counter
            writer.write("combats:" + combatEncounterCount + "\n");
            return true;
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a game from a save file
     * 
     * @param filePath The path to the save file
     * @return true if load was successful
     */
    public boolean loadGame(String filePath) {
        try {
            String[] lines = Files.readString(Paths.get(filePath)).split("\n");

            if (lines.length >= 2) {
                String characterName = lines[0];
                int nodeId = Integer.parseInt(lines[1]);

                // Create a new character
                this.character = new Character(characterName);
                
                // Reset combat counter
                this.combatEncounterCount = 0;
                
                // Load attributes and inventory
                for (int i = 2; i < lines.length; i++) {
                    String line = lines[i];
                    if (line.startsWith("attribute:")) {
                        String[] parts = line.split(":");
                        character.modifyAttribute(parts[1], Integer.parseInt(parts[2]));
                    } else if (line.startsWith("item:")) {
                        character.addItem(line.substring(5));
                    } else if (line.startsWith("combats:")) {
                        combatEncounterCount = Integer.parseInt(line.substring(8));
                    }
                }

                // Set current node
                this.currentNode = story.getNodeById(nodeId);
                return true;
            }
            return false;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Changes the current story
     * @param newStory The new story to load
     */
    public void changeStory(model.Story newStory) {
        if (newStory == null) {
            System.err.println("ERROR: Attempted to change to a null story");
            return;
        }
        
        try {
            // Save current character
            Character currentCharacter = this.character;
            
            // Change story
            this.story = newStory;
            
            // Keep the same character with its stats
            this.character = currentCharacter;
            
            // Reset visited nodes for this new story
            if (visitedNodes == null) {
                visitedNodes = new HashMap<>();
            } else {
                visitedNodes.clear();
            }
            
            // Start at the first node of the new story
            this.currentNode = story.getStartingNode();
            
        } catch (Exception e) {
            System.err.println("ERROR when changing story: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters
    public model.Story getStory() {
        return story;
    }

    public Character getCharacter() {
        return character;
    }

    public StoryNode getCurrentNode() {
        return currentNode;
    }
}