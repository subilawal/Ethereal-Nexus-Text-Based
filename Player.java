import java.util.*;
/*
Player.java

This class represents the player that the user will be in the game.
It is responsible for holding the inventory of items and moving to new locations.
*/

public class Player {
    GameState state;
    String name;
    String description;
    private ArrayList<Item> backpack = new ArrayList<Item>();
    int amount;
    int hp;

    public Player(GameState state) {
        this.state = state;
    }

    public Player(GameState state, String name, String desc) {
        this.state = state;
        this.name = name;
        this.description = desc;
    }

    public Item getItem(String itemName) {
        for (Item i : backpack) {
            if (i.name.toLowerCase().equals(itemName)) {
                return i;
            }
        }
        return null;
    }

    public void addToBackpack(Item i) {
        backpack.add(i);
    }

    public void removeItem(String itemName) {
        Item targetItem = getItem(itemName);
        backpack.remove(targetItem);
    }

    public String printBackpack() {
        String contents = "Current Backpack: ";
        if (backpack.size() > 0) {
            for (Item i : backpack) {
                if(!(i.name.equalsIgnoreCase("coins")))
                contents += "[" + i.name + "] | ";
            }
        }
        contents += "[Coins:] "+amount;
        return contents;
    }

    public int hasItem(String itemName) {
        for (Item i : backpack) {
            if (i.name.equalsIgnoreCase(itemName)) {
                return 0;
            }
        }
        return 1;
    }

    public void addAmount(int value) {
        amount += value;
    }

    public int addRandAmount() {
        Random rnum = new Random();
        int value = rnum.nextInt((11) - 5) + 5;
        amount += value;
        return value;
    }

    public void move(Location dest) {
        state.currentLocation = dest;
    }

    public void subHp(int value){
        hp -= value;
        if (hp < 0){
            hp = 0;
        }
    }

}
