// CharacterPortrait.java
package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Class for generating character portraits
 */
public class CharacterPortrait {
    private String characterType;
    private Color primaryColor;
    private Color secondaryColor;
    private Color accentColor;
    private Random random = new Random();
    
    /**
     * Creates a character portrait
     * @param characterType The type of character
     * @param primaryColor Primary color scheme
     * @param secondaryColor Secondary color scheme
     */
    public CharacterPortrait(String characterType, Color primaryColor, Color secondaryColor) {
        this.characterType = characterType;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.accentColor = new Color(
            Math.min(255, primaryColor.getRed() + 40),
            Math.min(255, primaryColor.getGreen() + 40),
            Math.min(255, primaryColor.getBlue() + 40)
        );
    }
    
    /**
     * @return the character type
     */
    public String getCharacterType() {
        return characterType;
    }
    
    /**
     * Draws the character portrait
     * @param g Graphics context
     * @param centerX Center X coordinate
     * @param centerY Center Y coordinate
     */
    public void draw(Graphics g, int centerX, int centerY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch (characterType) {
            case "Prophet":
                drawProphet(g2d, centerX, centerY);
                break;
            case "Seer":
                drawSeer(g2d, centerX, centerY);
                break;
            case "Oracle":
                drawOracle(g2d, centerX, centerY);
                break;
            case "Scholar":
                drawScholar(g2d, centerX, centerY);
                break;
            default:
                drawProphet(g2d, centerX, centerY);
        }
    }
    
    /**
     * Draws a prophet character
     */
    private void drawProphet(Graphics2D g, int centerX, int centerY) {
        // Base body
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // Draw staff first (behind figure)
        g.setColor(new Color(120, 80, 40));
        g.fillRect(centerX + 40, shoulderY, 5, feetY - shoulderY + 20);
        
        // Tall golden finial on staff
        g.setColor(new Color(212, 175, 55));
        int[] staffTopX = {centerX + 42, centerX + 35, centerX + 43, centerX + 50};
        int[] staffTopY = {shoulderY, shoulderY - 15, shoulderY - 30, shoulderY - 15};
        g.fillPolygon(staffTopX, staffTopY, 4);
        
        // Robes
        g.setColor(primaryColor);
        
        // Main body robe
        int[] robeX = {centerX - 30, centerX + 30, centerX + 50, centerX - 50};
        int[] robeY = {shoulderY, shoulderY, feetY, feetY};
        g.fillPolygon(robeX, robeY, 4);
        
        // Hood
        g.fillOval(centerX - 25, headY - 10, 50, 50);
        
        // Face (shadowed by hood)
        g.setColor(new Color(240, 220, 180, 180));
        g.fillOval(centerX - 15, headY, 30, 35);
        
        // Decorative belt
        g.setColor(secondaryColor);
        g.fillRect(centerX - 30, waistY - 10, 60, 7);
        
        // Decorative symbols on robe
        g.setColor(accentColor);
        
        // Draw a few mystical symbols
        drawSymbol(g, centerX - 15, waistY + 30, 10);
        drawSymbol(g, centerX + 15, waistY + 20, 10);
        drawSymbol(g, centerX, waistY + 50, 12);
    }
    
    /**
     * Draws a seer character
     */
    private void drawSeer(Graphics2D g, int centerX, int centerY) {
        // Base body
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // Robes (flowy and ethereal)
        g.setColor(primaryColor);
        
        // Flowing robe with asymmetrical design
        int[] robeX = {centerX - 35, centerX + 45, centerX + 65, centerX - 55};
        int[] robeY = {shoulderY, shoulderY, feetY, feetY};
        g.fillPolygon(robeX, robeY, 4);
        
        // Translucent outer layer
        g.setColor(new Color(primaryColor.getRed(), primaryColor.getGreen(), 
                           primaryColor.getBlue(), 120));
        int[] outerRobeX = {centerX - 45, centerX + 55, centerX + 80, centerX - 70};
        int[] outerRobeY = {shoulderY + 10, shoulderY + 10, feetY + 10, feetY + 10};
        g.fillPolygon(outerRobeX, outerRobeY, 4);
        
        // Head without hood
        g.setColor(new Color(240, 220, 180));
        g.fillOval(centerX - 18, headY, 36, 40);
        
        // Long flowing hair
        g.setColor(Color.BLACK);
        g.fillArc(centerX - 25, headY - 5, 50, 70, 0, 180);
        
        // Decorative headdress/circlet
        g.setColor(secondaryColor);
        g.drawArc(centerX - 20, headY, 40, 20, 0, 180);
        g.fillOval(centerX - 2, headY - 2, 4, 4);
        g.fillOval(centerX + 15, headY + 3, 3, 3);
        g.fillOval(centerX - 15, headY + 3, 3, 3);
        
        // Face features (eyes that glow slightly)
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 10, headY + 15, 5, 3);
        g.fillOval(centerX + 5, headY + 15, 5, 3);
        
        // Glowing effect around eyes
        g.setColor(new Color(secondaryColor.getRed(), secondaryColor.getGreen(), 
                           secondaryColor.getBlue(), 50));
        g.fillOval(centerX - 12, headY + 13, 9, 7);
        g.fillOval(centerX + 3, headY + 13, 9, 7);
        
        // Decorative patterns on robe
        g.setColor(secondaryColor);
        for (int i = 0; i < 5; i++) {
            int x = centerX - 25 + random.nextInt(50);
            int y = shoulderY + 20 + random.nextInt(waistY - shoulderY - 40);
            g.drawOval(x, y, 3, 3);
        }
        
        // Draw a hovering crystal orb
        drawHoveringOrb(g, centerX - 25, shoulderY + 30);
    }
    
    /**
     * Draws an oracle character
     */
    private void drawOracle(Graphics2D g, int centerX, int centerY) {
        // Base body
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // Elaborate ritual robes
        g.setColor(primaryColor);
        
        // Base robe
        g.fillRect(centerX - 30, shoulderY, 60, feetY - shoulderY);
        
        // Ornate outer robe/shawl
        g.setColor(secondaryColor);
        int[] shawlX = {centerX - 40, centerX + 40, centerX + 35, centerX - 35};
        int[] shawlY = {shoulderY, shoulderY, waistY, waistY};
        g.fillPolygon(shawlX, shawlY, 4);
        
        // Ritual mask or headdress
        g.setColor(new Color(212, 175, 55));
        g.fillOval(centerX - 25, headY - 10, 50, 50);
        
        // Mask details
        g.setColor(primaryColor);
        g.fillOval(centerX - 17, headY + 10, 12, 7); // Left eye hole
        g.fillOval(centerX + 5, headY + 10, 12, 7);  // Right eye hole
        
        // Decorative lines on the mask
        g.setColor(accentColor);
        for (int i = 0; i < 3; i++) {
            g.drawLine(centerX - 20 + i*20, headY, centerX - 20 + i*20, headY + 30);
        }
        
        // Amulet or ritual jewelry
        g.setColor(secondaryColor);
        g.fillOval(centerX - 7, shoulderY + 10, 14, 14);
        g.setColor(accentColor);
        g.drawOval(centerX - 7, shoulderY + 10, 14, 14);
        
        // Ritual objects/patterns
        drawSymbol(g, centerX - 25, waistY - 20, 8);
        drawSymbol(g, centerX + 25, waistY - 20, 8);
        drawSymbol(g, centerX, waistY - 10, 10);
    }
    
    /**
     * Draws a scholar character
     */
    private void drawScholar(Graphics2D g, int centerX, int centerY) {
        // Base body
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // More practical robes with many pockets
        g.setColor(primaryColor);
        
        // Main robe
        g.fillRect(centerX - 30, shoulderY, 60, feetY - shoulderY);
        
        // Practical outer vest/coat
        g.setColor(secondaryColor);
        g.fillRect(centerX - 25, shoulderY, 50, waistY - shoulderY + 30);
        
        // Belt with pouches
        g.setColor(new Color(101, 67, 33));
        g.fillRect(centerX - 30, waistY - 5, 60, 10);
        
        // Pouches on belt
        g.setColor(new Color(140, 100, 60));
        g.fillRect(centerX - 20, waistY - 3, 10, 15);
        g.fillRect(centerX + 10, waistY - 3, 10, 15);
        
        // Head (no hood or mask)
        g.setColor(new Color(240, 220, 180));
        g.fillOval(centerX - 18, headY, 36, 40);
        
        // Scholarly cap/hat
        g.setColor(secondaryColor);
        g.fillArc(centerX - 25, headY - 10, 50, 25, 0, 180);
        g.fillRect(centerX - 25, headY, 50, 5);
        
        // Face features
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 10, headY + 15, 4, 4); // Left eye
        g.fillOval(centerX + 6, headY + 15, 4, 4);  // Right eye
        
        // Glasses
        g.setColor(accentColor);
        g.drawOval(centerX - 13, headY + 13, 10, 8); // Left lens
        g.drawOval(centerX + 3, headY + 13, 10, 8);  // Right lens
        g.drawLine(centerX - 3, headY + 17, centerX + 3, headY + 17); // Bridge
        
        // Beard or facial hair
        g.setColor(Color.DARK_GRAY);
        g.fillArc(centerX - 15, headY + 25, 30, 20, 180, 180);
        
        // Scroll or book
        g.setColor(new Color(240, 230, 200));
        g.fillRect(centerX - 40, shoulderY + 30, 20, 30);
        g.setColor(Color.BLACK);
        // Draw some lines to represent text
        g.drawLine(centerX - 35, shoulderY + 40, centerX - 25, shoulderY + 40);
        g.drawLine(centerX - 35, shoulderY + 45, centerX - 30, shoulderY + 45);
        g.drawLine(centerX - 35, shoulderY + 50, centerX - 27, shoulderY + 50);
    }
    
    /**
     * Draws a mystical symbol
     */
    private void drawSymbol(Graphics2D g, int x, int y, int size) {
        int type = random.nextInt(5);
        Color original = g.getColor();
        
        switch (type) {
            case 0: // Star
                int[] starX = new int[10];
                int[] starY = new int[10];
                
                for (int i = 0; i < 10; i++) {
                    double angle = Math.PI/2 + i * Math.PI/5;
                    double radius = (i % 2 == 0) ? size/2 : size;
                    starX[i] = x + (int)(radius * Math.cos(angle));
                    starY[i] = y + (int)(radius * Math.sin(angle));
                }
                
                g.fillPolygon(starX, starY, 10);
                break;
                
            case 1: // Circle with dot
                g.drawOval(x - size/2, y - size/2, size, size);
                g.fillOval(x - 1, y - 1, 3, 3);
                break;
                
            case 2: // Triangle
                int[] triX = {x, x - size/2, x + size/2};
                int[] triY = {y - size/2, y + size/2, y + size/2};
                g.drawPolygon(triX, triY, 3);
                break;
                
            case 3: // Spiral
                for (int i = 0; i < 720; i += 20) {
                    double angle = Math.toRadians(i);
                    double radius = size * i / 720.0;
                    int px = x + (int)(radius * Math.cos(angle));
                    int py = y + (int)(radius * Math.sin(angle));
                    g.fillOval(px - 1, py - 1, 2, 2);
                }
                break;
                
            case 4: // Eye
                g.drawOval(x - size/2, y - size/4, size, size/2);
                g.fillOval(x - size/6, y - size/6, size/3, size/3);
                break;
        }
        
        g.setColor(original);
    }
    
    /**
     * Draws a hovering orb
     */
    private void drawHoveringOrb(Graphics2D g, int x, int y) {
        // Create a radial gradient for the orb
        RadialGradientPaint gradient = new RadialGradientPaint(
            x + 10, y - 5, 20,
            new float[] {0.0f, 0.7f, 1.0f},
            new Color[] {
                new Color(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue(), 240),
                new Color(secondaryColor.getRed()/2, secondaryColor.getGreen()/2, secondaryColor.getBlue()/2, 180),
                new Color(20, 20, 20, 100)
            }
        );
        
        // Save the old paint and set the gradient
        Paint oldPaint = g.getPaint();
        g.setPaint(gradient);
        
        // Draw the orb
        g.fill(new Ellipse2D.Double(x, y, 20, 20));
        
        // Restore the old paint
        g.setPaint(oldPaint);
        
        // Add a highlight
        g.setColor(new Color(255, 255, 255, 180));
        g.fillOval(x + 5, y + 3, 5, 5);
    }
}