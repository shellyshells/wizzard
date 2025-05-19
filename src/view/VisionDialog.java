// VisionDialog.java
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Dialog for displaying prophetic visions
 */
public class VisionDialog extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(15, 10, 30);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color ACCENT_COLOR = new Color(180, 130, 255);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 24);
    private static final Font TEXT_FONT = new Font("Serif", Font.ITALIC, 18);
    
    private final CountDownLatch latch = new CountDownLatch(1);
    private Random random = new Random();
    private String[] symbols;
    private int selectedSymbolIndex = -1;
    
    /**
     * Creates a vision dialog
     * @param parent Parent frame
     * @param vision The vision text
     */
    public VisionDialog(JFrame parent, String vision) {
        super(parent, "Prophetic Vision", true);
        
        setSize(650, 450);
        setLocationRelativeTo(parent);
        setUndecorated(true); // No window decoration for more immersive feel
        
        // Generate mystical symbols for interpretation
        generateSymbols();
        
        initComponents(vision);
    }
    
    /**
     * Initializes dialog components
     */
    private void initComponents(String vision) {
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Create a starry background effect
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background gradient
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(15, 10, 30),
                    getWidth(), getHeight(), new Color(40, 20, 80)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw stars
                g2d.setColor(new Color(255, 255, 255, 180));
                for (int i = 0; i < 100; i++) {
                    int x = random.nextInt(getWidth());
                    int y = random.nextInt(getHeight());
                    int size = random.nextInt(2) + 1;
                    g2d.fillOval(x, y, size, size);
                }
                
                // Draw larger stars with glow
                for (int i = 0; i < 20; i++) {
                    int x = random.nextInt(getWidth());
                    int y = random.nextInt(getHeight());
                    
                    // Star glow
                    Color glowColor = new Color(180, 180, 255, 50);
                    RadialGradientPaint rgp = new RadialGradientPaint(
                        x, y, 10,
                        new float[] {0.0f, 1.0f},
                        new Color[] {glowColor, new Color(0, 0, 0, 0)}
                    );
                    g2d.setPaint(rgp);
                    g2d.fillOval(x - 10, y - 10, 20, 20);
                    
                    // Star itself
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x - 1, y - 1, 3, 3);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel titleLabel = new JLabel("A Vision Comes to You...", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Vision text
        JTextArea visionText = new JTextArea(vision);
        visionText.setFont(TEXT_FONT);
        visionText.setForeground(TEXT_COLOR);
        visionText.setLineWrap(true);
        visionText.setWrapStyleWord(true);
        visionText.setEditable(false);
        visionText.setOpaque(false);
        visionText.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Symbol panel for interpretation
        JPanel symbolPanel = new JPanel(new GridLayout(1, symbols.length, 10, 0));
        symbolPanel.setOpaque(false);
        
        for (int i = 0; i < symbols.length; i++) {
            final int index = i;
            JButton symbolButton = new JButton(symbols[i]);
            symbolButton.setFont(new Font("Dialog", Font.PLAIN, 24));
            symbolButton.setForeground(TEXT_COLOR);
            symbolButton.setBackground(new Color(60, 30, 100, 150));
            symbolButton.setBorderPainted(false);
            symbolButton.setFocusPainted(false);
            symbolButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Hover effect
            symbolButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    symbolButton.setBackground(new Color(100, 60, 160, 180));
                }
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    symbolButton.setBackground(new Color(60, 30, 100, 150));
                }
            });
            
            // Action to select this symbol for interpretation
            symbolButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSymbolIndex = index;
                    latch.countDown();
                    dispose();
                }
            });
            
            symbolPanel.add(symbolButton);
        }
        
        // Instruction label
        JLabel instructionLabel = new JLabel("Select a symbol to interpret your vision", JLabel.CENTER);
        instructionLabel.setFont(TEXT_FONT.deriveFont(Font.PLAIN, 14));
        instructionLabel.setForeground(TEXT_COLOR);
        instructionLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        // Close button (in case something goes wrong)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton closeButton = new JButton("Dismiss Vision");
        closeButton.setFont(TEXT_FONT.deriveFont(Font.PLAIN, 14));
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setBackground(new Color(80, 40, 120));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                latch.countDown();
                dispose();
            }
        });
        
        buttonPanel.add(closeButton);
        
        // Add components to main panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        contentPanel.add(titleLabel);
        contentPanel.add(visionText);
        contentPanel.add(instructionLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(symbolPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    /**
     * Generates mystical symbols for interpretation
     */
    private void generateSymbols() {
        symbols = new String[] {"🔮", "⚜️", "🕯️", "🌙", "✨", "👁️"};
    }
    
    /**
     * Shows the dialog and waits for the user to select a symbol
     * @return The index of the selected symbol, or -1 if dialog was dismissed
     */
    public int showAndWaitForSymbol() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return selectedSymbolIndex;
    }
}