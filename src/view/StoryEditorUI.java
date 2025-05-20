package view;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import model.*;
import controller.StoryLoader;

public class StoryEditorUI extends JFrame {
    private Story currentStory;
    private JTree storyTree;
    private JPanel editorPanel;
    private JTextArea nodeTextArea;
    private JList<model.Choice> choicesList;
    private JList<Entity> entitiesList;
    private StoryNode selectedNode;
    private JComboBox<String> chapterSelector;
    
    public StoryEditorUI() {
        setTitle("Story Editor");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create main split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left panel - Story tree
        JPanel leftPanel = new JPanel(new BorderLayout());
        
        // Add chapter selector
        chapterSelector = new JComboBox<>(new String[]{"Chapter 1", "Chapter 2", "New Chapter"});
        chapterSelector.addActionListener(e -> loadSelectedChapter());
        leftPanel.add(chapterSelector, BorderLayout.NORTH);
        
        storyTree = new JTree();
        storyTree.setRootVisible(false);
        storyTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) storyTree.getLastSelectedPathComponent();
            if (node != null && node.getUserObject() instanceof StoryNode) {
                selectedNode = (StoryNode) node.getUserObject();
                updateEditorPanel();
            }
        });
        JScrollPane treeScroll = new JScrollPane(storyTree);
        leftPanel.add(treeScroll, BorderLayout.CENTER);
        
        // Right panel - Editor
        editorPanel = new JPanel(new BorderLayout());
        
        // Node editor
        JPanel nodeEditor = new JPanel(new BorderLayout());
        nodeTextArea = new JTextArea();
        nodeTextArea.setLineWrap(true);
        nodeTextArea.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(nodeTextArea);
        nodeEditor.add(new JLabel("Node Content:"), BorderLayout.NORTH);
        nodeEditor.add(textScroll, BorderLayout.CENTER);
        
        // Add save button for node content
        JButton saveNodeBtn = new JButton("Save Node Content");
        saveNodeBtn.addActionListener(e -> saveNodeContent());
        nodeEditor.add(saveNodeBtn, BorderLayout.SOUTH);
        
        // Choices editor
        JPanel choicesEditor = new JPanel(new BorderLayout());
        choicesList = new JList<>();
        JScrollPane choicesScroll = new JScrollPane(choicesList);
        choicesEditor.add(new JLabel("Choices:"), BorderLayout.NORTH);
        choicesEditor.add(choicesScroll, BorderLayout.CENTER);
        
        // Entities editor
        JPanel entitiesEditor = new JPanel(new BorderLayout());
        entitiesList = new JList<>();
        JScrollPane entitiesScroll = new JScrollPane(entitiesList);
        entitiesEditor.add(new JLabel("Entities:"), BorderLayout.NORTH);
        entitiesEditor.add(entitiesScroll, BorderLayout.CENTER);
        
        // Combine editors
        JSplitPane editorsSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, nodeEditor, choicesEditor);
        JSplitPane finalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorsSplit, entitiesEditor);
        editorPanel.add(finalSplit, BorderLayout.CENTER);
        
        // Add panels to main split
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(editorPanel);
        splitPane.setDividerLocation(300);
        
        // Add toolbar
        JToolBar toolBar = createToolBar();
        add(toolBar, BorderLayout.NORTH);
        
        // Add main split pane
        add(splitPane);
        
        // Initialize with empty story
        currentStory = new Story("New Story", "Enter story description here");
        updateStoryTree();
    }
    
    private void loadSelectedChapter() {
        String selected = (String) chapterSelector.getSelectedItem();
        if (selected != null) {
            switch (selected) {
                case "Chapter 1":
                    currentStory = StoryLoader.createDemoStory();
                    break;
                case "Chapter 2":
                    currentStory = StoryLoader.createChapter2Story();
                    break;
                case "New Chapter":
                    createNewStory();
                    break;
            }
            updateStoryTree();
        }
    }
    
    private void saveNodeContent() {
        if (selectedNode != null) {
            selectedNode.setContent(nodeTextArea.getText());
            JOptionPane.showMessageDialog(this, "Node content saved!");
        }
    }
    
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton newStoryBtn = new JButton("New Story");
        JButton saveStoryBtn = new JButton("Save Story");
        JButton loadStoryBtn = new JButton("Load Story");
        JButton addNodeBtn = new JButton("Add Node");
        JButton addChoiceBtn = new JButton("Add Choice");
        JButton addEntityBtn = new JButton("Add Entity");
        JButton exportStoryBtn = new JButton("Export to StoryLoader");
        
        toolBar.add(newStoryBtn);
        toolBar.add(saveStoryBtn);
        toolBar.add(loadStoryBtn);
        toolBar.addSeparator();
        toolBar.add(addNodeBtn);
        toolBar.add(addChoiceBtn);
        toolBar.add(addEntityBtn);
        toolBar.addSeparator();
        toolBar.add(exportStoryBtn);
        
        // Add action listeners
        newStoryBtn.addActionListener(e -> createNewStory());
        saveStoryBtn.addActionListener(e -> saveStory());
        loadStoryBtn.addActionListener(e -> loadStory());
        addNodeBtn.addActionListener(e -> addNewNode());
        addChoiceBtn.addActionListener(e -> addNewChoice());
        addEntityBtn.addActionListener(e -> addNewEntity());
        exportStoryBtn.addActionListener(e -> exportToStoryLoader());
        
        return toolBar;
    }
    
    private void exportToStoryLoader() {
        String chapterName = JOptionPane.showInputDialog(this, 
            "Enter chapter name (e.g., Chapter3):");
        if (chapterName != null && !chapterName.trim().isEmpty()) {
            try {
                // Create a new file for the chapter
                File storyFile = new File("src/controller/" + chapterName + ".java");
                if (storyFile.exists()) {
                    int result = JOptionPane.showConfirmDialog(this,
                        "File already exists. Overwrite?",
                        "Confirm Overwrite",
                        JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                // Generate the Java code
                StringBuilder code = new StringBuilder();
                code.append("package controller;\n\n");
                code.append("import model.*;\n\n");
                code.append("public class ").append(chapterName).append(" {\n");
                code.append("    public static Story createStory() {\n");
                code.append("        Story story = new Story(\"").append(currentStory.getTitle()).append("\", \"")
                    .append(currentStory.getDescription()).append("\");\n\n");
                
                // Add nodes
                for (StoryNode node : currentStory.getNodes().values()) {
                    code.append("        // Node ").append(node.getId()).append("\n");
                    code.append("        StoryNode node").append(node.getId()).append(" = new StoryNode(")
                        .append(node.getId()).append(", \"").append(node.getTitle()).append("\", \"")
                        .append(node.getContent().replace("\"", "\\\"")).append("\"")
                        .append(node.isEndNode() ? ", true" : "").append(");\n");
                    
                    // Add choices
                    for (model.Choice choice : node.getChoices()) {
                        code.append("        node").append(node.getId()).append(".addChoice(new Choice(\"")
                            .append(choice.getText().replace("\"", "\\\"")).append("\", ")
                            .append(choice.getTargetNodeId()).append("));\n");
                    }
                    
                    // Add entity if present
                    if (node.getEntity() != null) {
                        Entity entity = node.getEntity();
                        code.append("        Entity entity").append(node.getId()).append(" = new Entity(\"")
                            .append(entity.getName()).append("\", ").append(entity.getHealth())
                            .append(", ").append(entity.getDamage()).append(", \"")
                            .append(entity.getDescription().replace("\"", "\\\"")).append("\", \"")
                            .append(entity.getType()).append("\");\n");
                        code.append("        node").append(node.getId()).append(".setEntity(entity")
                            .append(node.getId()).append(");\n");
                    }
                    
                    code.append("        story.addNode(node").append(node.getId()).append(");\n\n");
                }
                
                code.append("        return story;\n");
                code.append("    }\n");
                code.append("}\n");
                
                // Write the file
                try (FileWriter writer = new FileWriter(storyFile)) {
                    writer.write(code.toString());
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Story exported successfully to " + storyFile.getAbsolutePath());
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error exporting story: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createNewStory() {
        String title = JOptionPane.showInputDialog(this, "Enter story title:");
        if (title != null && !title.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter story description:");
            currentStory = new Story(title, description);
            updateStoryTree();
        }
    }
    
    private void saveStory() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fileChooser.getSelectedFile()))) {
                oos.writeObject(currentStory);
                JOptionPane.showMessageDialog(this, "Story saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving story: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadStory() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileChooser.getSelectedFile()))) {
                currentStory = (Story) ois.readObject();
                updateStoryTree();
                JOptionPane.showMessageDialog(this, "Story loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading story: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addNewNode() {
        String title = JOptionPane.showInputDialog(this, "Enter node title:");
        if (title != null && !title.trim().isEmpty()) {
            String content = JOptionPane.showInputDialog(this, "Enter node content:");
            if (content != null) {
                int id = currentStory.getNodes().size() + 1;
                StoryNode node = new StoryNode(id, title, content);
                currentStory.addNode(node);
                updateStoryTree();
            }
        }
    }
    
    private void addNewChoice() {
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(this, "Please select a node first!");
            return;
        }
        
        String text = JOptionPane.showInputDialog(this, "Enter choice text:");
        if (text != null && !text.trim().isEmpty()) {
            String targetIdStr = JOptionPane.showInputDialog(this, "Enter target node ID:");
            try {
                int targetId = Integer.parseInt(targetIdStr);
                if (currentStory.getNodeById(targetId) != null) {
                    model.Choice choice = new model.Choice(text, targetId);
                    selectedNode.addChoice(choice);
                    updateEditorPanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Target node ID does not exist!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid target node ID!");
            }
        }
    }
    
    private void addNewEntity() {
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(this, "Please select a node first!");
            return;
        }
        
        String name = JOptionPane.showInputDialog(this, "Enter entity name:");
        if (name != null && !name.trim().isEmpty()) {
            String healthStr = JOptionPane.showInputDialog(this, "Enter entity health:");
            String damageStr = JOptionPane.showInputDialog(this, "Enter entity damage:");
            String description = JOptionPane.showInputDialog(this, "Enter entity description:");
            String type = JOptionPane.showInputDialog(this, "Enter entity type:");
            
            try {
                int health = Integer.parseInt(healthStr);
                int damage = Integer.parseInt(damageStr);
                
                Entity entity = new Entity(name, health, damage, description, type);
                selectedNode.setEntity(entity);
                updateEditorPanel();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid health or damage value!");
            }
        }
    }
    
    private void updateStoryTree() {
        DefaultTreeModel model = (DefaultTreeModel) storyTree.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(currentStory.getTitle());
        
        for (StoryNode node : currentStory.getNodes().values()) {
            DefaultMutableTreeNode nodeNode = new DefaultMutableTreeNode(node);
            root.add(nodeNode);
        }
        
        model.setRoot(root);
        model.reload();
    }
    
    private void updateEditorPanel() {
        if (selectedNode != null) {
            nodeTextArea.setText(selectedNode.getContent());
            
            DefaultListModel<model.Choice> choicesModel = new DefaultListModel<>();
            for (model.Choice choice : selectedNode.getChoices()) {
                choicesModel.addElement(choice);
            }
            choicesList.setModel(choicesModel);
            
            DefaultListModel<Entity> entitiesModel = new DefaultListModel<>();
            if (selectedNode.getEntity() != null) {
                entitiesModel.addElement(selectedNode.getEntity());
            }
            entitiesList.setModel(entitiesModel);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StoryEditorUI editor = new StoryEditorUI();
            editor.setVisible(true);
        });
    }
} 