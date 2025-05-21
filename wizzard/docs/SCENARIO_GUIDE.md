# Guide de Création de Scénarios - The Ancient Prophecy

## Table des Matières
1. [Structure de Base](#structure-de-base)
2. [Création de Nœuds](#création-de-nœuds)
3. [Gestion des Choix](#gestion-des-choix)
4. [Système de Combat](#système-de-combat)
5. [Bonnes Pratiques](#bonnes-pratiques)

## Structure de Base

Un scénario est composé de plusieurs éléments :
- Un titre et une description
- Des nœuds (points de l'histoire)
- Des choix qui relient les nœuds
- Des entités (personnages, créatures)

### Exemple de Structure
```
Chapitre 1
├── Nœud 1 (Introduction)
│   ├── Choix 1 → Nœud 2
│   └── Choix 2 → Nœud 3
├── Nœud 2 (Combat)
│   ├── Entité: Shadow Warrior
│   └── Choix 1 → Nœud 4
└── Nœud 3 (Dialogue)
    └── Choix 1 → Nœud 4
```

## Création de Nœuds

### Types de Nœuds
1. **Nœud Standard**
   - Contient du texte narratif
   - Peut avoir des choix
   - Peut avoir une entité

2. **Nœud de Combat**
   - Contient une entité avec `isCombatEntity = true`
   - Définit les statistiques de combat
   - Peut avoir des choix conditionnels

3. **Nœud Final**
   - Marqué comme `isEndNode = true`
   - Termine une branche de l'histoire

### Structure d'un Nœud
```java
StoryNode node = new StoryNode(
    id,           // Identifiant unique
    "Titre",      // Titre du nœud
    "Contenu",    // Texte narratif
    isEndNode     // Si c'est un nœud final
);
```

## Gestion des Choix

### Types de Choix
1. **Choix Simple**
   ```java
   new Choice("Texte du choix", targetNodeId);
   ```

2. **Choix avec Compétence Requise**
   ```java
   new Choice("Texte du choix", targetNodeId, "NomCompétence");
   ```

3. **Choix avec Objet Requis**
   ```java
   new Choice("Texte du choix", targetNodeId, "NomObjet");
   ```

### Bonnes Pratiques pour les Choix
- Chaque nœud devrait avoir au moins 2 choix
- Les choix devraient être clairs et distincts
- Évitez les choix qui mènent au même nœud
- Utilisez des compétences requises pour des chemins alternatifs

## Système de Combat

### Création d'Entités
```java
Entity entity = new Entity(
    "Nom",        // Nom de l'entité
    health,       // Points de vie
    damage,       // Dégâts
    "Description",// Description
    "Type"        // Type (physical, mystical, shadow)
);
```

### Types d'Entités
1. **Physical**
   - Fortes en combat direct
   - Résistance aux attaques physiques
   - Capacités : brutal_strike, defensive_stance

2. **Mystical**
   - Fortes en magie
   - Résistance aux attaques mystiques
   - Capacités : arcane_shield, energy_blast

3. **Shadow**
   - Fortes en furtivité
   - Résistance aux attaques d'ombre
   - Capacités : shadow_step, dark_embrace

## Bonnes Pratiques

### Structure de l'Histoire
1. **Introduction**
   - Présente le contexte
   - Établit les enjeux
   - Introduit les personnages principaux

2. **Développement**
   - Crée des tensions
   - Offre des choix significatifs
   - Développe les personnages

3. **Conclusion**
   - Résout les conflits
   - Offre des fins satisfaisantes
   - Récompense les choix du joueur

### Conseils Généraux
1. **Équilibrage**
   - Variez les types de nœuds
   - Mélangez combat et dialogue
   - Offrez des récompenses équilibrées

2. **Cohérence**
   - Maintenez la cohérence de l'histoire
   - Respectez les règles établies
   - Gardez une progression logique

3. **Engagement**
   - Créez des choix significatifs
   - Développez des personnages mémorables
   - Maintenez un rythme dynamique

### Exemple de Scénario Complet
```java
// Création du scénario
Story story = new Story("Le Temple Oublié", "Une ancienne prophétie vous mène vers un temple mystérieux...");

// Nœud d'introduction
StoryNode intro = new StoryNode(1, "L'Entrée du Temple", 
    "Vous vous tenez devant l'entrée du temple. Des symboles anciens ornent le portail...");

// Nœud de combat
Entity guardian = new Entity("Gardien du Temple", 10, 5, 
    "Une créature de pierre animée par la magie ancienne.", "physical");
guardian.setCombatEntity(true);
StoryNode combat = new StoryNode(2, "Le Gardien", 
    "Le gardien du temple s'anime et se dresse devant vous...");
combat.setEntity(guardian);

// Ajout des choix
intro.addChoice(new Choice("Examiner les symboles", 3, "knowledge"));
intro.addChoice(new Choice("Forcer l'entrée", 2));

// Ajout des nœuds au scénario
story.addNode(intro);
story.addNode(combat);
```

## Utilisation de l'Éditeur

1. **Chargement d'un Chapitre**
   - Sélectionnez le chapitre dans le menu déroulant
   - Modifiez les nœuds existants
   - Ajoutez de nouveaux nœuds

2. **Création d'un Nouveau Chapitre**
   - Cliquez sur "New Chapter"
   - Créez vos nœuds et choix
   - Exportez vers StoryLoader

3. **Exportation**
   - Cliquez sur "Export to StoryLoader"
   - Donnez un nom au chapitre
   - Le code sera généré automatiquement

## Dépannage

### Problèmes Courants
1. **Choix sans Destination**
   - Vérifiez que chaque choix a un nœud cible valide
   - Assurez-vous que les IDs des nœuds sont corrects

2. **Entités de Combat**
   - N'oubliez pas de définir `isCombatEntity = true`
   - Vérifiez les statistiques de combat

3. **Nœuds Inaccessibles**
   - Vérifiez qu'il existe un chemin vers chaque nœud
   - Testez tous les chemins possibles

### Conseils de Débogage
1. Utilisez l'arbre de navigation pour vérifier la structure
2. Testez chaque choix et combat
3. Vérifiez la cohérence des IDs
4. Sauvegardez régulièrement votre travail 