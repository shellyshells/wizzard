// ProphecyUI.java - Updated with combat integration
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.GameController;
import model.Character;
import model.Choice;
import model.Entity;
import model.StoryNode;

/**
 * Main interface for the visual novel
 */
public class ProphecyUI extends JFrame {
    private GameController gameController;

    // Colors and fonts for the mystical theme
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color ACCENT_COLOR = new Color(140, 100, 180);
    private static final Color BUTTON_COLOR = new Color(60, 40, 80);
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 70, 130);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 28);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 16);
    private static final String BACKGROUND_IMAGE = "/Assets/Images/background-mystical.jpg";

    private JPanel mainPanel;
    private JTextArea storyTextArea;
    private JPanel choicesPanel;
    private JPanel statsPanel;
    private Image backgroundImage;

    /**
     * Constructor for the ProphecyUI
     * 
     * @param gameController The game controller
     */
    public ProphecyUI(GameController gameController) {
        super("The Ancient Prophecy");
        this.gameController = gameController;

        // Configure main window
        setTitle("The Ancient Prophecy - " + gameController.getCharacter().getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Add key listener for Escape key to exit fullscreen
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (JOptionPane.showConfirmDialog(
                            ProphecyUI.this,
                            "Do you want to exit the game?",
                            "Exit",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // For keyboard listener to work
        setFocusable(true);

        // Try to load background image
        try {
            backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();
        } catch (Exception e) {
            System.out.println("Failed to load background image. Using default color.");
            backgroundImage = null;
        }

        // Initialize components
        initComponents();

        // Display the current node
        displayNode(gameController.getCurrentNode());
    }

    /**
     * Initializes UI components
     */
    private void initComponents() {
        // Main panel with background
        mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180)); // Semi-transparent overlay
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Top panel with title and player info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(gameController.getStory().getTitle());
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Character stats panel
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)));
        statsPanel.setOpaque(false);
        updateStatsPanel();
        headerPanel.add(statsPanel, BorderLayout.EAST);

        // Chapter title panel
        JPanel chapterTitlePanel = new JPanel();
        chapterTitlePanel.setOpaque(false);
        chapterTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel chapterLabel = new JLabel("PROPHECY");
        chapterLabel.setFont(BUTTON_FONT);
        chapterLabel.setForeground(ACCENT_COLOR);
        chapterTitlePanel.add(chapterLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(chapterTitlePanel, BorderLayout.WEST);

        // Text area for story content
        storyTextArea = new JTextArea();
        storyTextArea.setEditable(false);
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true);
        storyTextArea.setFont(TEXT_FONT);
        storyTextArea.setForeground(TEXT_COLOR);
        storyTextArea.setBackground(new Color(0, 0, 0, 100));
        storyTextArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        storyTextArea.setMargin(new Insets(10, 10, 10, 10));

        // Wrap in a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(storyTextArea);
        scrollPane.setBorder(new LineBorder(ACCENT_COLOR, 1));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Choices panel
        choicesPanel = new JPanel();
        choicesPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        choicesPanel.setOpaque(false);
        mainPanel.add(choicesPanel, BorderLayout.SOUTH);

        // Menu panel
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.EAST);

        // Set content pane
        setContentPane(mainPanel);
    }

    /**
     * Creates the menu panel with buttons
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JButton inventoryButton = createStyledButton("📦 Inventory");
        JButton saveButton = createStyledButton("Save Journey");
        JButton loadButton = createStyledButton("Load Journey");
        JButton menuButton = createStyledButton("Main Menu");
        JButton exitButton = createStyledButton("Exit");
        JButton helpButton = createStyledButton("❔ Help");

        inventoryButton.addActionListener(e -> {
            InventoryScreen inventoryScreen = new InventoryScreen(this, gameController.getCharacter());
            inventoryScreen.setVisible(true);
            updateStatsPanel(); // Update display after inventory changes
        });

        saveButton.addActionListener(e -> saveGame());
        loadButton.addActionListener(e -> loadGame());
        menuButton.addActionListener(e -> returnToMenu());
        exitButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Do you really want to exit?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        helpButton.addActionListener(e -> {
            HelpWindow helpWindow = new HelpWindow(this);
            helpWindow.setVisible(true);
        });

        menuPanel.add(inventoryButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(saveButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(loadButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(menuButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(helpButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        return menuPanel;
    }

    /**
     * Creates a styled button
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setPreferredSize(new Dimension(150, 40));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Creates a choice button with the appropriate style
     */
    private JButton createChoiceButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(700, 40));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    /**
     * Updates the stats panel display
     */
    private void updateStatsPanel() {
        statsPanel.removeAll();

        Character character = gameController.getCharacter();
        JLabel nameLabel = new JLabel(character.getName());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_COLOR);
        statsPanel.add(nameLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(ACCENT_COLOR);
        statsPanel.add(separator);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Character attributes
        java.util.Map<String, Integer> attributes = character.getAttributes();
        for (java.util.Map.Entry<String, Integer> attribute : attributes.entrySet()) {
            // Skip display of temporary or system attributes
            if (attribute.getKey().equals("defense_bonus")) {
                continue;
            }
            
            JPanel attrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            attrPanel.setOpaque(false);

            JLabel attrNameLabel = new JLabel(capitalizeFirstLetter(attribute.getKey()) + ":");
            attrNameLabel.setForeground(ACCENT_COLOR);
            attrNameLabel.setFont(new Font("Serif", Font.PLAIN, 14));

            JLabel attrValueLabel = new JLabel(attribute.getValue().toString());
            attrValueLabel.setForeground(TEXT_COLOR);
            attrValueLabel.setFont(new Font("Serif", Font.BOLD, 14));

            attrPanel.add(attrNameLabel);
            attrPanel.add(attrValueLabel);

            statsPanel.add(attrPanel);
        }

        // Inventory summary
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel inventoryLabel = new JLabel("Items:");
        inventoryLabel.setForeground(ACCENT_COLOR);
        inventoryLabel.setFont(new Font("Serif", Font.BOLD, 14));
        statsPanel.add(inventoryLabel);

        List<String> inventory = character.getInventory();
        if (inventory.isEmpty()) {
            JLabel emptyLabel = new JLabel("None");
            emptyLabel.setForeground(TEXT_COLOR);
            emptyLabel.setFont(new Font("Serif", Font.ITALIC, 14));
            statsPanel.add(emptyLabel);
        } else {
            // Limit to displaying just the first few items
            int displayLimit = Math.min(5, inventory.size());
            for (int i = 0; i < displayLimit; i++) {
                String item = inventory.get(i);
                String icon = Character.getItemIcon(item);
                JLabel itemLabel = new JLabel(icon + " " + item);
                itemLabel.setForeground(TEXT_COLOR);
                itemLabel.setFont(new Font("Serif", Font.PLAIN, 14));
                statsPanel.add(itemLabel);
            }
            
            // If there are more items, add a "more..." label
            if (inventory.size() > displayLimit) {
                JLabel moreLabel = new JLabel("And " + (inventory.size() - displayLimit) + " more...");
                moreLabel.setForeground(TEXT_COLOR);
                moreLabel.setFont(new Font("Serif", Font.ITALIC, 12));
                statsPanel.add(moreLabel);
            }
        }

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    /**
     * Converts first letter of a string to uppercase
     */
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Displays a story node and its choices
     * 
     * @param node The node to display
     */
    public void displayNode(StoryNode node) {
        // Check if this node triggers combat
        if (gameController.hasCombatTriggered()) {
            initiateCombat();
            return;
        }
        
        // Update story text
        storyTextArea.setText(node.getTitle() + "\n\n" + node.getContent());
        storyTextArea.setCaretPosition(0);

        // Update choices
        choicesPanel.removeAll();

        if (node.isEndNode()) {
            // If it's an ending node, show a dialog with the ending and offer to restart or go to menu
            SwingUtilities.invokeLater(() -> {
                String dialogTitle = isPositiveEnding(node.getTitle(), node.getContent()) ? 
                                   "Prophecy Fulfilled" : "Dark Outcome";
                
                EndingDialog endDialog = new EndingDialog(this, dialogTitle, node.getContent(), 
                                                      isPositiveEnding(node.getTitle(), node.getContent()));
                
                if (endDialog.showAndWait()) {
                    // Player wants to restart
                    gameController.startGame();
                    displayNode(gameController.getCurrentNode());
                } else {
                    // Player wants to return to main menu
                    returnToMenu();
                }
            });
            
            // Add a restart button in case dialog fails
            JButton restartButton = createChoiceButton("Continue Your Journey");
            restartButton.addActionListener(e -> {
                gameController.startGame();
                displayNode(gameController.getCurrentNode());
            });
            choicesPanel.add(restartButton);
        } else {
            // For regular nodes, display available choices
            List<Choice> availableChoices = node.getChoices();
            JLabel choicePrompt = new JLabel("What will you do?");
            choicePrompt.setForeground(ACCENT_COLOR);
            choicePrompt.setFont(BUTTON_FONT);
            choicePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);

            choicesPanel.add(choicePrompt);
            choicesPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            Character character = gameController.getCharacter();
            
            for (int i = 0; i < availableChoices.size(); i++) {
                final int choiceIndex = i;
                Choice choice = availableChoices.get(i);
                JButton choiceButton = createChoiceButton(choice.getText());
                
                // Disable button if item requirement not met
                boolean enabled = true;
                if (choice.hasItemRequirement() && !character.hasItem(choice.getRequiredItem())) {
                    enabled = false;
                    choiceButton.setText(choice.getText() + " (Requires: " + choice.getRequiredItem() + ")");
                    choiceButton.setBackground(new Color(60, 60, 80));
                }
                
                choiceButton.setEnabled(enabled);
                
                choiceButton.addActionListener(e -> {
                    // Handle the player's choice
                    StoryNode nextNode = gameController.makeChoice(choiceIndex);
                    if (nextNode != null) {
                        // If the next node has combat triggered, handle it first
                        if (gameController.hasCombatTriggered()) {
                            initiateCombat();
                        } else {
                            displayNode(nextNode);
                        }
                    }
                });

                choicesPanel.add(choiceButton);
                choicesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        // Update character stats
        updateStatsPanel();

        choicesPanel.revalidate();
        choicesPanel.repaint();
    }
    
    /**
     * Initiates a combat encounter on the current node
     */
    private void initiateCombat() {
        if (gameController.getCurrentNode().hasEntity()) {
            Entity enemy = gameController.getCurrentNode().getEntity();
            CombatScreen combatScreen = new CombatScreen(this, gameController.getCharacter(), enemy);
            boolean victorious = combatScreen.startCombat();
            
            // Record combat result
            gameController.recordCombatResult(enemy, victorious);
            
            // Update node combat trigger state to prevent re-triggering
            gameController.getCurrentNode().setCombatTriggered(false);
            
            // If player was defeated and doesn't have energy, show game over
            if (!victorious && gameController.getCharacter().getAttribute("energy") <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "You have been defeated by " + enemy.getName() + ".\n\n" +
                    "Your prophetic journey has come to an end.", 
                    "Defeat", JOptionPane.WARNING_MESSAGE);
                
                // Return to menu
                returnToMenu();
            } else {
                // Continue with the story
                displayNode(gameController.getCurrentNode());
            }
        }
    }
    
    /**
     * Determines if an ending is positive based on keywords in the text
     */
    private boolean isPositiveEnding(String title, String text) {
        String combined = (title + " " + text).toLowerCase();
        String[] positiveWords = {"triumph", "victory", "fulfilled", "success", "saved", "prosperity"};
        String[] negativeWords = {"dark", "failed", "doom", "death", "destruction", "consumed", "lost"};
        
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (String word : positiveWords) {
            if (combined.contains(word)) positiveCount++;
        }
        
        for (String word : negativeWords) {
            if (combined.contains(word)) negativeCount++;
        }
        
        return positiveCount > negativeCount;
    }

    /**
     * Saves the current game
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            boolean success = gameController.saveGame(filePath);

            if (success) {
                JOptionPane.showMessageDialog(this, "Journey saved successfully.",
                        "Save", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error saving journey.",
                        "Save", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads a saved game
     */
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            boolean success = gameController.loadGame(filePath);

            if (success) {
                displayNode(gameController.getCurrentNode());
                JOptionPane.showMessageDialog(this, "Journey loaded successfully.",
                        "Load", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error loading journey.",
                        "Load", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Returns to the main menu
     */
    private void returnToMenu() {
        if (JOptionPane.showConfirmDialog(
                this,
                "Return to the main menu? Unsaved progress will be lost.",
                "Return to Menu",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            this.dispose();
            MainMenuUI menuUI = new MainMenuUI();
            menuUI.setVisible(true);
        }
    }
}