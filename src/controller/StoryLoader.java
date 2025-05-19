// StoryLoader.java
package controller;

import model.Choice;
import model.Entity;
import model.StoryNode;
import model.Story;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads story content from different sources
 */
public class StoryLoader {

    /**
     * Loads a story from a JSON file
     * 
     * @param filePath Path to the JSON file
     * @return A loaded story or null if error
     */
    public static Story loadFromJson(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            // In a real implementation, use a JSON library like Jackson or Gson
            // This version is a simplified simulation
            return parseStoryJson(content);
        } catch (IOException e) {
            System.err.println("Error loading story: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a demo story for "The Ancient Prophecy"
     * 
     * @return A demo story for testing
     */
    public static Story createDemoStory() {
        // Create the story
        Story story = new Story("The Ancient Prophecy", 
                "You are a prophetic seer who has received visions of an impending catastrophe.");

        // Node 1: Introduction
        StoryNode node1 = new StoryNode(1, "The First Vision", 
                "You wake with a start, your heart pounding. The vision that disturbed your sleep was more vivid than any you've experienced before. Destruction, darkness, and a looming shadow over the land. You know, with absolute certainty, that this was no ordinary dream but a prophecy of things to come.\n\n" +
                "As the last Oracle of Calderon, it's your responsibility to interpret these visions and take action. The ancient texts speak of the Prophecy of Shadows - a dark force that returns every millennium. Could this be what you've seen?\n\n" +
                "Outside your window, the peaceful village of Misthaven sits unaware of any danger. What will you do with this knowledge?");

        // Node 2: The Council
        StoryNode node2 = new StoryNode(2, "The Council of Elders", 
                "The Council chambers are quiet as you finish relating your vision. Elder Thorne, leader of the Council, looks troubled but skeptical.\n\n" +
                "\"These are serious claims,\" he says, stroking his silver beard. \"The Prophecy of Shadows is just an old legend. We need more than just a dream to take action.\"\n\n" +
                "Several other Council members nod in agreement, though a few look concerned. You sense their doubt, but also their fear. The last time someone claimed to have prophetic visions, it led to panic and unnecessary preparations.\n\n" +
                "\"If this is true,\" says Lady Selene, the only one who seems to believe you, \"we must act quickly. The ancient texts in the Temple of Seers might contain guidance.\"");

        // Node 3: The Temple
        StoryNode node3 = new StoryNode(3, "The Temple of Seers", 
                "The Temple of Seers stands on a hill overlooking Mistaven. Its ancient stone walls contain centuries of prophetic knowledge.\n\n" +
                "As you approach, you notice the door is slightly ajar - unusual for the normally sealed temple. Inside, you find signs of a disturbance: scrolls scattered across the floor, ancient artifacts displaced.\n\n" +
                "In the center of the main chamber stands Keeper Lyra, the elderly woman who has maintained the temple for decades. She looks at you with wide, fearful eyes.\n\n" +
                "\"You've seen it too, haven't you?\" she whispers. \"The shadow approaches. Someone has already been here, searching for the Codex of Prophecies.\"");

        // Node 4: The Library
        StoryNode node4 = new StoryNode(4, "The Forbidden Library", 
                "You follow Keeper Lyra through a hidden door behind the altar, descending a narrow staircase into the depths beneath the temple. This secret library contains texts too dangerous or powerful for common knowledge.\n\n" +
                "\"The Codex remains safe,\" Lyra says, leading you to an ornate chest. \"Only someone with the gift of prophecy can open it.\"\n\n" +
                "She gestures for you to place your hand on the chest's lock. As you do, a warm sensation travels up your arm, and ancient symbols on the chest begin to glow with a soft blue light. The lock clicks open.\n\n" +
                "Inside lies a single book bound in midnight-blue leather, its pages seemingly made of some otherworldly material that shifts between parchment and pure light.");

        // Node 5: The Ancient Text
        StoryNode node5 = new StoryNode(5, "Revelations of the Codex", 
                "The Codex opens at your touch, its pages illuminating the chamber with soft light. Symbols and text in an ancient language fill the pages, but somehow, you understand them.\n\n" +
                "\"When the three moons align and the shadow grows long, the Harbinger of Darkness shall return. Only the Prophet of Light, bearing the Crystal of Truth, can seal the ancient evil.\"\n\n" +
                "You read passages describing the warning signs: strange weather patterns, unusual animal behavior, and most importantly - the awakening of dormant magical sources throughout the land.\n\n" +
                "\"The alignment of the moons,\" Lyra whispers, \"it's happening in seven days.\"");

        // Node 6: Make a Decision
        StoryNode node6 = new StoryNode(6, "The Crystal's Guardian", 
                "According to the Codex, the Crystal of Truth is hidden in the Caves of Echo, guarded by an ancient entity known as the Whispering Warden.\n\n" +
                "\"The Warden tests those who seek the Crystal,\" Lyra explains. \"Many have failed. The last attempt was over a century ago.\"\n\n" +
                "You gather supplies for the journey - it will take two days to reach the caves, leaving little time to return before the alignment. As you prepare to leave, a messenger arrives with troubling news: strange phenomena have been reported throughout the region. Dark clouds gather over the mountains, and wildlife is fleeing the forests.\n\n" +
                "The signs are accelerating. Time is running out.");

        // Node 7: The Journey
        StoryNode node7 = new StoryNode(7, "The Road to Echo", 
                "The journey to the Caves of Echo takes you through the Whispering Woods, a forest known for its unusual acoustic properties. Sounds travel strangely here, sometimes echoing for minutes, sometimes disappearing entirely.\n\n" +
                "As you travel, you notice the forest is unnaturally quiet. No birds sing, no small animals rustle in the underbrush. Even the wind seems hesitant to disturb the silence.\n\n" +
                "By nightfall, you reach a small clearing. As you set up camp, you hear what sounds like distant voices carried on the breeze - unintelligible whispers that seem to surround you.");

        // Node 8: The Strange Encounter
        StoryNode node8 = new StoryNode(8, "The Mystic", 
                "You awake to find a hooded figure sitting across from the embers of your campfire. Somehow, they approached without triggering your awareness.\n\n" +
                "\"Oracle of Calderon,\" the figure says in a melodic voice. \"I have been waiting for you.\"\n\n" +
                "The stranger pushes back their hood, revealing an ageless face with eyes that seem to shift colors like a kaleidoscope. This is a Mystic - one of the rare wandering seers who exist outside the established prophetic orders.\n\n" +
                "\"The path you walk is dangerous,\" the Mystic continues. \"The Harbinger already has servants searching for the Crystal. I can help you, but you must trust me.\"");

        // Node 9: The Decision
        StoryNode node9 = new StoryNode(9, "Trust or Suspicion", 
                "The Mystic extends their hand, offering a small velvet pouch. \"This herb will strengthen your prophetic abilities. You'll need every advantage when facing the Warden.\"\n\n" +
                "You hesitate, remembering warnings about Mystics. Some are genuine allies, while others serve darker powers, using their gifts to manipulate and deceive.\n\n" +
                "\"The choice is yours,\" the Mystic says calmly. \"But know that the Harbinger's influence grows stronger with each passing hour. Those without protection will find their minds... susceptible to its whispers.\"");

        // Node 10: The Caves
        StoryNode node10 = new StoryNode(10, "The Caves of Echo", 
                "The entrance to the Caves of Echo looms before you - a massive archway of natural stone, worn smooth by the elements. Strange symbols are carved around the entrance, similar to those in the Codex.\n\n" +
                "As you step inside, the temperature drops noticeably. Your footsteps echo far longer than they should, each sound returning multiplied and distorted. The main passage branches in multiple directions, but a faint luminescence seems to guide you deeper into the mountain.\n\n" +
                "The walls themselves are unusual - embedded with tiny crystals that catch and reflect light in mesmerizing patterns. As you proceed, you begin to hear whispers, as if the cave itself is speaking.");

        // Node 11: The Warden
        StoryNode node11 = new StoryNode(11, "The Whispering Warden", 
                "The passage opens into a vast underground chamber. Stalactites hang like frozen waterfalls from the ceiling, and in the center floats a being unlike any you've ever seen.\n\n" +
                "The Whispering Warden is not quite solid, not quite vapor - a swirling mass of light and shadow that vaguely resembles a human form. Its voice seems to come from everywhere at once.\n\n" +
                "\"Seeker of the Crystal,\" it intones, the words reverberating throughout the chamber. \"Few come this far. Fewer still depart with what they seek. To prove your worth, you must pass my trial.\"");

        // Node 12: The Trial
        StoryNode node12 = new StoryNode(12, "Trial of the Mind", 
                "\"The trial is simple,\" the Warden explains, its form shifting constantly. \"I will show you three possible futures. Two are false - deceptions crafted from your own fears and hopes. One is true - a genuine prophecy of what may come.\"\n\n" +
                "\"Identify the true vision, and the Crystal is yours. Choose wrongly, and you will join the others who failed.\"\n\n" +
                "With a gesture, the Warden envelops you in a swirling mist. Your surroundings fade, and visions begin to form...");

        // Node 13: Vision One
        StoryNode node13 = new StoryNode(13, "The First Vision: Victory", 
                "You see yourself standing triumphant atop a mountain, holding the Crystal aloft. Its light pushes back a massive wall of darkness that threatens to engulf the land. People cheer your name, and you are hailed as the savior of the realm.\n\n" +
                "The vision feels good - righteous and powerful. Everything works out perfectly, exactly as you hoped.\n\n" +
                "As the scene fades, you notice something curious - your reflection in a nearby lake doesn't cast a shadow, despite the bright sun overhead.");

        // Node 14: Vision Two
        StoryNode node14 = new StoryNode(14, "The Second Vision: Sacrifice", 
                "You see yourself confronting the Harbinger - a towering figure of shadow and malice. You hold the Crystal before you, but it's not enough. The Harbinger advances, and you realize the only way to stop it is to sacrifice yourself.\n\n" +
                "You plunge the Crystal into your own chest, becoming a vessel to contain the darkness. You will exist for eternity, neither alive nor dead, forever holding the evil at bay.\n\n" +
                "The vision is terrifying but has a ring of truth in its imperfection. As it fades, you notice a small detail - a familiar constellation visible in the night sky that shouldn't be visible during the season of the moon alignment.");

        // Node 15: Vision Three
        StoryNode node15 = new StoryNode(15, "The Third Vision: Unexpected Alliance", 
                "You see yourself approaching the Harbinger, Crystal in hand. But instead of a battle, you initiate a conversation. The Harbinger reveals itself not as an evil entity, but as a necessary force of balance - destructive but ultimately serving a cosmic purpose.\n\n" +
                "Together, you and the Harbinger use the Crystal to channel the dark energy, transforming it into something that renews rather than destroys. The land changes, parts die while others flourish, but humanity survives.\n\n" +
                "This vision feels complex and morally ambiguous. As it fades, you notice tiny details consistent with your original prophetic dreams - the specific way shadows move, the sound of wind through abandoned buildings.");

        // Node 16: The Choice
        StoryNode node16 = new StoryNode(16, "The Warden's Challenge", 
                "The visions fade, and you find yourself back in the cave chamber. The Warden hovers before you, awaiting your answer.\n\n" +
                "\"Which did you see?\" it asks. \"The vision of glory, the vision of sacrifice, or the vision of unexpected truth? Choose wisely, Oracle. The fate of more than you know depends on it.\"");

        // Node 17: Outcome - Correct Choice
        StoryNode node17 = new StoryNode(17, "The Crystal Revealed", 
                "\"You have chosen wisely,\" the Warden says, its form becoming more stable, more defined. \"Few see past their own desires and fears to recognize truth.\"\n\n" +
                "The chamber shifts around you, the walls becoming transparent like glass. Behind one such wall, a crystal floats in midair, pulsing with inner light.\n\n" +
                "\"The Crystal of Truth is yours, Oracle. But remember this: it amplifies the intention of its bearer. If your heart falters, so too will its power.\"\n\n" +
                "The wall dissolves, and the Crystal floats toward you, coming to rest in your outstretched hand. It feels both warm and cool to the touch, vibrating slightly as if alive.", 
                false);

        // Node 18: Return Journey
        StoryNode node18 = new StoryNode(18, "Race Against Shadow", 
                "With the Crystal secured, you begin the journey back to Mistaven. But outside the caves, you discover the sky has darkened prematurely. Unnatural storms brew on the horizon, and the air itself feels heavy with malevolent energy.\n\n" +
                "The Harbinger's influence is growing stronger, faster than anticipated. You'll need to hurry if you hope to reach the ritual site in time.\n\n" +
                "As you travel, you notice the Crystal responds to your emotions - glowing brighter when your resolve is strong, dimming when doubt creeps in. It's as if it's testing you, even now.");

        // Node 19: Final Node
        StoryNode node19 = new StoryNode(19, "The Coming Darkness", 
                "You reach the outskirts of Mistaven to find the village in turmoil. Strange phenomena are occurring with increasing frequency - objects moving on their own, people reporting shared nightmares, animals behaving erratically.\n\n" +
                "Elder Thorne meets you at the village boundary, his earlier skepticism replaced by grim acceptance.\n\n" +
                "\"The Council has evacuated most of the villagers to the southern havens,\" he tells you. \"Those with knowledge of the old ways remain to help with the ritual.\"\n\n" +
                "He looks at the Crystal in your hand, his eyes widening. \"So it's true. You actually found it.\" He pauses, the weight of the situation evident in his voice. \"The moons begin their alignment tonight. We must prepare.\"\n\n" +
                "As darkness falls, you look to the sky. The three moons are slowly moving into position, and on the horizon, a wall of shadow approaches that has nothing to do with natural night.",
                true);

        // Node 20: Wrong Choice
        StoryNode node20 = new StoryNode(20, "The Warden's Judgment", 
                "The Warden grows still, its shifting form solidifying into something more defined - a mirror image of yourself, but with eyes that hold the wisdom of ages.\n\n" +
                "\"You have chosen... poorly,\" it says, voice heavy with disappointment. \"The Crystal demands true sight, not what we wish to see.\"\n\n" +
                "The chamber darkens, and you feel a sudden chill. \"I cannot give you the Crystal, but neither will I take your life. Return to your village, Oracle. Perhaps there are other ways to face what comes.\"\n\n" +
                "A sudden wind sweeps through the cave, extinguishing your torch and disorienting you. When light returns, the Warden is gone, and with it, any hope of acquiring the Crystal.",
                true);

        // Create entities
        Entity mystic = new Entity("The Mystic", 8, 10, 
                "A wandering seer with kaleidoscope eyes and ancient knowledge. Their intentions are difficult to discern.");
        
        Entity warden = new Entity("The Whispering Warden", 10, 15, 
                "An ancient entity of light and shadow that guards the Crystal of Truth. It tests all who seek the Crystal with visions and riddles.");
        
        // Add entities to nodes
        node8.setEntity(mystic);
        node11.setEntity(warden);

        // Add choices to nodes
        node1.addChoice(new Choice("Consult the Council of Elders immediately", 2));
        node1.addChoice(new Choice("Seek guidance at the Temple of Seers first", 3));
        
        node2.addChoice(new Choice("Insist that action must be taken now", 3));
        node2.addChoice(new Choice("Accept their skepticism for now and investigate privately", 3));
        
        node3.addChoice(new Choice("Ask Keeper Lyra about the Codex of Prophecies", 4));
        node3.addChoice(new Choice("Investigate the signs of disturbance in the temple", 4));
        
        node4.addChoice(new Choice("Open the Codex", 5));
        
        node5.addChoice(new Choice("Prepare to seek the Crystal of Truth", 6));
        
        node6.addChoice(new Choice("Set out for the Caves of Echo immediately", 7));
        node6.addChoice(new Choice("Gather more information about the Warden first", 7));
        
        node7.addChoice(new Choice("Continue through the Whispering Woods", 8));
        
        node8.addChoice(new Choice("Question the Mystic about their identity and purpose", 9));
        node8.addChoice(new Choice("Listen cautiously to what they have to offer", 9));
        
        node9.addChoice(new Choice("Accept the Mystic's help", 10));
        node9.addChoice(new Choice("Politely decline and continue alone", 10));
        node9.addChoice(new Choice("Confront the Mystic with suspicion", 10, "Oracle's Eye"));
        
        node10.addChoice(new Choice("Follow the luminescent path deeper into the caves", 11));
        node10.addChoice(new Choice("Use your prophetic abilities to sense the correct path", 11, "Prophet's Staff"));
        
        node11.addChoice(new Choice("Accept the Warden's trial", 12));
        
        node12.addChoice(new Choice("Prepare to receive the visions", 13));
        
        node13.addChoice(new Choice("Continue to the next vision", 14));
        
        node14.addChoice(new Choice("Continue to the final vision", 15));
        
        node15.addChoice(new Choice("Return to the Warden with your choice", 16));
        
        node16.addChoice(new Choice("Choose the first vision of victory", 20));
        node16.addChoice(new Choice("Choose the second vision of sacrifice", 20));
        node16.addChoice(new Choice("Choose the third vision of unexpected alliance", 17));
        
        node17.addChoice(new Choice("Take the Crystal and prepare to leave", 18));
        
        node18.addChoice(new Choice("Rush back to Mistaven immediately", 19));
        node18.addChoice(new Choice("Travel carefully, conserving your strength for what's ahead", 19));
        
        // Add nodes to the story
        story.addNode(node1);
        story.addNode(node2);
        story.addNode(node3);
        story.addNode(node4);
        story.addNode(node5);
        story.addNode(node6);
        story.addNode(node7);
        story.addNode(node8);
        story.addNode(node9);
        story.addNode(node10);
        story.addNode(node11);
        story.addNode(node12);
        story.addNode(node13);
        story.addNode(node14);
        story.addNode(node15);
        story.addNode(node16);
        story.addNode(node17);
        story.addNode(node18);
        story.addNode(node19);
        story.addNode(node20);
        
        // Set the starting node
        story.setStartingNodeId(1);

        return story;
    }
    
    /**
     * Creates the story for Chapter 2 
     * @return Story for Chapter 2
     */
    public static Story createChapter2Story() {
        try {
            System.out.println("Creating Chapter 2 story...");
            
            Story story = new Story("The Ancient Prophecy - Chapter 2: The Harbinger's Approach",
                                  "With the Crystal of Truth in hand, you must now face the approaching darkness.");
            
            // Node 1 - Beginning of Chapter 2
            StoryNode node1 = new StoryNode(1, "Preparations for the Ritual", 
                                          "Back in Mistaven, preparations for the ritual are underway. The Crystal pulses in your hand, reacting to the energy in the air.\n\n" +
                                          "Elder Thorne leads you to the ritual site - an ancient stone circle at the village center that predates Mistaven itself. Keeper Lyra is already there, preparing sacred herbs and inscribing symbols on the ground.\n\n" +
                                          "\"The alignment begins at midnight,\" she tells you. \"We have only a few hours to prepare.\"\n\n" +
                                          "The villagers who stayed behind watch with a mixture of fear and hope as you take your place in the circle.");
            
            // Node 2 - Learning the Ritual
            StoryNode node2 = new StoryNode(2, "The Ancient Rites", 
                                          "Keeper Lyra hands you a worn tome with faded pages. \"These are the rites that must be performed. The Crystal responds to intention and focus - you must learn these words and understand their meaning.\"\n\n" +
                                          "The ritual is complex, involving specific intonations and gestures. As you study, you feel the Crystal responding to certain phrases, glowing brighter as if recognizing them.\n\n" +
                                          "\"The Harbinger will attempt to prevent the ritual,\" Elder Thorne warns. \"Its influence will be strongest when the moons reach perfect alignment. You must not falter, no matter what occurs.\"");
            
            // Node 3 - Disturbance in the Village
            StoryNode node3 = new StoryNode(3, "Signs of Corruption", 
                                          "A commotion outside interrupts your preparations. One of the village guards has collapsed, writhing on the ground. His skin has taken on a grayish hue, and his eyes have turned completely black.\n\n" +
                                          "\"It's beginning,\" Lyra whispers. \"The Harbinger's corruption spreads.\"\n\n" +
                                          "Other villagers back away in fear, but you approach cautiously, the Crystal still in your hand. As you near the fallen guard, the Crystal glows intensely, and the darkness seems to recoil from its light.");
            
            // Create more nodes for Chapter 2...
            
            // Node 4 - Using the Crystal
            StoryNode node4 = new StoryNode(4, "The Crystal's Power", 
                                          "You hold the Crystal over the afflicted guard. A beam of pure light extends from it, touching his forehead. The guard's back arches, and a shadowy mist begins to seep from his mouth and eyes, dissipating in the air.\n\n" +
                                          "After a moment, his eyes return to normal, and color returns to his skin. He looks at you with confusion and gratitude.\n\n" +
                                          "\"I heard... voices,\" he says weakly. \"Telling me to open the village gates. To let the darkness in.\"\n\n" +
                                          "Lyra touches your shoulder. \"The Crystal responds to you strongly. This is a good sign, but using its power depletes your own energy. Use it wisely.\"");
            
            // Node 5 - The First Attack
            StoryNode node5 = new StoryNode(5, "Shadows at the Gate", 
                                          "As dusk falls, an unnatural fog rolls through the village. Within it, darker shapes move - not quite physical, but more substantial than mere shadows.\n\n" +
                                          "\"Shadow Wraiths,\" Elder Thorne says grimly. \"Harbingers of what's to come. They cannot harm us physically, but their touch brings despair and fear.\"\n\n" +
                                          "The village defenders form a perimeter around the ritual site, but you can see the uncertainty in their eyes. These are not enemies they can fight with conventional weapons.");
            
            // Node 6 - The Mystic Returns
            StoryNode node6 = new StoryNode(6, "An Unexpected Ally", 
                                          "Through the gathering shadows, a familiar figure approaches - the Mystic from the Whispering Woods. They stride confidently through the fog, seemingly unaffected by the Shadow Wraiths.\n\n" +
                                          "\"The hour grows late,\" the Mystic says, joining your group at the ritual site. \"The Harbinger has sent its first wave of servants to test your defenses.\"\n\n" +
                                          "Elder Thorne eyes the newcomer suspiciously. \"Who is this?\"\n\n" +
                                          "Before you can answer, the Mystic produces a small crystal that glows with the same light as your larger Crystal of Truth. \"I am a keeper of the old knowledge, and I come to help.\"");
            
            // Add more nodes for the rest of Chapter 2...
            
            // Node 7 - The Decision
            StoryNode node7 = new StoryNode(7, "Trust or Suspicion", 
                                          "Keeper Lyra stares at the Mystic intently. \"Few outside our order know of the Crystals. How did you come by this knowledge?\"\n\n" +
                                          "The Mystic smiles enigmatically. \"I have walked many paths and learned many secrets. Some would call me friend, others enemy. What matters is that our goals align - to stop the Harbinger.\"\n\n" +
                                          "You sense no deception from the Mystic, but there is much they're not revealing. The Crystal in your hand pulses, neither brightening nor dimming in their presence.");
            
            // Node 8 - The Ritual Begins
            StoryNode node8 = new StoryNode(8, "As Midnight Approaches", 
                                          "The three moons rise higher in the sky, their alignment nearly complete. The air feels charged with conflicting energies - the pure light of the Crystal and the encroaching darkness of the Harbinger.\n\n" +
                                          "You take your position at the center of the stone circle. Lyra, Elder Thorne, and the other ritual participants form a protective ring around you. If you accepted the Mystic's help, they stand at a key position in the circle, their smaller crystal raised.\n\n" +
                                          "\"Begin the intonation,\" Lyra instructs. \"Focus on the Crystal and the words of binding.\"");
            
            // Node 9 - The Harbinger Manifests
            StoryNode node9 = new StoryNode(9, "The Shadow Takes Form", 
                                          "As you speak the ancient words, the Crystal flares with blinding light. The shadows surrounding Mistaven coalesce, forming a massive, towering figure of darkness that looms over the village.\n\n" +
                                          "The Harbinger has arrived.\n\n" +
                                          "Its form is difficult to comprehend - constantly shifting, yet with a suggestion of a humanoid shape. Two points of crimson light serve as eyes, focusing on you and the Crystal with malevolent intent.\n\n" +
                                          "\"The binding will not hold,\" a voice echoes, seeming to come from everywhere at once. \"This world is mine to cleanse, as it has been every millennium.\"");
            
            // Node 10 - The Final Challenge
            StoryNode node10 = new StoryNode(10, "Confrontation", 
                                           "The Harbinger extends a tendril of darkness toward you, bypassing the protective circle. Where it touches, the ground withers and cracks.\n\n" +
                                           "\"You have a choice, Prophet,\" the entity says. \"Join me, and I will spare this village. Resist, and watch as everything you love is consumed.\"\n\n" +
                                           "You feel a pressure in your mind - the Harbinger attempting to influence your thoughts. The Crystal pulses rapidly in response, its light flickering as if struggling against the darkness.\n\n" +
                                           "In this moment, you remember the third vision shown by the Warden - the unexpected alliance. Was that truly the prophecy, or is the Harbinger trying to manipulate you?");
            
            // Final nodes - different endings
            
            // Node 11 - Ending: Binding the Harbinger
            StoryNode node11 = new StoryNode(11, "The Binding", 
                                           "You raise the Crystal high, channeling all your will and energy into it. Its light expands outward, forming a dome of pure energy around the stone circle.\n\n" +
                                           "The ancient words flow from your lips, each syllable charged with power. The Harbinger roars in defiance, its form contorting as the binding takes effect.\n\n" +
                                           "\"This is not the end,\" it warns as its form begins to dissipate. \"I am eternal. I will return.\"\n\n" +
                                           "With a final surge of will, you complete the ritual. The Harbinger's form collapses, drawn into the Crystal itself, which now contains a swirling darkness at its core.\n\n" +
                                           "The ritual has succeeded, but at great cost. You feel drained, aged, as if years of your life were consumed in that final moment. The Crystal, now containing both light and shadow, must be protected until the next alignment in a thousand years.",
                                           true);
            
            // Node 12 - Ending: The Transformation
            StoryNode node12 = new StoryNode(12, "A Different Path", 
                                           "You lower the Crystal, stepping forward to address the Harbinger directly.\n\n" +
                                           "\"There is another way,\" you say, remembering the vision. \"Not binding, but transformation.\"\n\n" +
                                           "The Harbinger pauses, its crimson eyes studying you with newfound interest.\n\n" +
                                           "You raise the Crystal, not as a weapon but as a channel. \"This land needs change, not destruction. Let me guide your power.\"\n\n" +
                                           "What follows is neither victory nor defeat, but something unprecedented. The Crystal glows with blinding intensity as you channel the Harbinger's energy through it, transforming the destructive force into one of renewal.\n\n" +
                                           "The landscape changes - dead trees fall to nourish new growth, stagnant waters are cleared, long-dormant seeds awaken in the soil. It is painful, it is change, but it is not the end.",
                                           true);
            
            // Node 13 - Failed Ending
            StoryNode node13 = new StoryNode(13, "Darkness Triumphant", 
                                           "The pressure in your mind becomes overwhelming. The Crystal slips from your grasp, falling to the ground with a crystalline sound that seems to echo endlessly.\n\n" +
                                           "The Harbinger's laughter resonates through the village as darkness engulfs everything. The protective circle falters and breaks, your allies falling one by one.\n\n" +
                                           "In your final moments of clarity, you realize your mistake. The true prophecy was not what you thought, and now the world will pay the price for your error.\n\n" +
                                           "Darkness falls. The age of the Harbinger begins.",
                                           true);
            
            // Create entities
            Entity shadowWraith = new Entity("Shadow Wraith", 7, 8, 
                                           "A semi-corporeal manifestation of the Harbinger's influence, bringing fear and despair.");
            
            Entity harbinger = new Entity("The Harbinger", 10, 15, 
                                        "An ancient entity of darkness that returns every millennium to 'cleanse' the world. Its true nature and motivations remain mysterious.");
            
            // Add entities to nodes
            node5.setEntity(shadowWraith);
            node9.setEntity(harbinger);
            
            // Add choices to nodes
            node1.addChoice(new Choice("Help prepare the ritual site", 2));
            node1.addChoice(new Choice("Meditate with the Crystal to strengthen your connection", 2));
            
            node2.addChoice(new Choice("Study the ritual instructions carefully", 3));
            node2.addChoice(new Choice("Ask Keeper Lyra for more information about the Harbinger", 3));
            
            node3.addChoice(new Choice("Approach the afflicted guard with the Crystal", 4));
            node3.addChoice(new Choice("Ask Keeper Lyra how to help him", 4));
            
            node4.addChoice(new Choice("Return to your ritual preparations", 5));
            node4.addChoice(new Choice("Check if others in the village show signs of corruption", 5));
            
            node5.addChoice(new Choice("Use the Crystal to repel the Shadow Wraiths", 6));
            node5.addChoice(new Choice("Focus on completing the ritual preparations", 6));
            
            node6.addChoice(new Choice("Welcome the Mystic's help", 7));
            node6.addChoice(new Choice("Question the Mystic's intentions", 7));
            
            node7.addChoice(new Choice("Accept the Mystic's assistance for the ritual", 8));
            node7.addChoice(new Choice("Thank the Mystic but decline their direct involvement", 8));
            
            node8.addChoice(new Choice("Begin the ritual as instructed", 9));
            node8.addChoice(new Choice("Modify the ritual based on your prophetic insights", 9, "Prophet's Staff"));
            
            node9.addChoice(new Choice("Continue the binding ritual as planned", 10));
            node9.addChoice(new Choice("Attempt to communicate with the Harbinger", 10));
            
            node10.addChoice(new Choice("Complete the binding ritual to imprison the Harbinger", 11));
            node10.addChoice(new Choice("Attempt to transform the Harbinger's energy as seen in your vision", 12));
            node10.addChoice(new Choice("Lower your defenses and listen to the Harbinger's offer", 13));
            
            // Add nodes to the story
            story.addNode(node1);
            story.addNode(node2);
            story.addNode(node3);
            story.addNode(node4);
            story.addNode(node5);
            story.addNode(node6);
            story.addNode(node7);
            story.addNode(node8);
            story.addNode(node9);
            story.addNode(node10);
            story.addNode(node11);
            story.addNode(node12);
            story.addNode(node13);
            
            // Set the starting node
            story.setStartingNodeId(1);
            
            System.out.println("Chapter 2 story created successfully.");
            return story;
            
        } catch (Exception e) {
            System.err.println("ERROR while creating Chapter 2 story: " + e.getMessage());
            e.printStackTrace();
            return createFallbackStory();
        }
    }
    
    /**
     * Creates a simple fallback story in case of errors
     */
    private static Story createFallbackStory() {
        Story fallback = new Story("The Ancient Prophecy - Emergency Fallback", 
                                 "A simplified version loaded due to an error.");
        
        StoryNode intro = new StoryNode(1, "Continuation", 
                                       "After your previous journey, you continue your quest. The Crystal of Truth glows in your hand, guiding you forward.");
        
        StoryNode end = new StoryNode(2, "To Be Continued", 
                                     "Your adventure will continue in the next chapter...", true);
        
        intro.addChoice(new Choice("Continue", end));
        
        fallback.addNode(intro);
        fallback.addNode(end);
        
        return fallback;
    }

    /**
     * Simulates parsing a JSON file
     * In a real implementation, use a JSON library
     */
    private static Story parseStoryJson(String json) {
        // This code is a simulation; in a real project, use Jackson or Gson
        return createDemoStory();
    }
}