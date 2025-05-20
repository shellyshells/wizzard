package controller;

import model.Choice;
import model.Entity;
import model.Story;
import model.StoryNode;

public class TutorialStory {
    public static Story createStory() {
        Story story = new Story("Tutoriel de Création de Scénario", 
            "Ce tutoriel vous guidera à travers les bases de la création d'un scénario.");

        // Introduction
        StoryNode intro = new StoryNode(1, "Bienvenue dans le Tutoriel",
            "Bienvenue dans le tutoriel de création de scénario !\n\n" +
            "Ce tutoriel vous montrera comment créer votre propre histoire interactive.\n" +
            "Nous allons créer ensemble un petit scénario d'exemple.\n\n" +
            "Commençons par comprendre les différents types de nœuds...");

        // Explication des nœuds
        StoryNode nodeTypes = new StoryNode(2, "Types de Nœuds",
            "Il existe trois types principaux de nœuds :\n\n" +
            "1. Nœud Standard : Contient du texte narratif et des choix\n" +
            "2. Nœud de Combat : Contient une entité à combattre\n" +
            "3. Nœud Final : Termine une branche de l'histoire\n\n" +
            "Chaque nœud a un ID unique et peut contenir des choix qui mènent à d'autres nœuds.");

        // Exemple de nœud de combat
        Entity tutorialEnemy = new Entity("Gobelin d'Entraînement", 5, 2,
            "Un gobelin faible créé spécialement pour le tutoriel.", "physical");
        tutorialEnemy.setCombatEntity(true);
        StoryNode combatExample = new StoryNode(3, "Exemple de Combat",
            "Voici un exemple de nœud de combat.\n\n" +
            "Ce gobelin a été créé avec des statistiques faibles pour le tutoriel.\n" +
            "Dans un vrai scénario, vous pourriez créer des ennemis plus puissants.");

        // Exemple de choix
        StoryNode choiceExample = new StoryNode(4, "Exemple de Choix",
            "Les choix permettent au joueur d'influencer l'histoire.\n\n" +
            "Vous pouvez créer différents types de choix :\n" +
            "- Choix simples\n" +
            "- Choix nécessitant une compétence\n" +
            "- Choix nécessitant un objet\n\n" +
            "Chaque choix mène à un nœud différent.");

        // Exemple de nœud final
        StoryNode endExample = new StoryNode(5, "Nœud Final",
            "Ceci est un exemple de nœud final.\n\n" +
            "Les nœuds finaux terminent une branche de l'histoire.\n" +
            "Ils sont marqués comme 'isEndNode = true'.\n\n" +
            "Félicitations ! Vous avez terminé le tutoriel de base.");

        // Configuration des nœuds
        combatExample.setEntity(tutorialEnemy);
        endExample.setEndNode(true);

        // Ajout des choix
        intro.addChoice(new Choice("Comprendre les types de nœuds", 2));
        nodeTypes.addChoice(new Choice("Voir un exemple de combat", 3));
        nodeTypes.addChoice(new Choice("Voir un exemple de choix", 4));
        combatExample.addChoice(new Choice("Continuer vers les choix", 4));
        choiceExample.addChoice(new Choice("Terminer le tutoriel", 5));

        // Ajout des nœuds au scénario
        story.addNode(intro);
        story.addNode(nodeTypes);
        story.addNode(combatExample);
        story.addNode(choiceExample);
        story.addNode(endExample);

        return story;
    }
} 