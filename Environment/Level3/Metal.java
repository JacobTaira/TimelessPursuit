import mayflower.*;
public class Metal extends Actor
{
    public Metal(){
        //Constructor that creates the ground for level3
       MayflowerImage block = new MayflowerImage("img/Level3Assets/Level3ground.png");
       block.scale(100,100);
       setImage(block);
      
    }
    
    public void act(){
        
    }
}
