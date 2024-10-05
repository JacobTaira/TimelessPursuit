import mayflower.*;
public class winningScreen extends MyWorld
{
    public winningScreen(){
        //Constructor that sets background to the winning screen
        setBackground("img/EndScreen/Win.png");
    }
    
    public void act(){
        //If ENTER is pressed, the game plays again
       if(startGame())
           Mayflower.setWorld(new MyWorld());
    }
    }
