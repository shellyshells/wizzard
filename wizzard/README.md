# The Ancient Prophecy

![Ancient Prophecy Logo](assets/logo.png)

[VERSION FRANCAISE](README_fr.md)

A narrative-driven Java adventure game where you play as a prophetic seer who has received visions of an impending catastrophe. Navigate through a mystical world, make decisions that shape your destiny, and use your powers to prevent the approaching doom.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Character Types](#character-types)
- [Combat System](#combat-system)
- [Mechanics](#mechanics)
- [Project Structure](#project-structure)
- [Development](#development)
- [Credits](#credits)

## Overview

The Ancient Prophecy is an interactive narrative game built in Java using the MVC (Model-View-Controller) architecture. Players take on the role of a prophet, seer, oracle, or scholar who must navigate through a richly detailed world to prevent a coming catastrophe.

The game features a branching narrative with multiple endings, a deep character customization system, inventory management, combat with various entities, and prophetic vision mechanics that provide insight into future events.

## Features

- **Immersive Narrative**: Experience a deep, branching story with multiple paths and endings
- **Character Customization**: Choose from different character types, each with unique abilities
- **Attribute System**: Character traits like wisdom, intuition, charisma, perception, and knowledge influence gameplay
- **Inventory Management**: Collect and use mystical items that aid in your journey
- **Dynamic Combat**: Engage in tactical combat against various mystical entities
- **Prophetic Visions**: Receive and interpret visions that guide your quest
- **Chapter Structure**: Progress through multiple chapters of an evolving story
- **Save/Load System**: Save your progress and continue your journey later
- **Full GUI Interface**: Enjoy a visually appealing interface with background images and themed UI elements

## Requirements

- Java Development Kit (JDK) 21 or newer
- Minimum screen resolution of 1024x768
- 4GB RAM recommended
- 100MB disk space

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/ancient-prophecy.git
```

2. Navigate to the project directory:
```bash
cd ancient-prophecy
cd src
```

3. Run the game:
```bash
java Main.java
```

Alternatively, you can use an IDE like IntelliJ IDEA or Eclipse to import and run the project.

## How to Play

1. **Start the game**: Launch the application and click "Begin Your Journey" on the main menu.
2. **Choose a chapter**: Select which chapter of the story to play.
3. **Create your character**: Enter your name and select your character type (Prophet, Seer, Oracle, or Scholar).
4. **Navigate the story**: Read the narrative and make choices that will determine your path.
5. **Manage your inventory**: Collect and use items to aid in your journey.
6. **Engage in combat**: When confronted by hostile entities, use your combat abilities strategically.
7. **Interpret visions**: Throughout your journey, you'll receive prophetic visions that provide insight into future events.
8. **Save your progress**: Use the save feature to preserve your journey and continue later.

### Controls

- **Mouse**: Click on choices, buttons, and menu options
- **ESC**: Exit fullscreen/game (will prompt for confirmation)

## Character Types

The game offers four distinct character types, each with unique attributes and abilities:

- **Prophet**: Gifted with clear prophetic visions and persuasive speech. A natural leader whose words carry weight with both the common folk and those in power. (Bonus to Wisdom and Charisma)

- **Seer**: Blessed with extraordinary intuition and perception. Can sense things others cannot and often receives spontaneous visions of possible futures. (Bonus to Intuition and Perception)

- **Oracle**: Combines prophetic gifts with ancient knowledge. Trained in the rituals and traditions of the mystical orders, with power to interpret signs. (Bonus to Wisdom, Intuition, and Charisma)

- **Scholar**: A master of ancient texts and forgotten lore. What they lack in natural prophetic ability, they make up for with vast knowledge of history and ritual. (Bonus to Knowledge and Wisdom)

## Combat System

The Ancient Prophecy features a tactical combat system where characters face off against various mystical entities:

### Combat Actions

- **Quick Attack**: A swift strike with high accuracy but moderate damage
- **Focused Strike**: A powerful attack with higher damage but lower accuracy
- **Defensive Stance**: Reduces damage from the next enemy attack by 50%
- **Mystic Blast**: A magical attack available to characters with high wisdom
- **Prophetic Insight**: Use prophetic abilities to predict and counter enemy moves
- **Charismatic Approach**: Use charm to confuse and distract opponents
- **Knowledge Strike**: Exploit knowledge of enemy weaknesses
- **Perceptive Counter**: Use keen perception to counter-attack effectively

Character attributes influence combat effectiveness - Perception improves accuracy, Wisdom and Intuition enhance mystical attacks, and Energy represents your combat health.

## Mechanics

### Attributes

Characters have six primary attributes that influence gameplay:
- **Wisdom**: Spiritual understanding and ability to make good judgments
- **Intuition**: Natural ability to perceive truth without conscious reasoning
- **Charisma**: Persuasiveness and ability to influence others
- **Perception**: Awareness of subtle details and changes
- **Knowledge**: Accumulated learning and understanding of ancient texts
- **Energy**: Spiritual and mental reserves for prophetic work and combat

### Items

Throughout your journey, you'll collect various mystical items that provide different benefits:
- **Combat items**: Enhance combat abilities
- **Prophetic items**: Improve vision clarity and interpretation
- **Healing items**: Restore energy and remove negative effects
- **Protective items**: Shield from harm and negative influences

### Encounters

There are two types of encounters with entities:
1. **Social Encounters**: Resolve through dialogue and persuasion using your attributes
2. **Combat Encounters**: Direct confrontations using the combat system

### Visions

Prophetic visions provide insight into future events or hidden truths. Interpreting them correctly is crucial for success. Some visions are straightforward, while others are symbolic or metaphorical.

## Project Structure

The project follows the Model-View-Controller (MVC) architecture:

```
src/
├── Main.java                     # Entry point
├── Assets/                       # Resources (images, fonts)
│   ├── Fonts/                    # Custom fonts
│   └── Images/                   # Background and UI images
├── controller/                   # Controllers
│   ├── GameController.java       # Main game controller
│   ├── ProgressTracker.java      # Tracks story progress
│   ├── StoryLoader.java          # Loads story content
│   └── TutorialStory.java        # Tutorial story content
├── model/                        # Data models
│   ├── Character.java            # Player character
│   ├── Choice.java               # Story choices
│   ├── CombatSystem.java         # Combat mechanics
│   ├── Encounter.java            # Entity encounters
│   ├── Entity.java               # NPCs and enemies
│   ├── Inventory.java            # Item management
│   ├── Story.java                # Complete narrative
│   └── StoryNode.java            # Individual story segments
└── view/                         # User interfaces
    ├── CharacterPortrait.java    # Character visualization
    ├── CombatScreen.java         # Combat interface
    ├── EndingDialog.java         # Story ending display
    ├── HelpWindow.java           # Game instructions
    ├── InventoryScreen.java      # Inventory management
    ├── MainMenuUI.java           # Main menu
    ├── MapScreen.java            # Story map visualization
    ├── ProphecyUI.java           # Main game interface
    ├── StoryEditorUI.java        # Story creation tool
    ├── TutorialUI.java           # Tutorial interface
    └── VisionDialog.java         # Vision display
```

## Development

The Ancient Prophecy was developed using a number of modern development approaches:

### Story Creation

The game includes a built-in Story Editor tool that allows you to create and modify story content. To access it:

1. Launch the game
2. Click "Story Editor" from the main menu
3. Use the editor to create new story nodes, choices, and entities

### Adding New Chapters

To add a new chapter to the game:

1. Create a new Story using the StoryEditor or programmatically
2. Export the story or add it to the StoryLoader class
3. Update the chapter selection UI to include your new chapter

### Planned Features

- Additional character types
- More diverse combat options
- Expanded inventory system
- Enhanced visual effects for visions
- Multiplayer story creation and sharing

## Credits

The Ancient Prophecy was created as part of an educational project.

- Developed in Java using Swing for the user interface
- Character types and story inspired by classic prophecy narratives
- Original artwork and design elements

---

*© 2025 - The Ancient Prophecy Team*

