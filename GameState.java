import java.util.*;

/*
GameState.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the class to hold the state of the running game and allows easy
passing of important information to methods that require data from the
state of the game.
*/

public class GameState {
    Location currentLocation;
    CommandSystem commandSystem;
    Location[] location = new Location[25];
    Player player;
    Item pet;

    public static int DISPLAY_WIDTH = 80;

    public GameState() {
        commandSystem = new CommandSystem(this);
        
        
        commandSystem.addNoun("backpack");
        // Construct Player
        player = new Player(this);
        player.name = "Vyn Wilkes";
        player.description = "You are a treasure hunter from the Vitality Realm looking for the luxurious Nexus Sphere!";
        player.addAmount(20);
        player.hp = 100;
        commandSystem.addNoun("Vyn");

        // Construct Pet and store it in backpack
        pet = new Item(this, "Slinky");
        pet.description = "Slinky is a fluffy and adorable slynderof. They have been by your side for decades, assisting you throughout your quests for treasure!";
        commandSystem.addNoun(pet.name);
        player.addToBackpack(pet);

        // Construct coins
        Item coins = new Item(this, "coins");
        coins.description = "These are Nexus [coins]. The currency of the universe! Always helpful to collect to boost your wealth!";
        player.addToBackpack(coins);

        // Construct Locations
        // Beach
        // Middle Beach
        location[0] = new Location(this, "Bewildering Beach", "bb");
        location[0].description = "You are in the middle of a mysterious and sandy beach. There is a mountain somewhere in the distance.";

        // Up Beach
        location[1] = new Location(this, "Landside Beach",  "lb");
        location[1].description = "You are on a broken-down boardwalk at the beach. There is a tall mountain ahead of you...";
        location[1].interaction = "You use the grappling hook to soar over the peak and past the mountain!";
        location[1].hint = "Hint: You can use your [grapple] hook here";

        // East Beach
        location[2] = new Location(this,"Eastside Beach", "eb");
        location[2].description = "You have reached the end of the beach and can see a path leading to a Forest.";

        // Down Beach
        location[3] = new Location(this, "Oceanside Beach", "ob");
        location[3].description = "You are overlooking a tumultuous ocean. Much too dangerous to venture out there!";
        location[3].addItem(coins);

        // West Beach
        location[4] = new Location(this, "Westside Beach", "wb");
        location[4].description = "You are on the westside of the beach moving through the sand.";
        Item raft = new Item(this, "raft");
        raft.description = "This inflatable [raft] can be used to cross rivers and lakes. It may be useful for discovering new locations!";
        location[4].addItem(raft);

        // Beach Path
        location[5] = new Location(this, "Beach Path", "bp");
        location[5].description = "You are on the rocky path between the Beach and Forest. You see [Angelica] along your way.";
        Person angelica = new Person(this);
        angelica.name = "Angelica";
        angelica.description = "[Angelica] has battle-worn armor and a sleek helmet. She was part of the Ethereal Command Corp to protect the Nexus from villainous monsters like the terrifying Dracheon.";
        angelica.statement = "If you are looking for a way over the mountain at the Beach, you might want to check around the Majestic Mansion past the forest.";
        angelica.statement2 = "You found the grappling hook! Try usng it at the mountain near the Beach!";
        location[5].addPerson(angelica);
        
        // Forest
        // Middle Forest
        location[6] = new Location(this, "Forbidden Forest", "ff");
        location[6].description = "You are in the middle of the Forbidden Forest filled with dark oak trees that cloud your vision so explore at your own expense...";
        
        // Up Forest
        location[7] = new Location(this, "High-End Forest", "hf");
        location[7].description = "You are on the edge of a cliff overlooking a ravenous ravine. Too dangerous to cross!";
        location[7].addItem(coins);

        // East Forest
        location[8] = new Location(this, "East-End Forest", "ef");
        location[8].description = "You have reached an eastward clearing of the forest that is next to a bridge leading to a Mansion.";

        // Down Forest
        location[9] = new Location(this, "Low-End Forest", "lf");
        location[9].description = "You are at the low-end of the Forbidden Forest and it does not look like there is any more terrain past here.";
        Item sword = new Item(this, "sword");
        sword.description = "This [sword] was forged from meteorites and is a powerful tool in combat. It may be useful for slaying monsters!";
        location[9].addItem(sword);

        // West Forest
        location[10] = new Location(this, "West-End Forest", "wf");
        location[10].description = "You have reached a westward clearing of the forest that is next to a path leading to a beach.";
        
        // Mansion Path
        location[11] = new Location(this, "Mansion Path", "mp");
        location[11].description = "You are on the path between the Mansion and Forest. You see [Zelo] along your way.";
        Person zelo = new Person(this);
        zelo.name = "Zelo";
        zelo.description = "[Zelo] has dark skin with shiny earrings and a long wool trench coat. He is a former warlock and learned sorcery from an enchantress named Mystique.";
        zelo.statement = "If you are looking for a way over the river at the Mansion, you might want to check around the Bewildered Beach past the forest.";
        zelo.statement2 = "You found the raft! Try usng it at the river near the Mansion!";
        location[11].addPerson(zelo);

        // Mansion
        // Majestic Mansion
        location[12] = new Location(this, "Majestic Mansion", "mm");
        location[12].description = "You are now inside the Majestic Mansion. It is beautifully decorated with vintage art and elegant furniture. Make sure to watch your step!";

        // Up Mansion
        location[13] = new Location(this, "Mansion Frontyard", "mf");
        location[13].description = "You are in the frontyard of the Mansion with a raging river ahead. If only you had something to get across...";
        location[13].interaction = "You raft through the whitewater and make it across the river!";
        location[13].hint = "Hint: You can use your [raft] here";

        // East Mansion
        location[14] = new Location(this, "Mansion Garage", "mg");
        location[14].description = "You have reached the garage on the end of the Mansion. It is filled with a lot of clutter!";
        Item grapple = new Item(this, "grapple");
        grapple.description = "This [grapple] can be used to scale the highest peaks. It could help you discover new locations!";
        location[14].addItem(grapple);

        // Down Mansion
        location[15] = new Location(this, "Mansion Backyard", "mb");
        location[15].description = "You are around the back of the Mansion with an endless grass field stretching into the distance.";
        location[15].addItem(coins);

        // West Mansion
        location[16] = new Location(this, "Mansion Shed", "ms");
        location[16].description = "You have reached the shed on the west side of the Mansion that is next to a bridge leading to a forest.";

        // Past Mountain
        location[17] = new Location(this, "Plains by the Mountain", "pm");
        location[17].description = "You are now between the mountains and the plains. Towards the plains, you see a mysterious figure...";
        location[17].interaction = "You grapple and soar over the mountain back towards the landside of the Bewildering Beach.";
        location[17].hint = "[nl]Hint: You can use your [grapple] here!";

        // Past River
        location[18] = new Location(this, "Plains by the River", "pr");
        location[18].description = "You are now between the river and the plains. Towards the plains, you see a mysterious figure...";
        location[18].interaction = "You raft over the river back towards the front yard of the Majestic Mansion.";
        location[18].hint = "[nl]Hint: You can use your [raft] here!";

        // Prosperous Plains
        location[19] = new Location(this, "Prosperous Plains", "pp");
        location[19].description = "You are walking through the Prosperous Plains. As you get closer, the figure reveals themselves to be [Isaac].";
        Person isaac = new Person(this);
        isaac.name = "Isaac";
        isaac.description = "[Isaac] has long and scruffy beard along with a rough looking robe. He has the warmest smile you will ever see and looks like a generous man!";
        isaac.statement = "If you are a traveler on a journey to find the Nexus Sphere. I would look in the Caveful Canyon up ahead, past the Helpful Hut!";
        isaac.statement2 = "Wow! Thank you for letting me know! It has been so long since I have heard from her. Nice to know someone is thinking of me.";
        location[19].addPerson(isaac);

        // Canyon
        // Top Canyon
        location[20] = new Location(this, "Helpful Hut", "hh");
        location[20].description = "You have reached the hut at the top of a steep canyon. There is a super-sonic [sled] inside that may be useful to traverse the Canyon.";
        Item sled = new Item(this, "sled");
        sled.description = "This is a Super-Sonic [sled] that may be useful to go up and down the Canyon ahead.";
        location[20].addItem(sled);
        location[20].interaction = "You speed down the canyon on the Super-Sonic [sled], reaching the bottom within seconds!";

        // Bottom Canyon
        location[21] = new Location(this, "Caveful Canyon", "cc");
        location[21].description = "You have reached the bottom of the canyon. There seems to be two mysterious paths forward. Proceed with caution...";
        location[21].interaction = "You use the Super Sonic [sled] to zoom up the canyon!";

        // West Canyon
        location[22] = new Location(this, "Sacred Stronghold", "ss");
        location[22].description = "You are in a stronghold with a lot of elixirs and items along the walls. It looks very mysterious, and you see the enchantress, [Mystique]...";
        location[22].interaction = "[nl][nl]You traded with Mystique![nl]She lets you pass into entrance of a beautiful lake.";
        Person mystique = new Person(this);
        mystique.name = "Mystique";
        mystique.description = "[Mystique] has a long red robe and fancy belt. She is a sorceress that has lived in the Ethereal Nexus for many... many years due to a spell that granted her eternal life. She is a collector of an infinite number of items and entities.[nl]P.S. She taught Zelo his sorcery skills. ";
        mystique.statement = "If you would like to get past here, I am going to need something in exchange. I want 40 Nexus [coins] or your precious companion [Slinky]!";
        location[22].hint = "[nl][nl][Hint: To trade with Mystique, [give] [Mystique] [coins] or [slinky].]";
        location[22].addPerson(mystique);

        // East Canyon
        location[23] = new Location(this, "Cave of Dracheon", "cd");
        location[23].description = "You are inside a cave that looks dark and creepy. It looks empty but you turn the corner and hear a loud growl from the demon, [Dracheon]! [nl]Now it's your turn to talk back to him.";
        location[23].interaction = "[nl][nl]You have slain the M ighty Dracheon![nl][nl]You found a way out of the cave that has led you to a beautiful lake.";
        Person dracheon = new Person(this);
        dracheon.name = "Dracheon";
        dracheon.description = "[Dracheon] has rough scales and fangs that look like they could cut you up entirely! It has been an evil monster who has threatened the Ethereal Nexus for years until Mystique casted a spell that imprisoned it in this cave eternally!";
        dracheon.statement = "Another traveler with intent of finding the Nexus Sphere has come into my cave?? Do you really think you can defeat ME? RAWRRRRRR!!";
        dracheon.hp = 150;
        location[23].hint = "[nl][nl][Hint: To fight Dracheon, [use] [sword] or [use] [slinky] on [Dracheon].]";
        location[23].hint2 = "[nl][nl][Hint: To fight Dracheon, [use] [slinky] on [Dracheon], since you have no weapon.]";
        location[23].addPerson(dracheon);
        

        // Lake
        location[24] = new Location(this, "Luxurious Lake", "ll");
        location[24].description = "This is one of the prettiest places on the planet! The water is glistening, and grass looks miraculously green.";
        Item sphere = new Item(this, "Sphere");
        sphere.description = "Wow there it is, the Nexus [Sphere] right in front of your very eyes! It shines very bright!";
        location[24].addItem(sphere);

        //Beach
        location[0].addExit(location[1]);
        location[0].addExit(location[2]);
        location[0].addExit(location[3]);
        location[0].addExit(location[4]);

        
        location[2].addExit(location[5]);

        location[5].addExit(location[10]);
        
        //Forest
        location[6].addExit(location[7]);
        location[6].addExit(location[8]);
        location[6].addExit(location[9]);
        location[6].addExit(location[10]);

        location[8].addExit(location[11]);

        location[11].addExit(location[16]);
        
        //Mansion
        location[12].addExit(location[13]);
        location[12].addExit(location[14]);
        location[12].addExit(location[15]);
        location[12].addExit(location[16]);

        //Towards Plains
        location[17].addExit(location[19]);

        location[18].addExit(location[19]);

        location[19].addExit(location[20]);

        //Canyons
         //
        location[21].addExit(location[22]);
        location[21].addExit(location[23]);
        
        
        // Create array to store the three starting locations
        Location[] stLocations = {location[0], location[6], location[12]};
        // Create random object to produce a random integer
        Random rIndex = new Random();
        // Store the randomly chosen starting location in current location
        currentLocation = stLocations[rIndex.nextInt(stLocations.length)];

        //currentLocation = location[19]; // TEST
    }

    public int randNum(int max, int min){
        Random rnum = new Random();
        return rnum.nextInt((max - min + 1)) + min;
    }
}
