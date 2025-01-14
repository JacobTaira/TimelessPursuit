import mayflower.*;

public class TimelessPursuit extends Mayflower 
{
    //Constructor
    public TimelessPursuit()
    {
        //Create a window with 1664x650 resolution
        super("Timeless Pursuit", 1164, 650);
    }

    public void init()
    {
        //Runs program in full screen mode
        Mayflower.setFullScreen(true);
        World w =  new MyWorld();
        Mayflower.setWorld(w);
    }
}
