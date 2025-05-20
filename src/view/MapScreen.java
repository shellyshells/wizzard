package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MapScreen extends JPanel {
    private JButton backButton;
    private JPanel mapPanel;
    private JScrollPane scrollPane;

    // Main menu style constants
    private static final Color BACKGROUND_COLOR = new Color(20, 12, 28);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Color LIGHT_COLOR = new Color(255, 255, 200);
    private static final Color NATURE_COLOR = new Color(200, 255, 200);
    private static final Color HEALING_COLOR = new Color(200, 200, 255);
    private static final Color BALANCE_COLOR = new Color(255, 200, 255);
    private static final Color BUTTON_COLOR = new Color(60, 40, 80);
    private static final Color BUTTON_TEXT_COLOR = new Color(240, 230, 215);
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 70, 130);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 36);
    private static final Font SECTION_FONT = new Font("Serif", Font.BOLD, 24);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 18);

    public MapScreen() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        initializeComponents();
    }

    private void initializeComponents() {
        // Styled back button with hover and rounded corners
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(TEXT_FONT.deriveFont(Font.BOLD));
        backButton.setBackground(BUTTON_COLOR);
        backButton.setForeground(BUTTON_TEXT_COLOR);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(260, 44));
        backButton.setBorder(new EmptyBorder(8, 24, 8, 24));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.setOpaque(true);
        backButton.setBorder(new javax.swing.border.LineBorder(new Color(100, 70, 130), 2, true));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(BUTTON_HOVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(BUTTON_COLOR);
            }
        });
        backButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            MainMenuUI menuUI = new MainMenuUI();
            menuUI.setVisible(true);
        });
        // Center the button at the top
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        backPanel.setOpaque(false);
        backPanel.add(backButton);
        add(backPanel, BorderLayout.NORTH);

        // Map panel with custom painting
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                setBackground(BACKGROUND_COLOR);

                int xPad = 60;
                int y = 60;
                int sectionSpacing = 120;
                int lineSpacing = 28;

                // Title
                g2d.setFont(TITLE_FONT);
                g2d.setColor(TEXT_COLOR);
                g2d.drawString("Paths of Prophecy", xPad, y);
                y += 40;

                // Light Ending
                y += 30;
                g2d.setFont(SECTION_FONT);
                g2d.setColor(LIGHT_COLOR);
                g2d.drawString("Light Ending:", xPad, y);
                g2d.setFont(TEXT_FONT);
                g2d.setColor(TEXT_COLOR);
                g2d.drawString("Path: Light Path → Radiant Guardian → Dawn Bringer", xPad + 30, y + lineSpacing);
                g2d.drawString("Requirements: Collect all light items, defeat all light enemies, high wisdom/charisma", xPad + 30, y + 2 * lineSpacing);

                // Nature Ending
                y += sectionSpacing;
                g2d.setFont(SECTION_FONT);
                g2d.setColor(NATURE_COLOR);
                g2d.drawString("Nature Ending:", xPad, y);
                g2d.setFont(TEXT_FONT);
                g2d.setColor(TEXT_COLOR);
                g2d.drawString("Path: Nature Path → Forest Guardian → Earth Shaper", xPad + 30, y + lineSpacing);
                g2d.drawString("Requirements: Collect all nature items, defeat all nature enemies, high intuition/wisdom", xPad + 30, y + 2 * lineSpacing);

                // Healing Ending
                y += sectionSpacing;
                g2d.setFont(SECTION_FONT);
                g2d.setColor(HEALING_COLOR);
                g2d.drawString("Healing Ending:", xPad, y);
                g2d.setFont(TEXT_FONT);
                g2d.setColor(TEXT_COLOR);
                g2d.drawString("Path: Healing Path → Peace Keeper → Hope Bringer", xPad + 30, y + lineSpacing);
                g2d.drawString("Requirements: Collect all healing items, defeat all healing enemies, high charisma/wisdom", xPad + 30, y + 2 * lineSpacing);

                // True Balance Ending
                y += sectionSpacing;
                g2d.setFont(SECTION_FONT);
                g2d.setColor(BALANCE_COLOR);
                g2d.drawString("True Balance Ending:", xPad, y);
                g2d.setFont(TEXT_FONT);
                g2d.setColor(TEXT_COLOR);
                g2d.drawString("Path: True Balance Path", xPad + 30, y + lineSpacing);
                g2d.drawString("Requirements: At least one item/enemy from each category, balanced attributes", xPad + 30, y + 2 * lineSpacing);
            }
        };
        mapPanel.setPreferredSize(new Dimension(1200, 900));
        mapPanel.setBackground(BACKGROUND_COLOR);

        // Add scroll pane for the map
        scrollPane = new JScrollPane(mapPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);
    }
} 