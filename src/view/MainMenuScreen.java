package src.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuScreen extends JPanel {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton mapButton;
    private JButton settingsButton;
    private JButton exitButton;

    public MainMenuScreen() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // Create main panel with GridBagLayout for better button spacing
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title label
        JLabel titleLabel = new JLabel("WIZZARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.insets = new Insets(20, 10, 40, 10);
        mainPanel.add(titleLabel, gbc);
        
        // Reset insets for buttons
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // New Game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "CHARACTER_CREATION");
        });
        mainPanel.add(newGameButton, gbc);
        
        // Load Game button
        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "LOAD_GAME");
        });
        mainPanel.add(loadGameButton, gbc);
        
        // Map button
        mapButton = new JButton("Map");
        mapButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "MAP");
        });
        mainPanel.add(mapButton, gbc);
        
        // Settings button
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "SETTINGS");
        });
        mainPanel.add(settingsButton, gbc);
        
        // Exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton, gbc);
        
        // Add main panel to center
        add(mainPanel, BorderLayout.CENTER);
    }
} 