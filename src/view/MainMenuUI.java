// MainMenuUI.java
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.GameController;
import controller.ProgressTracker;
import controller.StoryLoader;
import model.Story;

/**
 * The main menu interface of the application
 */
public class MainMenuUI extends JFrame {
    private static final String TITLE = "The Ancient Prophecy";
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color BUTTON_COLOR = new Color(60, 40, 80);
    private static final Color BUTTON_TEXT_COLOR = new Color(240, 230, 215);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 18);
    private static final String BACKGROUND_IMAGE = "/Assets/Images/background-mystical.jpg";

    private JPanel mainMenuPanel;
    private JPanel chapterSelectPanel;
    private JPanel nameEntryPanel;
    private JPanel characterSelectPanel;
    private JPanel mapPanel;

    private Image backgroundImage;
    private int selectedChapter = 1;
    private CharacterPortrait selectedCharacter;
    private String selectedCharacterType = "Prophet"; // Default character type
    private ProgressTracker progressTracker;
    private MapScreen mapScreen;

    /**
     * Constructor
     */
    public MainMenuUI() {
        // Configure main window
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fullscreen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Try to load background image
        try {
            backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();
        } catch (Exception e) {
            System.out.println("Failed to load background image. Using default color.");
            backgroundImage = null;
        }

        progressTracker = new ProgressTracker();

        // Initialize components
        initComponents();

        // Show main menu on startup
        showMainMenu();
    }

    /**
     * Initializes UI components
     */
    private void initComponents() {
        // Main menu panel
        mainMenuPanel = createBackgroundPanel();
        mainMenuPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton playButton = createStyledButton("Begin Your Journey");
        JButton loadButton = createStyledButton("Continue Journey");
        JButton helpButton = createStyledButton("Prophecy Guide");
        JButton mapButton = createStyledButton("Path Map");
        JButton quitButton = createStyledButton("Leave This Realm");
        JButton settingsButton = createStyledButton("Settings");

        // Button actions
        playButton.addActionListener(e -> showChapterSelect());
        loadButton.addActionListener(e -> loadSavedGame());
        helpButton.addActionListener(e -> showHelp());
        mapButton.addActionListener(e -> showMap());
        quitButton.addActionListener(e -> System.exit(0));
        settingsButton.addActionListener(e -> showSettings());

        // Add buttons with spacing
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        addButtonWithSpacing(buttonPanel, playButton);
        addButtonWithSpacing(buttonPanel, loadButton);
        addButtonWithSpacing(buttonPanel, helpButton);
        addButtonWithSpacing(buttonPanel, mapButton);
        addButtonWithSpacing(buttonPanel, quitButton);

        // Center the button panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        // Settings panel
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsPanel.setOpaque(false);
        settingsPanel.add(settingsButton);

        mainMenuPanel.add(titlePanel, BorderLayout.NORTH);
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
        mainMenuPanel.add(settingsPanel, BorderLayout.SOUTH);

        // Chapter selection panel
        initChapterSelectPanel();

        // Name entry panel
        initNameEntryPanel();

        // Character selection panel
        initCharacterSelectPanel();

        // Initialize map panel
        mapScreen = new MapScreen();
    }

    /**
     * Initializes the chapter selection panel
     */
    private void initChapterSelectPanel() {
        chapterSelectPanel = createBackgroundPanel();
        chapterSelectPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Choose Your Prophecy");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        JPanel chapterButtonPanel = new JPanel();
        chapterButtonPanel.setLayout(new BoxLayout(chapterButtonPanel, BoxLayout.Y_AXIS));
        chapterButtonPanel.setOpaque(false);

        JButton chapter1Button = createStyledButton("Chapter 1: The Crystal of Truth");
        JButton chapter2Button = createStyledButton("Chapter 2: The Harbinger's Approach");
        JButton chapter3Button = createStyledButton("Chapter 3: The Final Revelation");
        JButton backButton = createStyledButton("Return");

        chapter1Button.addActionListener(e -> {
            selectedChapter = 1;
            showNameEntry();
        });

        chapter2Button.setEnabled(progressTracker.isChapterCompleted(1));
        chapter2Button.addActionListener(e -> {
            if (progressTracker.isChapterCompleted(1)) {
                selectedChapter = 2;
                showNameEntry();
            } else {
                JOptionPane.showMessageDialog(this,
                        "You must complete Chapter 1 before accessing Chapter 2.",
                        "Chapter Locked",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        chapter3Button.setEnabled(false);
        chapter3Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Chapter 3 is still being written in the book of fate. Please be patient!",
                    "Chapter in Development", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> showMainMenu());

        addButtonWithSpacing(chapterButtonPanel, chapter1Button);
        addButtonWithSpacing(chapterButtonPanel, chapter2Button);
        addButtonWithSpacing(chapterButtonPanel, chapter3Button);
        addButtonWithSpacing(chapterButtonPanel, backButton);

        // Center the buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(chapterButtonPanel);

        chapterSelectPanel.add(titlePanel, BorderLayout.NORTH);
        chapterSelectPanel.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the name entry panel
     */
    private void initNameEntryPanel() {
        nameEntryPanel = createBackgroundPanel();
        nameEntryPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Enter Your Name");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        JPanel nameInputPanel = new JPanel();
        nameInputPanel.setLayout(new BoxLayout(nameInputPanel, BoxLayout.Y_AXIS));
        nameInputPanel.setOpaque(false);

        JTextField nameField = new JTextField(20);
        nameField.setFont(BUTTON_FONT);
        nameField.setText("Oracle");

        JButton continueButton = createStyledButton("Continue");
        JButton backButton = createStyledButton("Back");

        continueButton.addActionListener(e -> showCharacterSelect());
        backButton.addActionListener(e -> showChapterSelect());

        // Center and align the text field
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textFieldPanel.setOpaque(false);
        textFieldPanel.add(nameField);

        nameInputPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        nameInputPanel.add(textFieldPanel);
        nameInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(continueButton);
        buttonPanel.add(backButton);

        nameInputPanel.add(buttonPanel);

        // Center the panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(nameInputPanel);

        nameEntryPanel.add(titlePanel, BorderLayout.NORTH);
        nameEntryPanel.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the character selection panel
     */
    private void initCharacterSelectPanel() {
        characterSelectPanel = createBackgroundPanel();
        characterSelectPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Choose Your Path");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        // Center panel with character grid
        JPanel charactersGrid = new JPanel(new GridLayout(1, 4, 40, 0));
        charactersGrid.setOpaque(false);

        // Create different character types
        java.util.List<CharacterPortrait> characters = new java.util.ArrayList<>();
        characters.add(new CharacterPortrait("Prophet", new Color(120, 60, 20), new Color(200, 150, 0)));
        characters.add(new CharacterPortrait("Seer", new Color(20, 70, 120), new Color(140, 180, 255)));
        characters.add(new CharacterPortrait("Oracle", new Color(80, 40, 100), new Color(150, 100, 200)));
        characters.add(new CharacterPortrait("Scholar", new Color(50, 90, 30), new Color(100, 160, 80)));

        // Default selection
        selectedCharacter = characters.get(0);

        // Add each character to the panel
        for (CharacterPortrait character : characters) {
            final String characterType = character.getCharacterType();

            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
            characterPanel.setOpaque(false);
            characterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Character preview
            JPanel avatarPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setOpaque(false);

                    // Selection frame
                    if (characterType.equals(selectedCharacterType)) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(new Color(180, 150, 255, 100));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                        g2d.setColor(new Color(180, 150, 255));
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);
                    }

                    // Draw the character
                    character.draw(g, getWidth() / 2, getHeight() / 2);
                }
            };
            avatarPanel.setPreferredSize(new Dimension(150, 250));
            avatarPanel.setMinimumSize(new Dimension(150, 250));
            avatarPanel.setMaximumSize(new Dimension(150, 250));

            // Make the panel clickable
            avatarPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            avatarPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedCharacterType = characterType;
                    selectedCharacter = character;
                    charactersGrid.repaint();
                }
            });

            // Character type
            JLabel typeLabel = new JLabel(characterType, JLabel.CENTER);
            typeLabel.setFont(BUTTON_FONT);
            typeLabel.setForeground(TEXT_COLOR);
            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Description
            JTextArea descArea = new JTextArea(getCharacterDescription(characterType));
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setFont(new Font("Serif", Font.PLAIN, 14));
            descArea.setForeground(TEXT_COLOR);
            descArea.setBackground(new Color(0, 0, 0, 0));
            descArea.setOpaque(false);
            descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            descArea.setPreferredSize(new Dimension(200, 100));
            descArea.setMaximumSize(new Dimension(200, 100));

            characterPanel.add(avatarPanel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            characterPanel.add(typeLabel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            characterPanel.add(descArea);

            charactersGrid.add(characterPanel);
        }

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(charactersGrid);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton startButton = createStyledButton("Begin Your Journey");
        JButton backButton = createStyledButton("Back");

        startButton.addActionListener(e -> {
            String playerName = "Oracle"; // Default name
            startGameWithSelectedCharacter(playerName, selectedCharacterType);
        });

        backButton.addActionListener(e -> showNameEntry());

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);

        characterSelectPanel.add(titlePanel, BorderLayout.NORTH);
        characterSelectPanel.add(centerPanel, BorderLayout.CENTER);
        characterSelectPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Returns the description for a character type
     */
    private String getCharacterDescription(String characterType) {
        switch (characterType) {
            case "Prophet":
                return "Gifted with clear prophetic visions and persuasive speech. A natural leader whose words carry weight with both the common folk and those in power.";
            case "Seer":
                return "Blessed with extraordinary intuition and perception. Can sense things others cannot and often receives spontaneous visions of possible futures.";
            case "Oracle":
                return "Combines prophetic gifts with ancient knowledge. Trained in the rituals and traditions of the mystical orders, with power to interpret signs.";
            case "Scholar":
                return "A master of ancient texts and forgotten lore. What they lack in natural prophetic ability, they make up for with vast knowledge of history and ritual.";
            default:
                return "A seeker of truth with unique talents for perceiving what others cannot.";
        }
    }

    /**
     * Creates a panel with background image
     */
    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw image stretched to panel size
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                    // Add semi-transparent overlay for better text visibility
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        return panel;
    }

    /**
     * Creates a styled button for the interface
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button dimensions
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 70, 130));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Adds a button with vertical spacing to a panel
     */
    private void addButtonWithSpacing(JPanel panel, JButton button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    /**
     * Shows the main menu
     */
    public void showMainMenu() {
        getContentPane().removeAll();
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }

    /**
     * Shows the chapter selection panel
     */
    private void showChapterSelect() {
        getContentPane().removeAll();
        getContentPane().add(chapterSelectPanel);
        revalidate();
        repaint();
    }

    /**
     * Shows the name entry panel
     */
    private void showNameEntry() {
        getContentPane().removeAll();
        getContentPane().add(nameEntryPanel);
        revalidate();
        repaint();
    }

    /**
     * Shows the character selection panel
     */
    private void showCharacterSelect() {
        getContentPane().removeAll();
        getContentPane().add(characterSelectPanel);
        revalidate();
        repaint();
    }

    /**
     * Starts the game with selected character
     */
    private void startGameWithSelectedCharacter(String playerName, String characterType) {
        try {
            // Create game controller with appropriate story
            Story story;
            
            if (selectedChapter == 2) {
                story = StoryLoader.createChapter2Story();
            } else {
                story = StoryLoader.createDemoStory();
            }
            
            System.out.println("Starting game with character: " + playerName + " (" + characterType + ")");
            
            // Close this menu before creating new window
            setVisible(false);
            
            // Create controller with character data
            GameController gameController = new GameController(story, playerName, characterType);
            
            // Completely close this window
            dispose();
            
            // Launch the game with improved interface
            SwingUtilities.invokeLater(() -> {
                ProphecyUI gameUI = new ProphecyUI(gameController);
                gameUI.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred while starting the game: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads a saved game
     */
    private void loadSavedGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Create controller and load save
            Story story = StoryLoader.createDemoStory(); // Default story
            GameController gameController = new GameController(story, "Oracle");
            
            boolean success = gameController.loadGame(filePath);
            
            if (success) {
                // Close this menu
                this.dispose();
                
                // Launch the game with the loaded save
                ProphecyUI gameUI = new ProphecyUI(gameController);
                gameUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to load saved game.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Shows the help screen
     */
    private void showHelp() {
        JOptionPane.showMessageDialog(this,
                "The Ancient Prophecy\n\n" +
                "You are a gifted seer who has received visions of an impending catastrophe.\n" +
                "Navigate through the story by making choices that will determine your fate and\n" +
                "the fate of the realm.\n\n" +
                "Your prophetic abilities allow you to perceive what others cannot, but beware -\n" +
                "not all visions reveal the truth, and some may lead you astray.\n\n" +
                "May the wisdom of the ancients guide your path!",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the settings screen
     */
    private void showSettings() {
        JOptionPane.showMessageDialog(this,
                "Settings will be available in a future update.",
                "Settings", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMap() {
        getContentPane().removeAll();
        getContentPane().add(mapScreen);
        revalidate();
        repaint();
    }
}