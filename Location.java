import java.util.*;

/*
Location.java

This class is used to represent the locations the player can move to. 
It can hold persons and items that the player can interact with.
*/

public class Location {
    GameState state;
    String name;
    String description;
    String key;
    String interaction;
    String hint;
    String hint2;
    ArrayList<Item> locationItem = new ArrayList<Item>();
    ArrayList<Person> locationPerson = new ArrayList<Person>();
    ArrayList<Location> exits = new ArrayList<Location>();

    public Location(GameState state, String name, String name_key) {
        this.state = state;
        this.name = name;
        this.key = name_key;
        this.interaction = "";
        this.hint = "";
        state.commandSystem.addNoun(name_key);
    }

    // might not need
    public String getLocation() {
        return name;
    }

    public String getDesc() {
        String result = "";
        if(locationItem.size() == 0 && locationPerson.size() == 0 ){
            result = "Looking around you see the terrain of the " + name + ".[nl][nl]";
        }

        result += description;

        if (!(state.player.hasItem("grapple") == 0) && (state.currentLocation == state.location[1])) {
            result += " and it looks like you need a specific item to get over it...";
        }

        if (!(state.player.hasItem("raft") == 0) && (state.currentLocation == state.location[13])) {
            result += " and it looks like you need a specific item to get over it...";
        }

        if (locationItem.size() > 0) {
            if (state.currentLocation.hasItem("sphere") == 0){
                result += "[nl][nl]Looking around you see the Nexus [" + locationItem.get(0).name + "].";
            } else if (state.currentLocation.hasItem("coins") == 0){
                result += "[nl][nl]Looking around you see Nexus [" + locationItem.get(0).name + "].";
            } else{
                result += "[nl][nl]Looking around you see the [" + locationItem.get(0).name + "].";
            }
        }


        if (exits.size() > 0) {
            result += "[nl][nl]Exits: ";
            for (Location l : exits) {
                result += "[nl]" + l.name + " - [" + l.key + "]";
            }
        }

        return result;
    }

    public Item getItem(String itemName) {
        return locationItem.get(0);
    }

    public void addPerson(Person person) {
        locationPerson.add(person);
        state.commandSystem.addNoun(person.name);
    }

    public void addItem(Item item) {
        locationItem.add(item);
        state.commandSystem.addNoun(item.name);
    }

    /*
     * public int hasItem(String name){ for (int i = 0; i < items.length; i++){ if
     * (items[i].name.equalsIgnoreCase(name)){ return i; } } return -1; }
     */

    public int hasPerson(String name) {
        if (locationPerson.size() > 0) {
            if (locationPerson.get(0).name.equalsIgnoreCase(name)) {
                return 0;
            }
        }
        return 1;
    }

    public int hasItem(String name) {
        if (locationItem.size() > 0) {
            if (locationItem.get(0).name.equalsIgnoreCase(name)) {
                return 0;
            }
        }
        return 1;
    }

    public void addExit(Location exit) {
        exits.add(exit);
        if (!(exit.name.equals("Sacred Stronghold") || exit.name.equals("Dracheon's Cave"))) {
            exit.exits.add(this);
        }
    }

    public String removeItem(String itemName) {
        Item targetItem = getItem(itemName);
        state.player.addToBackpack(targetItem);
        locationItem.remove(targetItem);
        if (targetItem.name.equalsIgnoreCase("coins")) {
            state.player.addAmount(5);
            return "You picked up 5 Nexus [" + targetItem.name + "]";
        } else {
            return "You picked up the [" + targetItem.name + "]";
        }
    }

}
