import mayflower.*;
public class MyWorld extends World {
    private static Scientist scientist;
    //Creates the scientist and displays the title screen
    
    public MyWorld() 
    {
        setBackground("img/TitleScreen/TitleScreen.png");
       scientist = new Scientist();
        Mayflower.showBounds(false);
        }
    
    public void act()
    {
        //If the ENTER key is pressed, Level 1 is created
       if(startGame()){
           Mayflower.setWorld(new Level1(scientist));
        }
    } 
    //Checks if the ENTER key is pressed
    public boolean startGame(){
        if(Mayflower.isKeyDown(Keyboard.KEY_ENTER))
            return true;
        return false;
    }
   
    //Returns the original scientist
    public static Scientist getScientist(){
        return scientist;
    }
    
}