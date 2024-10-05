import mayflower.*;
public class HeartBox extends Actor
{
   public HeartBox(){
       //Creates the image for the heartBox
       MayflowerImage heart = new MayflowerImage("img/Items/HeartBox.png");
       heart.scale(25,25);
       setImage(heart);
       
   }
   
   public void act(){
       
   }
   //Checks if the heartBox is touching anything else on the world
   public boolean isColliding(){
       if(isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class) || isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(HeartBox.class) || isTouching(Ladder.class) || isTouching(Portal.class) || isTouching(Part1.class) || isTouching(Part2.class) || isTouching(Part3.class))
       return true;
       return false;
    }
}
