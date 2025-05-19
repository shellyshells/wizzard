// CombatScreen.java
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Character;
import model.CombatSystem;
import model.Entity;

/**
 * Screen for handling combat between the player and entities
 */
public class CombatScreen extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color ACCENT_COLOR = new Color(140, 100, 180);
    private static final Color BUTTON_COLOR = new Color(60, 40, 80);
    private static final Color BUTTON_HOVER_COLOR = new Color(90, 60, 120);
    private static final Color HEALTH_BAR_COLOR = new Color(120, 50, 50);
    private static final Color PLAYER_HEALTH_COLOR = new Color(50, 120, 80);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 16);
    
    private Character player;
    private Entity entity;
    private CombatSystem combatSystem;
    private JTextArea combatLog;
    private JProgressBar playerHealthBar;
    private JProgressBar entityHealthBar;
    private JPanel actionButtonsPanel;
    private Map<String, JButton> actionButtons = new HashMap<>();
    
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean combatResult = false;
    
    /**
     * Creates a combat screen
     * @param parent Parent frame
     * @param player Player character
     * @param entity Entity to battle
     */
    public CombatScreen(JFrame parent, Character player, Entity entity) {
        super(parent, "Combat: " + entity.getName(), true);
        this.player = player;
        this.entity = entity;
        this.combatSystem = new CombatSystem(player, entity);
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        initComponents();
        updateUI();
    }
    
    /**
     * Initializes the UI components
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Combat with " + entity.getName(), JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Health bars panel
        JPanel healthBarsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        healthBarsPanel.setOpaque(false);
        
        // Player health
        JPanel playerPanel = new JPanel(new BorderLayout(5, 5));
        playerPanel.setOpaque(false);
        JLabel playerLabel = new JLabel(player.getName(), JLabel.CENTER);
        playerLabel.setForeground(TEXT_COLOR);
        playerLabel.setFont(TEXT_FONT);
        
        playerHealthBar = new JProgressBar(0, player.getAttribute("energy"));
        playerHealthBar.setValue(player.getAttribute("energy"));
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(PLAYER_HEALTH_COLOR);
        playerHealthBar.setString("Energy: " + player.getAttribute("energy"));
        
        playerPanel.add(playerLabel, BorderLayout.NORTH);
        playerPanel.add(playerHealthBar, BorderLayout.CENTER);
        
        // Entity health
        JPanel entityPanel = new JPanel(new BorderLayout(5, 5));
        entityPanel.setOpaque(false);
        JLabel entityLabel = new JLabel(entity.getName(), JLabel.CENTER);
        entityLabel.setForeground(TEXT_COLOR);
        entityLabel.setFont(TEXT_FONT);
        
        entityHealthBar = new JProgressBar(0, entity.getEnergyMax());
        entityHealthBar.setValue(entity.getEnergy());
        entityHealthBar.setStringPainted(true);
        entityHealthBar.setForeground(HEALTH_BAR_COLOR);
        entityHealthBar.setString("Energy: " + entity.getEnergy());
        
        entityPanel.add(entityLabel, BorderLayout.NORTH);
        entityPanel.add(entityHealthBar, BorderLayout.CENTER);
        
        healthBarsPanel.add(playerPanel);
        healthBarsPanel.add(entityPanel);
        
        titlePanel.add(healthBarsPanel, BorderLayout.SOUTH);
        
        // Combat log area
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setFont(TEXT_FONT);
        combatLog.setForeground(TEXT_COLOR);
        combatLog.setBackground(new Color(15, 10, 20));
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane logScrollPane = new JScrollPane(combatLog);
        logScrollPane.setBorder(new LineBorder(ACCENT_COLOR, 1));
        
        // Combat visualization panel (optional graphical representation)
        JPanel combatVisualizationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw a mystical combat arena background
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(30, 15, 45),
                    0, getHeight(), new Color(15, 10, 30)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw some mystical energy patterns
                g2d.setColor(new Color(ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue(), 50));
                for (int i = 0; i < 5; i++) {
                    int size = 40 + i * 30;
                    g2d.drawOval(getWidth()/2 - size/2, getHeight()/2 - size/2, size, size);
                }
                
                // Draw player avatar on the left
                drawPlayerAvatar(g2d, 150, getHeight()/2);
                
                // Draw entity on the right
                drawEntityAvatar(g2d, getWidth() - 150, getHeight()/2);
            }
            
            private void drawPlayerAvatar(Graphics2D g, int x, int y) {
                g.setColor(new Color(50, 100, 150, 200));
                g.fillOval(x - 30, y - 60, 60, 60); // Head
                g.fillRect(x - 25, y, 50, 80); // Body
                
                // Mystical aura
                g.setColor(new Color(100, 150, 255, 50));
                g.fillOval(x - 50, y - 80, 100, 160);
            }
            
            private void drawEntityAvatar(Graphics2D g, int x, int y) {
                g.setColor(new Color(150, 50, 50, 200));
                g.fillOval(x - 30, y - 60, 60, 60); // Head
                g.fillRect(x - 25, y, 50, 80); // Body
                
                // Malevolent aura
                g.setColor(new Color(200, 0, 0, 30));
                g.fillOval(x - 50, y - 80, 100, 160);
            }
        };
        combatVisualizationPanel.setPreferredSize(new Dimension(800, 200));
        
        // Action buttons panel
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        actionButtonsPanel.setOpaque(false);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(combatVisualizationPanel, BorderLayout.CENTER);
        mainPanel.add(logScrollPane, BorderLayout.EAST);
        mainPanel.add(actionButtonsPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    /**
     * Updates the UI with current combat state
     */
    private void updateUI() {
        // Update health bars
        playerHealthBar.setValue(player.getAttribute("energy"));
        entityHealthBar.setValue(entity.getEnergy());
        
        // Update health labels
        combatLog.setText("");
        for (String entry : combatSystem.getCombatLog()) {
            combatLog.append(entry + "\n");
        }
        
        // Update action buttons
        actionButtonsPanel.removeAll();
        actionButtons.clear();
        
        // Add available combat actions
        List<String> availableActions = combatSystem.getAvailableActions();
        for (String actionId : availableActions) {
            JButton actionButton = createActionButton(actionId);
            actionButtons.put(actionId, actionButton);
            actionButtonsPanel.add(actionButton);
        }
        
        // Add inventory button
        JButton inventoryButton = createStyledButton("Use Item");
        inventoryButton.addActionListener(e -> openInventory());
        actionButtonsPanel.add(inventoryButton);
        
        // Add retreat button
        JButton retreatButton = createStyledButton("Attempt Retreat");
        retreatButton.setEnabled(true);
        retreatButton.addActionListener(e -> attemptRetreat());
        actionButtonsPanel.add(retreatButton);
        
        // Check if combat is over
        if (combatSystem.isCombatOver()) {
            if (combatSystem.playerVictorious()) {
                combatLog.append("\nVictory! You have overcome " + entity.getName() + "!");
                // Grant rewards
                grantVictoryRewards();
            } else {
                combatLog.append("\nDefeat! You have been overcome by " + entity.getName() + ".");
            }
            
            // Disable action buttons but keep retreat enabled
            for (JButton button : actionButtons.values()) {
                button.setEnabled(false);
            }
            
            // Add a "Continue" button
            JButton continueButton = new JButton("Continue");
            continueButton.setEnabled(true);
            continueButton.addActionListener(e -> {
                combatResult = combatSystem.playerVictorious();
                latch.countDown();
                dispose();
            });
            actionButtonsPanel.add(continueButton);
        }
        
        actionButtonsPanel.revalidate();
        actionButtonsPanel.repaint();
    }
    
    /**
     * Creates a styled button for a combat action
     */
    private JButton createActionButton(String actionId) {
        String actionName = CombatSystem.getActionName(actionId);
        String tooltip = CombatSystem.getActionDescription(actionId);
        
        JButton button = createStyledButton(actionName);
        button.setToolTipText(tooltip);
        
        button.addActionListener(e -> {
            // Disable all buttons during action
            setButtonsEnabled(false);
            
            // Execute action
            boolean combatContinues = combatSystem.executePlayerAction(actionId);
            
            // Update UI
            updateUI();
            
            // Check if combat is over
            if (!combatContinues) {
                finishCombat();
            } else {
                setButtonsEnabled(true);
            }
        });
        
        return button;
    }
    
    /**
     * Creates a styled button with consistent look
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER_COLOR);
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });
        
        return button;
    }
    
    /**
     * Enables or disables all action buttons
     */
    private void setButtonsEnabled(boolean enabled) {
        for (JButton button : actionButtons.values()) {
            button.setEnabled(enabled);
        }
    }
    
    /**
     * Opens the inventory during combat
     */
    private void openInventory() {
        InventoryScreen inventoryScreen = new InventoryScreen(this, player, true);
        inventoryScreen.setVisible(true);
        
        // After closing inventory, update UI in case items were used
        updateUI();
    }
    
    /**
     * Attempts to retreat from combat
     */
    private void attemptRetreat() {
        combatLog.append("You attempt to retreat from combat...\n");
        
        // Chance to retreat based on player's intuition
        int intuition = player.getAttribute("intuition");
        double retreatChance = 0.3 + (intuition * 0.05); // 30% base + 5% per intuition point
        
        if (Math.random() < retreatChance) {
            combatLog.append("You successfully disengage from combat!\n");
            
            // End combat with retreat outcome
            Timer timer = new Timer(1500, e -> {
                combatResult = false; // Player did not win, but retreated
                latch.countDown();
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            combatLog.append("Your retreat fails! " + entity.getName() + " blocks your escape.\n");
            
            // Entity gets a free attack
            boolean combatContinues = combatSystem.executePlayerAction("defensive_stance");
            updateUI();
            
            if (!combatContinues) {
                finishCombat();
            }
        }
    }
    
    /**
     * Finishes combat and sets the result
     */
    private void finishCombat() {
        combatResult = combatSystem.playerVictorious();
        
        if (combatResult) {
            // Player victory rewards
            grantVictoryRewards();
        }
        
        // Show finish message and close dialog after a short delay
        Timer timer = new Timer(2000, e -> {
            latch.countDown();
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Grants rewards for combat victory
     */
    private void grantVictoryRewards() {
        combatLog.append("Victory! You've overcome " + entity.getName() + "!\n");
        
        // Determine reward type based on entity type
        String rewardType = determineRewardType();
        
        // Grant attribute increase
        String increasedAttr = determineAttributeIncrease(rewardType);
        player.modifyAttribute(increasedAttr, 1);
        combatLog.append("Your " + increasedAttr + " has increased by 1.\n");
        
        // Chance for an item drop based on entity type
        if (Math.random() < getItemDropChance()) {
            String item = determineItemDrop(rewardType);
            player.addItem(item);
            combatLog.append("You found: " + item + "!\n");
        }
        
        // Special rewards for certain entity types
        grantSpecialRewards(rewardType);
    }
    
    private String determineRewardType() {
        if (entity.getName().contains("Shadow") || entity.getName().contains("Wraith")) {
            return "shadow";
        } else if (entity.getName().contains("Mystic") || entity.getName().contains("Mage")) {
            return "mystical";
        } else if (entity.getName().contains("Prophet") || entity.getName().contains("Seer")) {
            return "prophetic";
        } else if (entity.getName().contains("Scholar") || entity.getName().contains("Sage")) {
            return "knowledge";
        } else {
            return "physical";
        }
    }
    
    private String determineAttributeIncrease(String rewardType) {
        switch (rewardType) {
            case "shadow":
                return "intuition";
            case "mystical":
                return "wisdom";
            case "prophetic":
                return "perception";
            case "knowledge":
                return "knowledge";
            default:
                String[] attributes = {"wisdom", "intuition", "perception", "charisma", "knowledge"};
                return attributes[(int)(Math.random() * attributes.length)];
        }
    }
    
    private double getItemDropChance() {
        // Base chance is 40%
        double chance = 0.4;
        
        // Increase chance based on player's perception
        chance += player.getAttribute("perception") * 0.02;
        
        // Cap at 80%
        return Math.min(0.8, chance);
    }
    
    private String determineItemDrop(String rewardType) {
        switch (rewardType) {
            case "shadow":
                String[] shadowItems = {
                    "Shadow Crystal", "Dark Essence", "Void Shard",
                    "Night's Whisper", "Eclipse Fragment"
                };
                return shadowItems[(int)(Math.random() * shadowItems.length)];
                
            case "mystical":
                String[] mysticalItems = {
                    "Mystical Crystal", "Arcane Dust", "Spell Fragment",
                    "Mana Essence", "Rune Shard"
                };
                return mysticalItems[(int)(Math.random() * mysticalItems.length)];
                
            case "prophetic":
                String[] propheticItems = {
                    "Oracle's Eye", "Vision Incense", "Future Fragment",
                    "Time Crystal", "Destiny Shard"
                };
                return propheticItems[(int)(Math.random() * propheticItems.length)];
                
            case "knowledge":
                String[] knowledgeItems = {
                    "Scholar's Tome", "Ancient Scroll", "Wisdom Fragment",
                    "Knowledge Crystal", "Memory Shard"
                };
                return knowledgeItems[(int)(Math.random() * knowledgeItems.length)];
                
            default:
                String[] physicalItems = {
                    "Protection Charm", "Healer's Potion", "Strength Elixir",
                    "Vitality Crystal", "Endurance Shard"
                };
                return physicalItems[(int)(Math.random() * physicalItems.length)];
        }
    }
    
    private void grantSpecialRewards(String rewardType) {
        // Special rewards based on entity type and player's attributes
        switch (rewardType) {
            case "shadow":
                if (player.getAttribute("intuition") >= 10) {
                    player.addItem("Shadow Sight");
                    combatLog.append("Your high intuition allows you to see through the shadows!\n");
                    combatLog.append("You gained: Shadow Sight\n");
                }
                break;
                
            case "mystical":
                if (player.getAttribute("wisdom") >= 10) {
                    player.addItem("Arcane Insight");
                    combatLog.append("Your wisdom reveals the secrets of the arcane!\n");
                    combatLog.append("You gained: Arcane Insight\n");
                }
                break;
                
            case "prophetic":
                if (player.getAttribute("perception") >= 10) {
                    player.addItem("Future Glimpse");
                    combatLog.append("Your perception allows you to glimpse the future!\n");
                    combatLog.append("You gained: Future Glimpse\n");
                }
                break;
                
            case "knowledge":
                if (player.getAttribute("knowledge") >= 10) {
                    player.addItem("Ancient Knowledge");
                    combatLog.append("Your knowledge reveals ancient secrets!\n");
                    combatLog.append("You gained: Ancient Knowledge\n");
                }
                break;
        }
        
        // Random chance for a special ability based on charisma
        if (player.getAttribute("charisma") >= 8 && Math.random() < 0.3) {
            String[] specialAbilities = {
                "Charm of Persuasion", "Voice of Authority", "Silver Tongue",
                "Diplomatic Grace", "Inspiring Presence"
            };
            String ability = specialAbilities[(int)(Math.random() * specialAbilities.length)];
            player.addItem(ability);
            combatLog.append("Your charisma manifests a new ability!\n");
            combatLog.append("You gained: " + ability + "\n");
        }
    }
    
    /**
     * Begins combat and waits for its conclusion
     * @return true if player is victorious, false otherwise
     */
    public boolean startCombat() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return combatResult;
    }
}