import java.util.ArrayList;

/*
Item.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class represents an item that can be interacted with by the player. They are stored in locations.
*/
public class Item {
    GameState state;
    String name;
    boolean embeded;
    String shortDesc;
    String description;
    ArrayList<String> status = new ArrayList<String>();

    public Item (GameState state, String name){
        this.state = state;
        this.name = name;
    }
    
    public Item(String n, String d){
        name = n;
        embeded = false;
        shortDesc = d;
    }
}
