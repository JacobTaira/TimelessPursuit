import mayflower.*;
public class AmmoBox extends Actor
{
   public AmmoBox(){
       //Creates the image for the ammoBox
       MayflowerImage ammo = new MayflowerImage("img/Items/AmmoBox.png");
       ammo.scale(25,25);
       setImage(ammo);
       
   }
   
   public void act(){
       
   }
   //Checks if the ammoBox is touching anything else on the world
   public boolean isColliding(){
       if(isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class) || isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(HeartBox.class) || isTouching(Ladder.class) || isTouching(Portal.class) || isTouching(Part1.class) || isTouching(Part2.class) || isTouching(Part3.class))
       return true;
       return false;
    }
}
