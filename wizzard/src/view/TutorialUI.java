package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import controller.StoryLoader;
import model.Story;
import model.StoryNode;

public class TutorialUI extends JFrame {
    private Story tutorialStory;
    private StoryNode currentNode;
    private JTextArea contentArea;
    private JPanel choicesPanel;
    
    public TutorialUI() {
        setTitle("Tutoriel de Création de Scénario");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialiser le tutoriel
        tutorialStory = StoryLoader.createTutorialStory();
        currentNode = tutorialStory.getNodeById(1);
        
        // Créer le panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Zone de contenu
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane contentScroll = new JScrollPane(contentArea);
        mainPanel.add(contentScroll, BorderLayout.CENTER);
        
        // Panneau des choix
        choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        JScrollPane choicesScroll = new JScrollPane(choicesPanel);
        choicesScroll.setPreferredSize(new Dimension(300, 0));
        mainPanel.add(choicesScroll, BorderLayout.EAST);
        
        // Ajouter le panneau principal
        add(mainPanel);
        
        // Afficher le premier nœud
        displayCurrentNode();
    }
    
    private void displayCurrentNode() {
        if (currentNode != null) {
            // Afficher le contenu
            contentArea.setText(currentNode.getContent());
            
            // Effacer les choix précédents
            choicesPanel.removeAll();
            
            // Ajouter les nouveaux choix
            for (model.Choice choice : currentNode.getChoices()) {
                JButton choiceButton = new JButton(choice.getText());
                choiceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                choiceButton.setMaximumSize(new Dimension(280, 40));
                choiceButton.addActionListener(e -> makeChoice(choice));
                choicesPanel.add(choiceButton);
                choicesPanel.add(Box.createVerticalStrut(10));
            }
            
            // Si c'est un nœud final, ajouter un bouton pour fermer
            if (currentNode.isEndNode()) {
                JButton closeButton = new JButton("Terminer le Tutoriel");
                closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                closeButton.setMaximumSize(new Dimension(280, 40));
                closeButton.addActionListener(e -> dispose());
                choicesPanel.add(closeButton);
            }
            
            // Rafraîchir l'interface
            choicesPanel.revalidate();
            choicesPanel.repaint();
        }
    }
    
    private void makeChoice(model.Choice choice) {
        currentNode = tutorialStory.getNodeById(choice.getTargetNodeId());
        displayCurrentNode();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TutorialUI tutorial = new TutorialUI();
            tutorial.setVisible(true);
        });
    }
} 