import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
CommandSystem.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class is the primary logic class for the system. It defines what commands are valid, 
and what happens when those commands are executed.  
*/

import java.util.*;

public class CommandSystem {
    private static int DISPLAY_WIDTH = 80;
    private GameState state;

    private List<String> verbs = new ArrayList<String>();
    private List<String> verbDescription = new ArrayList<String>();
    private List<String> nouns = new ArrayList<String>();
    public static final boolean ADD_COLOR = true;
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";
    public static final String COLOR = ANSI_RED;
    public static final String COLOR2 = ANSI_PURPLE;
    public static final String COLOR3 = ANSI_BLUE;
    public static final String COLOR4 = ANSI_GREEN;
    public static final String COLOR5 = ANSI_YELLOW;

    String ending = "";

    public CommandSystem(GameState state) {
        this.state = state;
        DISPLAY_WIDTH = GameState.DISPLAY_WIDTH;
        // Assign verbs and descriptions here
        addVerb("?", "Show this help screen.");
        addVerb("look",
                "Use the look command by itself to look in your current area. \nYou can also look at a person or object by typing look and the name of what/who you want to look at.\nExample: look book");
        addVerb("l", "Same as the look command.");
        addVerb("grab",
                "Use the grab command on an object by typing grab and the name of what you want to grab. Example: grab book");
        addVerb("g", "Same as the grab command.");
        addVerb("move", "Moves the player to the location. Type  move and the name key of the location to move to. Example: move ab");
        addVerb("m", "Same as the move command.");
        addVerb("use",
                "Use the use command with an object by typing use and the name of an item you want to use. You can also use an item on a person or an entity by typing use, then the item you are using, then the person or entity you are interacting with. Example: use book or use net fish");
        addVerb("u", "Same as the use command.");
        addVerb("give",
                "Use the give command with an object by typing give, the person you would like to give the item to, then the name of the item. Example: give shawn money");
        addVerb("gv", "Same as the give command.");
        addVerb("open", "Use the open command with your backpack by typing open backpack to view your inventory.");
        addVerb("o", "Same as the open command.");
        addVerb("talk",
                "Use the talk command to talk to a person by typing talk, then the name of who you want to talk to if they are in the same location as you. Example: talk shawn");
        addVerb("t", "Same as the talk command.");
        addVerb("tell",
                "Use the tell command to tell a person about something else by typing tell, the name of who you are telling, then the person you are telling them about. Example: \"tell shawn jessica\" to tell shawn about jessica");
        addVerb("quit", "Quit the game.");
        addVerb("q", "Same as quit.");
        // NOTE: In the starter code, this is handeled by the client code - not the
        // CommandSystem.
    }

    // When a command is only one Verb this method controls the result.
    public void executeVerb(String verb) {
        switch (verb) {
            case "l":
            case "look": // will show the description of the current room (stored in the state object)
                System.out.println(formatStringToScreenWidth(state.currentLocation.getDesc()));

                if (state.currentLocation.hint.length() > 0) {
                    if (state.player.hasItem("grapple") == 0 && (state.currentLocation == state.location[1]
                            || state.currentLocation == state.location[17])) {
                        System.out.println(formatStringToScreenWidth("\n" + state.currentLocation.hint));
                    }
                }

                if (state.currentLocation.hint.length() > 0) {
                    if (state.player.hasItem("raft") == 0 && (state.currentLocation == state.location[13]
                            || state.currentLocation == state.location[18])) {
                        System.out.println(formatStringToScreenWidth("\n" + state.currentLocation.hint));
                    }
                }
                break;

            case "?":
                this.printHelp();
                break;
            default:
                System.out.println("That command does not work on its own.");
        }
    }

    // When a command is a Verb followed by a noun, this method controls the result.
    public void executeVerbNoun(String verb, String noun) {
        // Initilize the string that we will use as a response to player input.
        String resultString = "";

        switch (verb) { // Deciddes what to do based on each verb
            case "l":
            case "look":
                switch (noun) {
                    case "sword":
                    case "grapple":
                    case "raft":
                    case "coins":
                    case "sled":
                    case "sphere":
                    case "slinky":
                        if (state.currentLocation.hasItem(noun) == 0) {
                            resultString = state.currentLocation.locationItem.get(0).description;
                        } else if (state.player.hasItem(noun) == 0) {
                            resultString = state.player.getItem(noun).description;
                        } else {
                            resultString = "You don't see " + noun + " here.";
                        }
                        break;
                    case "angelica":
                    case "zelo":
                    case "isaac":
                    case "mystique":
                    case "dracheon":
                        if (state.currentLocation.hasPerson(noun) == 0) {
                            resultString = state.currentLocation.locationPerson.get(0).description;
                        } else {
                            resultString = "You don't see " + noun + " here.";
                        }
                        break;
                    case "vyn":
                        resultString = state.player.description;
                        break;
                    default:
                        resultString = "That doesn't make sense, silly!";
                }

                break;
            case "t":
            case "talk":
                switch (noun) {
                    case "angelica":
                    case "zelo":
                    case "isaac":
                    case "mystique":
                    case "dracheon":
                        if (state.currentLocation.hasPerson(noun) == 0) {
                            if (state.currentLocation.locationPerson.get(0).counter == 0
                                    && !(state.currentLocation == state.location[23])) {
                                resultString = "Hello, my name is " + state.currentLocation.locationPerson.get(0).name
                                        + "! ";
                            }
                            resultString += state.currentLocation.locationPerson.get(0).statement;
                            switch (noun) {
                                case "angelica":
                                    if (state.player.hasItem("grapple") == 0) {
                                        if (state.currentLocation.locationPerson.get(0).counter == 0
                                                && !(state.currentLocation == state.location[23])) {
                                            resultString = "Hello, my name is "
                                                    + state.currentLocation.locationPerson.get(0).name
                                                    + "! ";
                                            resultString += state.currentLocation.locationPerson.get(0).statement2;
                                        } else {
                                            resultString = state.currentLocation.locationPerson.get(0).statement2;
                                        }
                                    }
                                    if (state.location[19].locationPerson.get(0).counter == 0) {
                                        resultString += "[nl][nl]By the way, if you see Isaac in the Prosperous Plains, tell him I said \"Hi!\". It has been so long since I have seen him... ";
                                    }
                                    break;
                                case "zelo":
                                    if (state.player.hasItem("raft") == 0) {
                                        if (state.currentLocation.locationPerson.get(0).counter == 0
                                                && !(state.currentLocation == state.location[23])) {
                                            resultString = "Hello, my name is "
                                                    + state.currentLocation.locationPerson.get(0).name
                                                    + "! ";
                                            resultString += state.currentLocation.locationPerson.get(0).statement2;
                                        } else {
                                            resultString = state.currentLocation.locationPerson.get(0).statement2;
                                        }
                                    }
                                    break;
                                case "dracheon":
                                    if (state.player.hasItem("sword") == 0) {
                                        resultString += state.currentLocation.hint;
                                    } else {
                                        resultString += state.currentLocation.hint2;
                                    }
                                    break;
                                case "isaac":
                                    if (state.currentLocation.locationPerson.get(0).counter2 == 0) {
                                        resultString += "[nl]I wonder if anyone is thinking about me...";
                                    }
                                    break;
                                case "mystique":
                                    resultString += state.currentLocation.hint;
                                    break;
                            }
                            state.currentLocation.locationPerson.get(0).counter++;
                        } else {
                            resultString = "You can't talk to " + noun + " here.";
                        }
                        break;
                    default:
                        resultString = "That does not make sense, silly!";
                }
                break;
            case "g":
            case "grab":
                if (state.currentLocation.hasItem(noun) == 0) {
                    if (noun.equalsIgnoreCase("sphere")) {
                        ending = "";
                        if (state.player.hasItem("slinky") == 0) {
                            if (state.randNum(1, 0) == 0) {
                                ending = "You grabbed the Nexus Sphere but Slinky takes it and eats it! \nWhat a bad companion lololol!";
                            } else {
                                ending += "You have finally achieved the Nexus Sphere!\nAfter all this time it's finally yours!\nNow you can return home and boast about your journey, achievements, and riches with your loyal companion, Slinky.";
                            }
                        } else {
                            if (state.randNum(1, 0) == 0) {
                                ending += "You have finally achieved the Nexus Sphere!\nAfter all this time it's finally yours!\nBut you're burdened with the loss of your most faithful companion, Slinky...";
                            } else {
                                ending += "You have finally achieved the Nexus Sphere!\nBut you drop it and it shatters at your feet! \nAll for nothing...";
                            }
                        }
                        state.player.addToBackpack(state.currentLocation.locationItem.get(0));
                    } else {
                        resultString = state.currentLocation.removeItem(noun);
                    }
                } else {
                    resultString = "You cannot grab the " + noun + " here.";
                }
                break;
            case "u":
            case "use":
                if (state.player.hasItem(noun) == 0) {
                    if (state.currentLocation.interaction.length() > 0) {
                        switch (noun) {
                            case "grapple":
                                if (state.currentLocation == state.location[1]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[17];
                                } else if (state.currentLocation == state.location[17]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[1];
                                } else {
                                    resultString = "You cannot use the " + noun + " here.";
                                }
                                break;
                            case "raft":
                                if (state.currentLocation == state.location[13]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[18];
                                } else if (state.currentLocation == state.location[18]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[13];
                                } else {
                                    resultString = "You cannot use the " + noun + " here.";
                                }
                                break;
                            case "sled":
                                if (state.currentLocation == state.location[20]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[21];
                                } else if (state.currentLocation == state.location[21]) {
                                    resultString = state.currentLocation.interaction;
                                    state.currentLocation = state.location[20];
                                } else {
                                    resultString = "You cannot use the " + noun + " here.";
                                }
                                break;
                            case "slinky":
                                resultString += "Slinky purrs and smiles at you!";
                                if (state.currentLocation == state.location[23]) {
                                    resultString += "You need to [use slinky] on [dracheon].";
                                } else {
                                    resultString += "Slinky purrs and smiles at you!";
                                }
                                break;
                            case "sword":
                                if (state.currentLocation == state.location[23]) {
                                    resultString += "You need to [use sword] on [dracheon].";
                                } else {
                                    resultString += "No need for the " + noun + " here!";
                                }
                                break;
                            default:
                                resultString = "No need for the " + noun + " here!";
                        }
                    } else {
                        resultString = "You cannot use the " + noun + " here.";
                    }
                } else {
                    resultString = "You do not have the " + noun;
                }
                break;
            case "m":
            case "move":
                for (Location l : state.currentLocation.exits) {
                    if (l.key.equalsIgnoreCase(noun)) {
                        state.player.move(l);
                        resultString = "You moved to the " + l.name + ".[nl][nl]";
                        resultString += state.currentLocation.getDesc();
                        break;
                    } else {
                        resultString = "You cannot move there from " + state.currentLocation.name + ".";
                    }
                }
                break;
            case "o":
            case "open":
                if (noun.equalsIgnoreCase("backpack")) {
                    resultString = state.player.printBackpack();
                } else {
                    resultString = "You can only open your backpack.";
                }
                break;
            default:
                resultString = "This command does not apply here.";
        }

        System.out.println(formatStringToScreenWidth(resultString));
    }

    // When a command is a Verb followed by two nouns, this method controls the
    // result.
    public void executeVerbNounNoun(String verb, String noun, String noun2) {
        String resultString = "";

        switch (verb) {
            case "t":
            case "talk":
            case "tell":
                if (state.currentLocation.hasPerson(noun) == 0) {
                    if (noun.equalsIgnoreCase("isaac")) {
                        if (noun2.equalsIgnoreCase("angelica")) {
                            if (state.location[5].locationPerson.get(0).counter > 0) {
                                resultString = state.currentLocation.locationPerson.get(0).statement2;
                                if (state.currentLocation.locationPerson.get(0).counter2 == 0) {
                                    resultString += "[nl]Here, take some Nexus Coins for good luck![nl][nl]You got "
                                            + state.player.addRandAmount() + " Nexus [coins]!";
                                }
                                state.currentLocation.locationPerson.get(0).counter2++;
                            } else {
                                resultString = "You have not talked to Angelica yet.";
                            }
                        } else {
                            resultString = "" + noun2 + " is not of importance to Isaac.";
                        }
                    } else {
                        resultString = "They do not want to hear about " + noun2 + ".";
                    }
                } else {
                    resultString = "You can't talk to " + noun + " here.";
                }
                break;
            case "gv":
            case "give":
                if (state.player.hasItem(noun2) == 0) {
                    if (state.currentLocation.interaction.length() > 0) {
                        switch (noun2) {
                            case "slinky":
                            case "coins":
                                if (state.currentLocation.hasPerson(noun) == 0) {
                                    if (noun.equalsIgnoreCase("mystique")) {
                                        if (noun2.equalsIgnoreCase("coins")) {
                                            if (state.player.amount >= 40) {
                                                resultString = state.currentLocation.interaction;
                                                resultString += " You lost 40 coins. ";
                                                state.player.addAmount(-40);
                                                state.currentLocation = state.location[24];
                                            } else {
                                                resultString = "You dont have enough Nexus [Coins]!";
                                            }
                                        } else if (noun2.equalsIgnoreCase("slinky")) {
                                            resultString = " Slinky gives you one last purr...";
                                            resultString += state.currentLocation.interaction;
                                            state.player.removeItem(noun2);
                                            state.currentLocation = state.location[24];
                                        }
                                    } else {
                                        resultString = "You cannot give " + noun2 + " to " + noun + " here.";
                                    }
                                } else {
                                    resultString = "There is nothing here to use the " + noun2 + " on.";
                                }
                                break;
                            default:
                                resultString = "Mystique does not want your " + noun2 + ".";
                        }
                    }
                } else {
                    resultString = "You do not have a " + noun2 + " item.";
                }
                break;

            case "u":
            case "use":
                if (state.player.hasItem(noun) == 0) {
                    if (state.currentLocation.interaction.length() > 0) {
                        switch (noun) {
                            case "slinky":
                            case "coins":
                            case "sword":
                            case "sled":
                            case "raft":
                            case "grapple":
                                if (state.currentLocation.hasPerson(noun2) == 0) {
                                    if (noun2.equalsIgnoreCase("dracheon")) {
                                        if (noun.equalsIgnoreCase("sword")) {
                                            if (state.currentLocation.locationPerson.get(0).hp > 50) {
                                                int dmg = state.randNum(50, 25);
                                                state.currentLocation.locationPerson.get(0).subHp(dmg);
                                                resultString = " You slash Dracheon for " + dmg + " damage!     ";
                                                if (state.player.hp > 30) {
                                                    int dmg2 = state.randNum(30, 15);
                                                    state.player.subHp(dmg2);
                                                    resultString += "[nl][nl]Dracheon claws you for " + dmg2
                                                            + " damage!";
                                                } else {
                                                    ending = "\nYou have been slain by the Mighty Dracheon! (X_X)";
                                                    state.player.hp = 0;
                                                }
                                            } else {
                                                resultString = " You slash Dracheon for "
                                                        + state.currentLocation.locationPerson.get(0).hp
                                                        + " damage!";
                                                state.currentLocation.locationPerson.get(0)
                                                        .subHp(state.currentLocation.locationPerson.get(0).hp);
                                                resultString += state.currentLocation.interaction;
                                                state.currentLocation = state.location[24];
                                            }
                                        } else if (noun.equalsIgnoreCase("slinky")) {
                                            resultString = "Slinky sacrifices their life to defeat Dracheon![nl][nl]You found a way out of the cave that has led you to a beautiful lake.";
                                            state.player.removeItem(noun);
                                            state.currentLocation = state.location[24];
                                        } else if (noun.equalsIgnoreCase("coins")) {
                                            ending = "\nYou have been slain by the Mighty Dracheon because you tried to use "
                                                    + noun + " to fight him! (X_X)";
                                            state.player.hp = 0;
                                        } else {
                                            ending = "\nYou have been slain by the Mighty Dracheon because you tried to use a "
                                                    + noun + " to fight him! (X_X)";
                                            state.player.hp = 0;
                                        }
                                    } else {
                                        resultString = "You cannot use " + noun + " on " + noun2 + " here.";
                                    }
                                } else {
                                    resultString = "There is nothing here to use the " + noun + " on.";
                                }
                                break;
                            default:
                                resultString = "No need to use the " + noun + " on " + noun2 + " here.";
                        }
                    } else {
                        resultString = "You cannot use the " + noun + " on " + noun2 + " here.";
                    }
                } else {
                    resultString = "You do not have a " + noun + " item.";
                }

        }

        System.out.println(formatStringToScreenWidth(resultString));
    }

    /*
     * Prints out the help menu. Goes through all verbs and verbDescriptions
     * printing a list of all commands the user can use.
     */
    public void printHelp() {
        String s1 = "";
        while (s1.length() < DISPLAY_WIDTH)
            s1 += "-";

        String s2 = "";
        while (s2.length() < DISPLAY_WIDTH) {
            if (s2.length() == (DISPLAY_WIDTH / 2 - 10)) {
                s2 += " Commands ";
            } else {
                s2 += " ";
            }
        }

        System.out.println("\n\n" + s1 + "\n" + s2 + "\n" + s1 + "\n");
        for (String v : verbs) {
            // System.out.printp(v + " --> " + verbDescription.get(verbs.indexOf(v)));
            System.out.printf("%-8s  %s", v, formatMenuString(verbDescription.get(verbs.indexOf(v))));
        }
    }

    // Allows the client code to check to see if a verb is in the game.
    public boolean hasVerb(String string) {
        return verbs.contains(string);
    }

    // Allows the client code to check to see if a noun is in the game.
    public boolean hasNoun(String string) {
        return nouns.contains(string);
    }

    // Used to format the help menu
    public String formatMenuString(String longString) {
        String result = "";
        Scanner chop = new Scanner(longString);
        int charLength = 0;

        while (chop.hasNext()) {
            String next = chop.next();
            charLength += next.length();
            result += next + " ";
            if (charLength >= (DISPLAY_WIDTH - 30)) {
                result += "\n          ";
                charLength = 0;
            }
        }
        chop.close();
        return result + "\n\n";
    }

    // formats a string to DISPLAY_WIDTH character width.
    // Used when getting descriptions from items/locations and printing them to the
    // screen.
    // use [nl] for a newline in a string in a description etc.
    // if the ADD_COLOR field is true, anything in []'s will be colored the COLOR
    // field value
    public String formatStringToScreenWidth(String longString) {

        Scanner chop = new Scanner(longString);
        String result = "";
        int charLength = 0;
        boolean addSpace = true;

        while (chop.hasNext()) {

            // Get our next word in the string.
            String next = chop.next();

            // Add the legnth to our charLength.
            charLength += next.length() + 1;

            // Find and replace any special newline characters [nl] with \n.
            if (next.contains("[nl]")) {
                // Find the index after our [nl] characters.
                int secondHalf = next.indexOf("[nl]") + 4;

                // Set charLength to the number of characters after the [nl],
                // because that will be the beginnig of a new line.
                if (secondHalf < next.length()) {
                    charLength = secondHalf;
                } else {
                    charLength = 0;
                    addSpace = false; // Do not add space after if this ended with a newline character.
                }

                // Now actually replace the [nl] with the newline character
                next = next.replace("[nl]", "\n");

            }

            if (ADD_COLOR) {
                Pattern PATTERN = Pattern.compile("\\[(.*?)\\]");
                Matcher m = PATTERN.matcher(next);
                while (m.find()) {
                    String s = m.group(1);
                    // s.toLowerCase();
                    switch (s) {
                        case "Angelica":
                        case "Zelo":
                        case "Isaac":
                        case "Mystique":
                        case "give":
                            next = next.replace("[" + s + "]", COLOR2 + s + RESET);
                            break;
                        case "Dracheon":
                            next = next.replace("[" + s + "]", COLOR + s + RESET);
                            break;
                        case "slinky":
                        case "Slinky":
                        case "coins":
                        case "sword":
                        case "sled":
                        case "raft":
                        case "grapple":
                        case "Sphere":
                        case "use":
                            next = next.replace("[" + s + "]", COLOR3 + s + RESET);
                            break;
                        case "Coins:":
                            next = next.replace("[" + s + "]", COLOR5 + s + RESET);
                            break;
                        case "bb":
                        case "lb":
                        case "eb":
                        case "ob":
                        case "wb":
                        case "bp":
                        case "ff":
                        case "hf":
                        case "ef":
                        case "lf":
                        case "wf":
                        case "mp":
                        case "mm":
                        case "mf":
                        case "mg":
                        case "mb":
                        case "ms":
                        case "pm":
                        case "pr":
                        case "pp":
                        case "hh":
                        case "cc":
                        case "ss":
                        case "cd":
                        case "ll":
                            next = next.replace("[" + s + "]", COLOR4 + s + RESET);
                            break;
                    }
                    // next = next.replace("[" + s + "]", COLOR + s + RESET);
                }
            }
            // Add the word to the result.
            result += next;

            // Only add a space if our special case did not happen.
            if (addSpace)
                result += " ";

            // Normally we add a space after a word, prepare for that.
            addSpace = true;

            if (charLength >= DISPLAY_WIDTH) {
                result += "\n";
                charLength = 0;
            }
        }
        chop.close();
        return result;
    }

    // Adds a noun to the noun list
    // lets the command system know this is something you an interact with.
    public void addNoun(String string) {
        if (!nouns.contains(string.toLowerCase()))
            nouns.add(string.toLowerCase());
    }

    // Adds a verb to the verb list and the description to the parallel description
    // list
    // Adding a verb lets the command system know you want this to be a command.
    public void addVerb(String verb, String description) {
        verbs.add(verb.toLowerCase());
        verbDescription.add(description.toLowerCase());
    }

}
