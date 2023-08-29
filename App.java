
/* UML document for starter code:
    https://lucid.app/lucidchart/285ca85b-58f3-40e4-a98a-4d54bd2fc03d/view?page=0_0#


App.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the driver class of the project. It is in charge of the following:

- Setting up the game by creating the GameState.
- The main game loop deciding when the game quits.
- The input from the user and routing to the correct methods in the CommandSystem.

*/

import java.util.*;

public class App {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        // Create needed objects for the game.
        GameState state = new GameState();
        // Store the command system for easy reference in the client code.
        CommandSystem commandSystem = state.commandSystem;

        // This controls if the game should continue running.
        boolean gameRunning = true;

        // INSERT INTRODUCTION AND TITLE SCREEN
        String[][] star = { {
                "                       *                                                                                             " },
                { "                                                                  *                                                  " },
                { "            *                                                                          *                              " },
                { "      *     *    *                  *                                                                                  " },
                { "        *   *   *                                                                              *                        " },
                { "          * * *                                                 *                *                               " },
                { "   * * * * * * * * * *                    *                                                                     " },
                { "          * * *                                   *                                  *                        " },
                { "        *   *   *                                * *                             *   *   *                          " },
                { "      *     *     *                             *   *                              * * *                         " },
                { "            *                                  *     *                       * * * * * * * * *                       " },
                { "                              * * * * * * * * *       * * * * * * * * *            * * *                          " },
                { "                                 *                                 *             *   *   *                 " },
                { "      *                             *                           *                    *                             " },
                { "                                       *                     *                                                      " },
                { "                                          *               *                                      *" },
                { "             *                           *                 *               *                                 " },
                { "                                        *         *         *                               *                 " },
                { "                                       *      *       *      *                                                " },
                { "       *                              *   *               *   *                *                                " },
                { "                                     *                         *                                                " },
                { "                     *                                                                      *" },
                { "  *                        " },
                { "         *     *   *******  *******  *     *  *******  *******  *******      *      *             *               " },
                { "        *********  *           *     *     *  *        *    *   *           * *     *         *********                   " },
                { "          *****    *****       *     *******  *****    * * *    *****      *   *    *           ** **             " },
                { "  *         *      *           *     *     *  *        *    *   *         *******   *          *     *        " },
                { "                   *******     *     *     *  *******  *     *  *******  *       *  *******                             " },
                {},
                { "                               *     *  *******  *       *  *     *   ******               " },
                { "                               **    *  *         *     *   *     *  *            " },
                { "                               *  *  *  *****        *      *     *   *****      " },
                { "                               *    **  *         *     *   *     *        *       " },
                { "                               *     *  *******  *       *  *******  ******                 " },
                { "                                                                     " } };

        for (int i = 0; i < star.length; i++) {
            for (int j = 0; j < star[i].length; j++) {
                System.out.print(star[i][j] + " ");
            }
            System.out.println();
        }

        String welcomeStatement = "\nWelcome to the Ethereal Nexus! \n\nYou are a treasure hunter named Vyn Wilkes.\nYou just crash landed at the "
                + state.currentLocation.name
                + " on the planet, \nEthereal Nexus, in search of the fabled Nexus Sphere.\nYou exit your spacecraft with your loyal slynderof, Slinky, who hangs out of your backpack.\nYou see a vast variety of locations to explore. \nYou begin your search for the Nexus Sphere to claim the greatest treasure in the universe!";

        System.out.println(welcomeStatement);

        // The main game loop.
        while (gameRunning) {

            // Gets input from the user in an array of strings that they typed in.
            if (state.player.hasItem("sphere") == 0 || state.player.hp == 0) {
                for (int i = 0; i < star.length; i++) {
                    for (int j = 0; j < star[i].length; j++) {
                        System.out.print(star[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println(state.commandSystem.ending + "\n\nThank you for playing Ethereal Nexus!\n" );
                gameRunning = false;
                continue;
            }

            String[] input = getCommand(state);

            if (input.length < 1) {
                System.out.println("Unknown command. Type ? for help.");

            } else if (input[0].equals("quit")) {
                gameRunning = false;
                System.out.println("Goodbye.");
                in.close();

                // Command has 1 word - Check if it is a valid verb and execute it.
            } else if (input.length == 1 && commandSystem.hasVerb(input[0])) {
                commandSystem.executeVerb(input[0]);

                // Command has 2 words - should be verb and noun.
            } else if (input.length == 2) {
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) {
                    unknownCommand(input[0]);
                } else if (!commandSystem.hasNoun(input[1])) {
                    unknownCommand(input[1]);
                } else {
                    // Run command
                    commandSystem.executeVerbNoun(input[0], input[1]);
                }

                // command has 3 words - should be verb noun noun
            } else if (input.length == 3) {
                // Validate that the commands are known verb/nouns
                if (!commandSystem.hasVerb(input[0])) {
                    unknownCommand(input[0]);
                } else if (!commandSystem.hasNoun(input[1])) {
                    unknownCommand(input[1]);
                } else if (!commandSystem.hasNoun(input[2])) {
                    unknownCommand(input[2]);
                } else {
                    // Run command
                    commandSystem.executeVerbNounNoun(input[0], input[1], input[2]);
                }

                // Deal with any possible unknown structure/command
            } else {
                if (input.length > 1) {
                    String userInput = "";

                    for (String s : input)
                        userInput += s + " ";

                    unknownCommand(userInput);

                } else {
                    unknownCommand(input[0]);
                }
            }
        }

    }

    // Gets input from the user
    // seperates the input into each word (determined by whitespace)
    // returns an array with each word an element of the array.
    public static String[] getCommand(GameState state) {

        in = new Scanner(System.in);
        System.out.println("\n------------------------------");
        System.out.println("Current Location: " + state.currentLocation.name + "\n"); // Temp
        if (state.currentLocation == state.location[23]) {
            System.out.println("--------------------");
            System.out.println("     BOSS FIGHT     ");
            System.out.println("--------------------");
            System.out.println(" Dracheon's HP: " + state.currentLocation.locationPerson.get(0).hp);
            System.out.println("--------------------");
            System.out.println("    Vyn's HP: "+ state.player.hp);
            System.out.println("--------------------\n");
        }
        System.out.print("What would you like to do? (Press ? for help) >  ");
        String input = in.nextLine();
        System.out.println();

        return input.toLowerCase().split("\\s+");
    }

    // Used to let the user know that what they typed as a command is not
    // understood.
    public static void unknownCommand(String input) {
        if (Math.random() < .3) // A random chance for a silly response.
            System.out.println("Don't be silly. Everyone knows '" + input + "' is not a command! Type ? for help.");
        else
            System.out.println("I don't understand '" + input + "'. Type ? for help.");
    }

}