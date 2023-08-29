public class Person {

/*
Person.java

This class is used to represent the persons that the player can interact with. They are stored in locations.
*/
    GameState state;
    String name;
    String description;
    String statement;
    String statement2;
    int counter;
    int counter2;
    int hp;


    public Person(GameState state){
        this.state = state;
    }
    public Person(GameState state, String name, String desc){
        this.state = state;
        this.name = name;
        this.description = desc;
        this.counter = 0;
    }

    public void subHp(int value){
        hp -= value;
        if (hp < 0){
            hp = 0;
        }
    }

}
