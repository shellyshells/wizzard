// HelpWindow.java
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Window that displays help information
 */
public class HelpWindow extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color ACCENT_COLOR = new Color(140, 100, 180);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 24);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 16);

    /**
     * Creates a help window
     * @param parent Parent frame
     */
    public HelpWindow(JFrame parent) {
        super(parent, "Prophecy Guide", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        initComponents();
    }

    /**
     * Initializes window components
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = createTitleLabel("The Ancient Prophecy - Guide");
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Help sections
        addSection(mainPanel, "🔮 Your Prophetic Journey", 
            "You are a gifted seer who has received visions of an impending catastrophe. " +
            "Navigate through the story by making choices that will determine your fate and " +
            "the fate of the realm.\n\n" +
            "Your prophetic abilities allow you to perceive what others cannot, but beware - " +
            "not all visions reveal the truth, and some may lead you astray.");

        addSection(mainPanel, "📊 Character Attributes", 
            "• Wisdom: Your spiritual understanding and ability to make good judgments\n" +
            "• Intuition: Your natural ability to perceive the truth without conscious reasoning\n" +
            "• Charisma: Your persuasiveness and ability to influence others\n" +
            "• Perception: Your awareness of subtle details and changes\n" +
            "• Knowledge: Your accumulated learning and understanding of ancient texts\n" +
            "• Energy: Your spiritual and mental reserves for prophetic work");

        addSection(mainPanel, "🎒 Item Management", 
            "Throughout your journey, you'll acquire various mystical items that can aid you:\n\n" +
            "• Sacred Water: Restores 2 Energy points when consumed\n" +
            "• Mystical Crystal: Enhances visions and can store prophetic energy\n" +
            "• Protection Charm: Provides a one-time protection from fatal outcomes\n" +
            "• Oracle's Eye: Helps see through deceptions and grants insight\n" +
            "• Vision Incense: Can induce prophetic visions when used\n\n" +
            "Use your items wisely - some are consumable while others provide passive benefits.");

        addSection(mainPanel, "⚔️ Encounters", 
            "When you encounter significant entities or obstacles, you have several approaches:\n\n" +
            "• Persuade: Use your charisma to convince or influence\n" +
            "• Insight: Rely on your intuition to perceive hidden truths\n" +
            "• Knowledge: Apply your understanding of ancient texts and prophecies\n" +
            "• Items: Use appropriate items from your inventory\n" +
            "• Withdraw: Attempt to leave the encounter if it proves too challenging\n\n" +
            "Different approaches work better in different situations. Learn to recognize when " +
            "each is most effective.");

        addSection(mainPanel, "💭 Visions", 
            "Throughout your journey, you may receive prophetic visions. These can provide " +
            "valuable insights into future events or hidden truths, but interpreting them " +
            "correctly is crucial.\n\n" +
            "Some visions are straightforward, while others are symbolic or metaphorical. " +
            "Pay close attention to details, as they may be significant later in your journey.");

        addSection(mainPanel, "💾 Saving Your Journey", 
            "You can save your progress at any time using the Save button. This allows you " +
            "to return to your journey later or explore different paths by loading previous saves.");

        // Close button
        JButton closeButton = new JButton("I Understand");
        closeButton.setFont(TEXT_FONT);
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setBackground(new Color(60, 40, 80));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(closeButton);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);
    }

    /**
     * Creates a title label
     */
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Adds a section to the help panel
     */
    private void addSection(JPanel panel, String title, String content) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TEXT_FONT.deriveFont(Font.BOLD));
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(TEXT_FONT);
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setBackground(BACKGROUND_COLOR);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(contentArea);
    }
}