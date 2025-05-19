// EndingDialog.java
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * Dialog shown at the end of a story path
 */
public class EndingDialog extends JDialog {
    private static final Color POSITIVE_COLOR = new Color(70, 130, 180);
    private static final Color NEGATIVE_COLOR = new Color(139, 0, 0);
    private static final Color TEXT_COLOR = new Color(240, 230, 215);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 28);
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 16);
    
    private boolean restart = false;
    private final CountDownLatch latch = new CountDownLatch(1);
    
    /**
     * Creates an ending dialog
     * @param parent Parent frame
     * @param title Dialog title
     * @param message Ending message
     * @param positive Whether this is a positive ending
     */
    public EndingDialog(JFrame parent, String title, String message, boolean positive) {
        super(parent, title, true);
        
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents(message, positive);
    }
    
    /**
     * Initializes dialog components
     */
    private void initComponents(String message, boolean positive) {
        Color backgroundColor = positive ? new Color(20, 40, 60) : new Color(40, 20, 20);
        Color accentColor = positive ? POSITIVE_COLOR : NEGATIVE_COLOR;
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Background with gradient
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, backgroundColor, 
                    getWidth(), getHeight(), 
                    new Color(backgroundColor.getRed()/3, backgroundColor.getGreen()/3, backgroundColor.getBlue()/3)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add some decorative elements
                g2d.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 40));
                for (int i = 0; i < getWidth(); i += 40) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
                for (int i = 0; i < getHeight(); i += 40) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title with icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        String iconText = positive ? "✨" : "🔮";
        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        iconLabel.setForeground(accentColor);
        
        JLabel titleLabel = new JLabel(getTitle());
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(accentColor);
        
        titlePanel.add(iconLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        titlePanel.add(titleLabel);
        
        // Message area
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(TEXT_FONT);
        messageArea.setForeground(TEXT_COLOR);
        messageArea.setBackground(new Color(0, 0, 0, 0));
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setOpaque(false);
        messageArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        JButton restartButton = new JButton("Begin New Journey");
        styleButton(restartButton, accentColor);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = true;
                latch.countDown();
                dispose();
            }
        });
        
        JButton menuButton = new JButton("Return to Main Menu");
        styleButton(menuButton, accentColor);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = false;
                latch.countDown();
                dispose();
            }
        });
        
        buttonPanel.add(restartButton);
        buttonPanel.add(menuButton);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    /**
     * Applies styling to a button
     */
    private void styleButton(JButton button, Color accentColor) {
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(new Color(accentColor.getRed()/2, accentColor.getGreen()/2, accentColor.getBlue()/2));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
        
        // Add hover effect
        Color hoverColor = new Color(
            Math.min(255, accentColor.getRed()*2/3), 
            Math.min(255, accentColor.getGreen()*2/3), 
            Math.min(255, accentColor.getBlue()*2/3)
        );
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(accentColor.getRed()/2, accentColor.getGreen()/2, accentColor.getBlue()/2));
            }
        });
    }
    
    /**
     * Shows the dialog and waits for user interaction
     * @return true if player wants to restart, false to return to menu
     */
    public boolean showAndWait() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return restart;
    }
}