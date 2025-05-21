// Main.java
import javax.swing.SwingUtilities;

import controller.StoryLoader;
import view.MainMenuUI;

/**
 * Main class for starting the application
 */
public class Main {

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        System.out.println("Starting The Ancient Prophecy...");

        // Preload stories for better performance
        preloadStories();
        
        // Launch the main menu UI
        SwingUtilities.invokeLater(() -> {
            MainMenuUI menuUI = new MainMenuUI();
            menuUI.setVisible(true);
        });
    }
    
    /**
     * Preloads stories in the background to reduce loading times
     */
    private static void preloadStories() {
        new Thread(() -> {
            try {
                // Preload stories in a separate thread
                System.out.println("Preloading chapters...");
                
                // Load demo story
                try {
                    StoryLoader.createDemoStory();
                    System.out.println("Demo story preloaded successfully.");
                } catch (Exception e) {
                    System.err.println("Error preloading demo story: " + e.getMessage());
                }
                
                // Load chapter 2 story
                try {
                    StoryLoader.createChapter2Story();
                    System.out.println("Chapter 2 story preloaded successfully.");
                } catch (Exception e) {
                    System.err.println("Error preloading chapter 2 story: " + e.getMessage());
                    e.printStackTrace();
                }
                
                System.out.println("Preloading complete.");
            } catch (Exception e) {
                System.err.println("General error during story preloading: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}