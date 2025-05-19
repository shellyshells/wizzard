// ProgressTracker.java
package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks player progress through the story
 */
public class ProgressTracker {
    private static final String PROGRESS_FILE = "story_progress.dat";
    private Map<Integer, Boolean> completedChapters;
    private Map<String, Integer> discoveredSecrets;
    private Map<String, Boolean> achievements;

    /**
     * Constructor
     */
    public ProgressTracker() {
        completedChapters = new HashMap<>();
        discoveredSecrets = new HashMap<>();
        achievements = new HashMap<>();
        loadProgress();
    }

    /**
     * Marks a chapter as completed
     * 
     * @param chapterId The chapter ID
     */
    public void markChapterCompleted(int chapterId) {
        completedChapters.put(chapterId, true);
        saveProgress();
    }

    /**
     * Checks if a chapter is completed
     * 
     * @param chapterId The chapter ID
     * @return true if the chapter is completed
     */
    public boolean isChapterCompleted(int chapterId) {
        return completedChapters.getOrDefault(chapterId, false);
    }
    
    /**
     * Records that a secret has been discovered
     * 
     * @param secretId The secret's identifier
     * @param value The value or count to record
     */
    public void recordSecret(String secretId, int value) {
        discoveredSecrets.put(secretId, value);
        
        // Check for achievement unlocks based on secrets
        if (secretId.equals("ancient_runes") && value >= 5) {
            unlockAchievement("rune_master");
        } else if (secretId.equals("visions") && value >= 3) {
            unlockAchievement("true_prophet");
        }
        
        saveProgress();
    }
    
    /**
     * Unlocks an achievement
     * 
     * @param achievementId The achievement identifier
     */
    public void unlockAchievement(String achievementId) {
        if (!hasAchievement(achievementId)) {
            achievements.put(achievementId, true);
            saveProgress();
            
            // Achievement notifications would be triggered here
            System.out.println("Achievement unlocked: " + getAchievementName(achievementId));
        }
    }
    
    /**
     * Gets a friendly name for an achievement
     */
    private String getAchievementName(String achievementId) {
        switch (achievementId) {
            case "rune_master": return "Rune Master - Discovered all ancient runes";
            case "true_prophet": return "True Prophet - Experienced multiple prophetic visions";
            case "shadow_walker": return "Shadow Walker - Survived an encounter with the Harbinger";
            case "crystal_bearer": return "Crystal Bearer - Obtained the Crystal of Truth";
            default: return achievementId;
        }
    }
    
    /**
     * Checks if the player has an achievement
     */
    public boolean hasAchievement(String achievementId) {
        return achievements.getOrDefault(achievementId, false);
    }

    /**
     * Saves the progress to a file
     */
    private void saveProgress() {
        try (FileWriter writer = new FileWriter(PROGRESS_FILE)) {
            // Save completed chapters
            for (Map.Entry<Integer, Boolean> entry : completedChapters.entrySet()) {
                writer.write("chapter:" + entry.getKey() + ":" + entry.getValue() + "\n");
            }
            
            // Save discovered secrets
            for (Map.Entry<String, Integer> entry : discoveredSecrets.entrySet()) {
                writer.write("secret:" + entry.getKey() + ":" + entry.getValue() + "\n");
            }
            
            // Save achievements
            for (Map.Entry<String, Boolean> entry : achievements.entrySet()) {
                writer.write("achievement:" + entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving progress: " + e.getMessage());
        }
    }

    /**
     * Loads the progress from a file
     */
    private void loadProgress() {
        try {
            File file = new File(PROGRESS_FILE);
            if (file.exists()) {
                Files.readAllLines(Paths.get(PROGRESS_FILE)).forEach(line -> {
                    String[] parts = line.split(":");
                    if (parts.length >= 3) {
                        try {
                            if (parts[0].equals("chapter")) {
                                int chapterId = Integer.parseInt(parts[1]);
                                boolean completed = Boolean.parseBoolean(parts[2]);
                                completedChapters.put(chapterId, completed);
                            } else if (parts[0].equals("secret")) {
                                discoveredSecrets.put(parts[1], Integer.parseInt(parts[2]));
                            } else if (parts[0].equals("achievement")) {
                                achievements.put(parts[1], Boolean.parseBoolean(parts[2]));
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid format in progress file");
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Error loading progress: " + e.getMessage());
        }
    }

    /**
     * Resets all progress
     */
    public void resetProgress() {
        completedChapters.clear();
        discoveredSecrets.clear();
        achievements.clear();
        saveProgress();
    }
}