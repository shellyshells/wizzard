# La Prophétie Ancienne

![Logo de la Prophétie Ancienne](assets/logo.png)

[ENGLISH VERSION](README.md)

Un jeu d'aventure narratif en Java dans lequel vous incarnez un voyant prophétique ayant reçu des visions d'une catastrophe imminente. Explorez un monde mystique, prenez des décisions qui façonneront votre destinée et utilisez vos pouvoirs pour empêcher la fin du monde.

## Table des Matières
- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Pré-requis](#pré-requis)
- [Installation](#installation)
- [Comment Jouer](#comment-jouer)
- [Types de Personnages](#types-de-personnages)
- [Système de Combat](#système-de-combat)
- [Mécaniques](#mécaniques)
- [Structure du Projet](#structure-du-projet)
- [Développement](#développement)
- [Crédits](#crédits)

## Aperçu

La Prophétie Ancienne est un jeu narratif interactif développé en Java en suivant l'architecture MVC (Modèle-Vue-Contrôleur). Les joueurs incarnent un prophète, un voyant, un oracle ou un érudit qui doit parcourir un monde richement détaillé pour empêcher une catastrophe annoncée.

Le jeu propose une narration à embranchements avec plusieurs fins possibles, un système de personnalisation de personnage approfondi, une gestion d'inventaire, des combats contre diverses entités mystiques, ainsi que des mécaniques de visions prophétiques qui offrent un aperçu des événements à venir.

## Fonctionnalités

- **Narration immersive** : Vivez une histoire riche à embranchements avec de multiples chemins et fins
- **Personnalisation de personnage** : Choisissez parmi différents types de personnages, chacun ayant des capacités uniques
- **Système d'attributs** : Des traits comme la sagesse, l'intuition, le charisme, la perception et la connaissance influencent le gameplay
- **Gestion d'inventaire** : Collectez et utilisez des objets mystiques qui vous aideront dans votre quête
- **Combat dynamique** : Affrontez tactiquement diverses entités mystiques
- **Visions prophétiques** : Recevez et interprétez des visions qui guideront votre quête
- **Structure en chapitres** : Progressez à travers plusieurs chapitres d'une histoire évolutive
- **Système de sauvegarde/chargement** : Sauvegardez votre progression et reprenez votre aventure plus tard
- **Interface graphique complète** : Profitez d'une interface visuellement attrayante avec images de fond et éléments graphiques thématiques

## Pré-requis

- Kit de Développement Java (JDK) 21 ou plus récent
- Résolution d'écran minimale de 1024x768
- 4 Go de RAM recommandés
- 100 Mo d'espace disque

## Installation

1. Clonez le dépot:
```bash
git clone https://github.com/yourusername/ancient-prophecy.git
```

2. Naviguez vers le dossier source:
```bash
cd ancient-prophecy
cd src
```

3. Lancez le jeu:
```bash
java Main.java
```
Vous pouvez également utiliser un IDE comme IntelliJ IDEA ou Eclipse pour importer et exécuter le projet.

## Comment Jouer

1. **Démarrer le jeu** : Lancez l'application et cliquez sur "Commencer votre voyage" dans le menu principal.
2. **Choisir un chapitre** : Sélectionnez le chapitre de l'histoire que vous souhaitez jouer.
3. **Créer votre personnage** : Entrez votre nom et choisissez votre type de personnage (Prophète, Voyant, Oracle ou Érudit).
4. **Naviguer dans l'histoire** : Lisez la narration et faites des choix qui détermineront votre parcours.
5. **Gérer votre inventaire** : Collectez et utilisez des objets pour vous aider dans votre quête.
6. **Participer aux combats** : Lors de rencontres avec des entités hostiles, utilisez vos capacités de combat de manière stratégique.
7. **Interpréter les visions** : Tout au long de votre aventure, vous recevrez des visions prophétiques qui vous donneront un aperçu des événements futurs.
8. **Sauvegarder votre progression** : Utilisez la fonction de sauvegarde pour préserver votre aventure et la reprendre plus tard.

### Contrôles

- **Souris** : Cliquez sur les choix, boutons et options du menu
- **ECHAP** : Quitter le plein écran/le jeu (une confirmation sera demandée)

## Types de Personnages

Le jeu propose quatre types de personnages distincts, chacun possédant des attributs et des capacités uniques :

- **Prophète** : Doué de visions prophétiques claires et d'un discours persuasif. Un leader naturel dont les paroles influencent aussi bien le peuple que les puissants. (Bonus en Sagesse et Charisme)

- **Voyant** : Bénéficie d'une intuition et d'une perception extraordinaires. Peut ressentir ce que les autres ne perçoivent pas et reçoit souvent des visions spontanées de futurs possibles. (Bonus en Intuition et Perception)

- **Oracle** : Allie dons prophétiques et savoir ancien. Formé aux rituels et traditions des ordres mystiques, il a le pouvoir d'interpréter les signes. (Bonus en Sagesse, Intuition et Charisme)

- **Érudit** : Maître des textes anciens et des savoirs oubliés. Ce qu'il lui manque en capacités prophétiques, il le compense par une vaste connaissance de l'histoire et des rituels. (Bonus en Connaissance et Sagesse)

## Système de Combat

La Prophétie Ancienne propose un système de combat tactique où les personnages affrontent diverses entités mystiques :
### Actions de Combat

- **Attaque Rapide** : Une frappe rapide avec une grande précision mais des dégâts modérés
- **Frappe Concentrée** : Une attaque puissante infligeant plus de dégâts mais avec une précision réduite
- **Posture Défensive** : Réduit de 50 % les dégâts de la prochaine attaque ennemie
- **Explosion Mystique** : Une attaque magique disponible pour les personnages ayant une grande sagesse
- **Intuition Prophétique** : Utilisez vos capacités prophétiques pour anticiper et contrer les mouvements ennemis
- **Approche Charismatique** : Utilisez votre charme pour déstabiliser et distraire vos adversaires
- **Frappe de Connaissance** : Exploitez votre savoir pour viser les faiblesses de l'ennemi
- **Contre Perceptif** : Utilisez votre perception aiguë pour effectuer un contre efficace

Les attributs du personnage influencent l’efficacité au combat — la Perception améliore la précision, la Sagesse et l’Intuition renforcent les attaques mystiques, et l’Énergie représente votre santé spirituelle et mentale en combat.

## Mécaniques

### Attributs

Les personnages possèdent six attributs principaux qui influencent le gameplay :
- **Sagesse** : Compréhension spirituelle et capacité à prendre de bonnes décisions
- **Intuition** : Capacité naturelle à percevoir la vérité sans raisonnement conscient
- **Charisme** : Capacité à persuader et influencer les autres
- **Perception** : Sensibilité aux détails subtils et aux changements
- **Connaissance** : Savoir accumulé et compréhension des textes anciens
- **Énergie** : Réserves spirituelles et mentales pour les visions prophétiques et les combats

### Objets

Au cours de votre aventure, vous collecterez divers objets mystiques offrant différents avantages :
- **Objets de combat** : Améliorent les capacités offensives
- **Objets prophétiques** : Améliorent la clarté des visions et leur interprétation
- **Objets de soin** : Restaure l’énergie et élimine les effets négatifs
- **Objets de protection** : Protègent des dangers et influences néfastes

### Rencontres

Il existe deux types de rencontres avec des entités :
1. **Rencontres Sociales** : Se résolvent par le dialogue et la persuasion à l’aide de vos attributs
2. **Rencontres de Combat** : Confrontations directes utilisant le système de combat

### Visions

Les visions prophétiques offrent un aperçu des événements futurs ou des vérités cachées. Leur interprétation correcte est cruciale pour réussir. Certaines visions sont claires, tandis que d'autres sont symboliques ou métaphoriques.

## Structure du Projet

Le projet suit l’architecture Modèle-Vue-Contrôleur (MVC) :
```
src/
├── Main.java                     # Point d'entrée
├── Assets/                       # Ressources (images, polices)
│   ├── Fonts/                    # Polices personnalisées
│   └── Images/                   # Images d'arrière-plan et de l'interface utilisateur
├── controller/                   # Contrôleurs
│   ├── GameController.java       # Contrôleur principal du jeu
│   ├── ProgressTracker.java      # Suivi de la progression de l’histoire
│   ├── StoryLoader.java          # Chargement du contenu narratif
│   └── TutorialStory.java        # Contenu de l’histoire tutoriel
├── model/                        # Modèles de données
│   ├── Character.java            # Personnage du joueur
│   ├── Choice.java               # Choix dans l’histoire
│   ├── CombatSystem.java         # Mécaniques de combat
│   ├── Encounter.java            # Rencontres avec des entités
│   ├── Entity.java               # PNJ et ennemis
│   ├── Inventory.java            # Gestion des objets
│   ├── Story.java                # Narration complète
│   └── StoryNode.java            # Segments individuels de l’histoire
└── view/                         # Interfaces utilisateur
    ├── CharacterPortrait.java    # Visualisation du personnage
    ├── CombatScreen.java         # Interface de combat
    ├── EndingDialog.java         # Affichage de la fin de l’histoire
    ├── HelpWindow.java           # Instructions du jeu
    ├── InventoryScreen.java      # Gestion de l’inventaire
    ├── MainMenuUI.java           # Menu principal
    ├── MapScreen.java            # Visualisation de la carte de l’histoire
    ├── ProphecyUI.java           # Interface principale du jeu
    ├── StoryEditorUI.java        # Outil de création de scénario
    ├── TutorialUI.java           # Interface du tutoriel
    └── VisionDialog.java         # Affichage des visions
```

## Développement

La Prophétie Ancienne a été développée en utilisant plusieurs approches modernes de développement :

### Création de l’Histoire

Le jeu inclut un outil intégré nommé Éditeur de Scénario (Story Editor) qui vous permet de créer et modifier le contenu narratif. Pour y accéder :

1. Lancez le jeu
2. Cliquez sur "Éditeur de Scénario" depuis le menu principal
3. Utilisez l’éditeur pour créer de nouveaux nœuds narratifs, choix et entités

### Ajouter de Nouveaux Chapitres

Pour ajouter un nouveau chapitre au jeu :

1. Créez une nouvelle histoire à l’aide de l’Éditeur de Scénario ou par programmation
2. Exportez l’histoire ou ajoutez-la dans la classe `StoryLoader`
3. Mettez à jour l’interface de sélection de chapitre pour inclure votre nouveau contenu

### Fonctionnalités Prévue

- Nouveaux types de personnages
- Options de combat plus variées
- Système d’inventaire étendu
- Effets visuels améliorés pour les visions
- Création et partage d’histoires en multijoueur

## Crédits

La Prophétie Ancienne a été créée dans le cadre d’un projet éducatif.

- Développé en Java avec Swing pour l’interface utilisateur
- Types de personnages et scénario inspirés des récits prophétiques classiques
- Illustrations et éléments graphiques originaux

---

*© 2025 - L’Équipe de La Prophétie Ancienne*
