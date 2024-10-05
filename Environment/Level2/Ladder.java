import mayflower.*;
public class Ladder extends Actor
{
   public Ladder(){
       //Sets the image for building the ladders on the world
       MayflowerImage ladder = new MayflowerImage("img/Items/Ladder.png");
       ladder.scale(23, 75);
       setImage(ladder);
       
   }
   
   public void act(){
       
   }
}
