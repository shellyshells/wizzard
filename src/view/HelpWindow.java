// HelpWindow.java - Updated with combat information
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
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
        setSize(600, 600); // Made taller to accommodate more content
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
            "• Energy: Your spiritual and mental reserves for prophetic work and combat");

        addSection(mainPanel, "🎒 Item Management", 
            "Throughout your journey, you'll acquire various mystical items that can aid you:\n\n" +
            "• Sacred Water: Restores 2 Energy points when consumed\n" +
            "• Mystical Crystal: Enhances visions and amplifies mystical attacks in combat\n" +
            "• Protection Charm: Provides a one-time protection from fatal outcomes\n" +
            "• Oracle's Eye: Helps see through deceptions and grants combat insight\n" +
            "• Vision Incense: Can induce prophetic visions when used\n" +
            "• Prophet's Staff: Enhances prophetic abilities and mystical combat attacks\n\n" +
            "Use your items wisely - some are consumable while others provide passive benefits.");

        // New section for combat
        addSection(mainPanel, "⚔️ Combat System", 
            "When confronting hostile entities, you may enter combat. The combat system offers several actions:\n\n" +
            "• Quick Attack: A swift strike with a high chance to hit but moderate damage\n" +
            "• Focused Strike: A powerful attack that deals higher damage but may miss\n" +
            "• Defensive Stance: Reduces damage from the next enemy attack by 50%\n" +
            "• Mystic Blast: A powerful energy attack available to those with high wisdom\n" +
            "• Prophetic Insight: Predict and counter enemy moves using your prophetic abilities\n\n" +
            "Your attributes affect combat effectiveness - Perception improves accuracy, Wisdom and " +
            "Intuition enhance mystical attacks, and Energy represents your combat health.\n\n" +
            "Items can provide advantages in combat. For example, the Mystical Crystal amplifies " +
            "Mystic Blast damage, while the Oracle's Eye enhances Prophetic Insight.");

        addSection(mainPanel, "⚔️ Encounters", 
            "There are two types of encounters with entities:\n\n" +
            "1. Traditional Encounters: Resolve through dialogue and persuasion using your attributes\n" +
            "2. Combat Encounters: Direct confrontations using the combat system\n\n" +
            "For traditional encounters, you have several approaches:\n" +
            "• Persuade: Use your charisma to convince or influence\n" +
            "• Insight: Rely on your intuition to perceive hidden truths\n" +
            "• Knowledge: Apply your understanding of ancient texts and prophecies\n\n" +
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