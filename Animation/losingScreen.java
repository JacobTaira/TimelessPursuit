import mayflower.*;
public class losingScreen extends MyWorld
{
    public losingScreen(){
        //Constructor that sets the background to the losing screen
        setBackground("img/EndScreen/Death.png");
    }
    
    public void act(){
        //If ENTER is pressed, the game plays again
       if(startGame())
           Mayflower.setWorld(new MyWorld());
    }
}
