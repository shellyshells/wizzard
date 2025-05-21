// InventoryScreen.java
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.Character;
import model.Inventory;

/**
 * Screen for managing the player's inventory
 */
public class InventoryScreen extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color ACCENT_COLOR = new Color(140, 100, 180);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 20);
    private static final Font ITEM_FONT = new Font("Serif", Font.PLAIN, 16);
    private static final Font DESC_FONT = new Font("Serif", Font.ITALIC, 14);

    private Character character;
    private JPanel inventoryPanel;
    private JTextArea itemDescriptionArea;
    private boolean encounterMode;
    private boolean usedItem = false;
    private String lastUsedItem = "";

    /**
     * Creates an inventory screen
     * @param parent Parent frame
     * @param character Player character
     */
    public InventoryScreen(Window parent, Character character) {
        this(parent, character, false);
    }
    
    /**
     * Creates an inventory screen with encounter mode option
     * @param parent Parent window
     * @param character Player character
     * @param encounterMode Whether this is being used during an encounter
     */
    public InventoryScreen(Window parent, Character character, boolean encounterMode) {
        // Fix for Java Swing compatibility
        super(parent instanceof Frame ? (Frame) parent : null, "Mystical Items", true);
        this.character = character;
        this.encounterMode = encounterMode;
        
        setSize(500, 600);
        setLocationRelativeTo(parent);
        initComponents();
    }

    /**
     * Initializes screen components
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Your Mystical Items", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel for inventory display
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Panel for the inventory list
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setBackground(BACKGROUND_COLOR);
        
        // Panel for item description
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(BACKGROUND_COLOR);
        descriptionPanel.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        
        JLabel descriptionLabel = new JLabel("Item Description", SwingConstants.CENTER);
        descriptionLabel.setFont(ITEM_FONT.deriveFont(Font.BOLD));
        descriptionLabel.setForeground(ACCENT_COLOR);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        
        itemDescriptionArea = new JTextArea();
        itemDescriptionArea.setFont(DESC_FONT);
        itemDescriptionArea.setForeground(TEXT_COLOR);
        itemDescriptionArea.setBackground(new Color(30, 20, 40));
        itemDescriptionArea.setEditable(false);
        itemDescriptionArea.setLineWrap(true);
        itemDescriptionArea.setWrapStyleWord(true);
        itemDescriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemDescriptionArea.setText("Select an item to view its description.");
        
        descriptionPanel.add(new JScrollPane(itemDescriptionArea), BorderLayout.CENTER);
        
        // Update inventory display
        updateInventoryDisplay();

        // Scroll panes
        JScrollPane inventoryScroll = new JScrollPane(inventoryPanel);
        inventoryScroll.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        
        // Split the content panel
        contentPanel.add(inventoryScroll, BorderLayout.CENTER);
        contentPanel.add(descriptionPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton closeButton = new JButton(encounterMode ? "Return to Encounter" : "Close");
        closeButton.setFont(ITEM_FONT);
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setBackground(new Color(60, 40, 80));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Updates the inventory display
     */
    private void updateInventoryDisplay() {
        inventoryPanel.removeAll();
        
        List<String> items = character.getInventory();
        
        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your inventory is empty.");
            emptyLabel.setFont(ITEM_FONT);
            emptyLabel.setForeground(TEXT_COLOR);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inventoryPanel.add(emptyLabel);
        } else {
            for (String item : items) {
                JPanel itemPanel = createItemPanel(item);
                inventoryPanel.add(itemPanel);
            }
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
    
    /**
     * Creates a panel for displaying an inventory item
     */
    private JPanel createItemPanel(String item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 20, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        String icon = Character.getItemIcon(item);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(icon + " " + item);
        nameLabel.setFont(ITEM_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        infoPanel.add(nameLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);
        
        // View button
        JButton viewButton = new JButton("Info");
        styleButton(viewButton);
        viewButton.addActionListener(e -> {
            String description = Inventory.getItemDescription(item);
            String effect = Inventory.getItemEffect(item);
            itemDescriptionArea.setText(description + "\n\n" + effect);
        });
        
        // Use button (only for usable items)
        JButton useButton = new JButton("Use");
        styleButton(useButton);
        
        // Check if item is usable
        boolean usable = isItemUsable(item);
        useButton.setEnabled(usable);
        
        useButton.addActionListener(e -> {
            String result = Inventory.useItem(character, item);
            itemDescriptionArea.setText(result);
            usedItem = true;
            lastUsedItem = item;
            updateInventoryDisplay();
        });
        
        buttonPanel.add(viewButton);
        buttonPanel.add(useButton);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        
        // Add hover effect
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(50, 35, 60));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(30, 20, 40));
            }
        });
        
        return panel;
    }
    
    /**
     * Determines if an item is usable
     */
    private boolean isItemUsable(String item) {
        return item.equals("Healer's Potion") || 
               item.equals("Sacred Water") ||
               item.equals("Vision Incense");
    }
    
    /**
     * Applies styling to a button
     */
    private void styleButton(JButton button) {
        button.setFont(ITEM_FONT.deriveFont(Font.PLAIN, 12));
        button.setForeground(TEXT_COLOR);
        button.setBackground(new Color(60, 40, 80));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(60, 25));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(80, 60, 100));
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(60, 40, 80));
                }
            }
        });
    }
    
    /**
     * @return Whether an item was used during this inventory session
     */
    public boolean hasUsedItem() {
        return usedItem;
    }
    
    /**
     * @return The last item that was used
     */
    public String getLastUsedItem() {
        return lastUsedItem;
    }
}