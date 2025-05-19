// EncounterScreen.java
package view;

import model.Character;
import model.Entity;
import model.Inventory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Screen for handling character encounters with entities
 */
public class EncounterScreen extends JDialog implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color ACCENT_COLOR = new Color(140, 100, 180);
    private static final Color BUTTON_COLOR = new Color(60, 40, 80);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 16);

    private Character character;
    private Entity entity;
    private JTextArea encounterLog;
    private JProgressBar visionBar;
    private JProgressBar entityBar;
    private JButton persuadeButton;
    private JButton insightButton;
    private JButton knowledgeButton;
    private JButton inventoryButton;
    private JButton fleeButton;

    private boolean encounterCompleted = false;
    private boolean success = false;
    private final CountDownLatch latch = new CountDownLatch(1);
    private Random random = new Random();

    /**
     * Creates an encounter screen
     * @param parent Parent frame
     * @param character Player character
     * @param entity Entity to encounter
     */
    public EncounterScreen(JFrame parent, Character character, Entity entity) {
        super(parent, "Encounter - " + entity.getName(), true);
        this.character = character;
        this.entity = entity;

        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initComponents();
    }

    /**
     * Initializes the UI components
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top panel with status bars
        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statusPanel.setOpaque(false);

        // Character panel
        JPanel characterPanel = new JPanel(new BorderLayout(5, 5));
        characterPanel.setOpaque(false);

        JLabel characterLabel = new JLabel(character.getName(), JLabel.LEFT);
        characterLabel.setFont(TITLE_FONT);
        characterLabel.setForeground(TEXT_COLOR);

        visionBar = new JProgressBar(0, 100);
        visionBar.setValue(character.getAttribute("energy"));
        visionBar.setStringPainted(true);
        visionBar.setString("Energy: " + character.getAttribute("energy"));
        visionBar.setForeground(new Color(100, 150, 200));

        characterPanel.add(characterLabel, BorderLayout.NORTH);
        characterPanel.add(visionBar, BorderLayout.CENTER);

        // Entity panel
        JPanel entityPanel = new JPanel(new BorderLayout(5, 5));
        entityPanel.setOpaque(false);

        JLabel entityLabel = new JLabel(entity.getName(), JLabel.RIGHT);
        entityLabel.setFont(TITLE_FONT);
        entityLabel.setForeground(TEXT_COLOR);

        entityBar = new JProgressBar(0, entity.getEnergyMax());
        entityBar.setValue(entity.getEnergy());
        entityBar.setStringPainted(true);
        entityBar.setString("Energy: " + entity.getEnergy());
        entityBar.setForeground(new Color(180, 100, 100));

        entityPanel.add(entityLabel, BorderLayout.NORTH);
        entityPanel.add(entityBar, BorderLayout.CENTER);

        statusPanel.add(characterPanel);
        statusPanel.add(entityPanel);

        mainPanel.add(statusPanel, BorderLayout.NORTH);

        // Center encounter log
        encounterLog = new JTextArea();
        encounterLog.setEditable(false);
        encounterLog.setFont(TEXT_FONT);
        encounterLog.setForeground(TEXT_COLOR);
        encounterLog.setBackground(new Color(30, 20, 40));
        encounterLog.setLineWrap(true);
        encounterLog.setWrapStyleWord(true);
        encounterLog.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        
        // Initial encounter text
        encounterLog.setText("You are facing " + entity.getName() + ".\n\n" + 
                           entity.getDescription() + "\n\nHow will you approach this encounter?");

        JScrollPane scrollPane = new JScrollPane(encounterLog);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 0));

        persuadeButton = createButton("Persuade");
        insightButton = createButton("Insight");
        knowledgeButton = createButton("Knowledge");
        inventoryButton = createButton("Inventory");
        fleeButton = createButton("Withdraw");

        buttonPanel.add(persuadeButton);
        buttonPanel.add(insightButton);
        buttonPanel.add(knowledgeButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(fleeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Creates a styled button
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 60, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Starts the encounter and waits for its completion
     * @return true if the player was successful
     */
    public boolean startEncounter() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return success;
    }

    /**
     * Handles button actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Disable buttons during action processing
        setButtonsEnabled(false);
        
        if (source == persuadeButton) {
            processAction("persuade");
        } else if (source == insightButton) {
            processAction("insight");
        } else if (source == knowledgeButton) {
            processAction("knowledge");
        } else if (source == inventoryButton) {
            openInventory();
            setButtonsEnabled(true);
        } else if (source == fleeButton) {
            attemptToFlee();
        }
    }
    
    /**
     * Processes an action against the entity
     */
    private void processAction(String actionType) {
        appendToLog("\nYou attempt to use your " + actionType + "...");
        
        // Calculate success chance
        int playerScore = 0;
        String attributeUsed = "";
        
        if ("persuade".equals(actionType)) {
            playerScore = rollDice() + character.getAttribute("charisma");
            attributeUsed = "charisma";
        } else if ("insight".equals(actionType)) {
            playerScore = rollDice() + character.getAttribute("intuition");
            attributeUsed = "intuition";
        } else if ("knowledge".equals(actionType)) {
            playerScore = rollDice() + character.getAttribute("knowledge");
            attributeUsed = "knowledge";
        }
        
        int entityScore = rollDice() + entity.getInfluence();
        
        appendToLog("You roll " + playerScore + " (using your " + attributeUsed + ")");
        appendToLog(entity.getName() + " rolls " + entityScore);
        
        // Process result
        if (playerScore > entityScore) {
            // Success
            appendToLog("\nYou succeed! Your " + actionType + " works effectively against " + entity.getName() + ".");
            
            // Deal damage to entity
            int damage = 2;
            
            // Bonus damage based on character attributes or items
            if ("persuade".equals(actionType) && character.getAttribute("charisma") > 8) {
                damage += 1;
            } else if ("insight".equals(actionType) && character.hasItem("Oracle's Eye")) {
                damage += 2;
                appendToLog("Your Oracle's Eye grants you deeper insight (+2 damage)!");
            } else if ("knowledge".equals(actionType) && character.hasItem("Ancient Scroll")) {
                damage += 1;
                appendToLog("Knowledge from your Ancient Scroll proves useful (+1 damage)!");
            }
            
            entity.reduceEnergy(damage);
            entityBar.setValue(entity.getEnergy());
            entityBar.setString("Energy: " + entity.getEnergy());
            
            // Small chance to gain insight
            if (random.nextDouble() < 0.2) {
                character.modifyAttribute(attributeUsed, 1);
                appendToLog("You gain valuable experience (+1 " + attributeUsed + ")");
            }
        } else {
            // Failure
            appendToLog("\nYour attempt fails. " + entity.getName() + " resists your " + actionType + ".");
            
            // Character loses energy
            int damage = 1;
            character.modifyAttribute("energy", -damage);
            visionBar.setValue(character.getAttribute("energy"));
            visionBar.setString("Energy: " + character.getAttribute("energy"));
        }
        
        // Check if encounter is over
        checkEncounterStatus();
    }
    
    /**
     * Opens the inventory screen
     */
    private void openInventory() {
        InventoryScreen inventoryScreen = new InventoryScreen(this, character, true);
        
        // If player used an item that affects the encounter
        if (inventoryScreen.hasUsedItem()) {
            // Update energy display
            visionBar.setValue(character.getAttribute("energy"));
            visionBar.setString("Energy: " + character.getAttribute("energy"));
            
            // Apply any special effects from items
            if (inventoryScreen.getLastUsedItem().equals("Oracle's Eye")) {
                appendToLog("\nThe Oracle's Eye reveals hidden weaknesses in " + entity.getName() + "!");
                entity.reduceEnergy(3);
                entityBar.setValue(entity.getEnergy());
                entityBar.setString("Energy: " + entity.getEnergy());
            } else if (inventoryScreen.getLastUsedItem().equals("Vision Incense")) {
                appendToLog("\nThe Vision Incense clears your mind, enhancing your intuition!");
                character.modifyAttribute("intuition", 1);
            }
            
            // Check if encounter is over after item use
            checkEncounterStatus();
        }
    }
    
    /**
     * Attempts to flee from the encounter
     */
    private void attemptToFlee() {
        appendToLog("\nYou attempt to withdraw from the encounter...");
        
        // 50% base chance, modified by intuition
        double fleeChance = 0.5 + (character.getAttribute("intuition") * 0.03);
        
        if (random.nextDouble() < fleeChance) {
            appendToLog("You successfully disengage from " + entity.getName() + ".");
            success = false;
            encounterCompleted = true;
            
            // End encounter after a short delay
            Timer timer = new Timer(1500, evt -> {
                latch.countDown();
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            appendToLog("Your attempt to withdraw fails. " + entity.getName() + " blocks your retreat.");
            
            // Character loses energy from the failed attempt
            character.modifyAttribute("energy", -1);
            visionBar.setValue(character.getAttribute("energy"));
            visionBar.setString("Energy: " + character.getAttribute("energy"));
            
            // Re-enable buttons after failure
            setButtonsEnabled(true);
        }
    }
    
    /**
     * Checks if the encounter is over
     */
    private void checkEncounterStatus() {
        if (entity.isOvercome()) {
            // Entity is overcome
            appendToLog("\nYou have successfully overcome " + entity.getName() + "!");
            success = true;
            encounterCompleted = true;
            
            // Grant a reward
            grantReward();
            
            // End encounter after a delay
            Timer timer = new Timer(2000, evt -> {
                latch.countDown();
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
            
        } else if (character.getAttribute("energy") <= 0) {
            // Character has no energy left
            appendToLog("\nYou are exhausted and unable to continue. " + entity.getName() + " has bested you.");
            success = false;
            encounterCompleted = true;
            
            // End encounter after a delay
            Timer timer = new Timer(2000, evt -> {
                latch.countDown();
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
            
        } else {
            // Encounter continues
            setButtonsEnabled(true);
        }
    }
    
    /**
     * Grants a reward for overcoming the entity
     */
    private void grantReward() {
        // 50% chance to get an item
        if (random.nextDouble() < 0.5) {
            String[] possibleRewards = {
                "Sacred Water", "Vision Incense", "Protection Charm", 
                "Ancient Runes", "Mystical Crystal"
            };
            
            int index = random.nextInt(possibleRewards.length);
            String reward = possibleRewards[index];
            
            character.addItem(reward);
            appendToLog("You found: " + reward);
        }
        
        // Always gain some attribute points
        int attribute = random.nextInt(3);
        String attributeName;
        
        switch (attribute) {
            case 0:
                attributeName = "wisdom";
                break;
            case 1:
                attributeName = "intuition";
                break;
            case 2:
                attributeName = "knowledge";
                break;
            default:
                attributeName = "wisdom";
        }
        
        character.modifyAttribute(attributeName, 1);
        appendToLog("You gained insight: +1 " + attributeName);
    }
    
    /**
     * Rolls 2d6 (two six-sided dice)
     * @return Result (2-12)
     */
    private int rollDice() {
        return random.nextInt(6) + 1 + random.nextInt(6) + 1;
    }
    
    /**
     * Enables or disables action buttons
     */
    private void setButtonsEnabled(boolean enabled) {
        persuadeButton.setEnabled(enabled);
        insightButton.setEnabled(enabled);
        knowledgeButton.setEnabled(enabled);
        inventoryButton.setEnabled(enabled);
        fleeButton.setEnabled(enabled);
    }
    
    /**
     * Adds text to the encounter log
     */
    private void appendToLog(String text) {
        encounterLog.append("\n" + text);
        encounterLog.setCaretPosition(encounterLog.getDocument().getLength());
    }
}