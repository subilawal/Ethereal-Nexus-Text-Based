public class Currency {

    GameState state;
    String name;
    String description;
    int amount;

    public Currency(GameState state, String name, String desc){
        this.state = state;
        this.name = name;
        this.description = desc;
        amount = 20;
    }

    public void addAmount(int value){
        amount += value;
    }

    public void randAddAmount(){
        //
    }
    
}
