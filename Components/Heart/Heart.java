import mayflower.*;
public class Heart extends Actor
{
   public Heart(){
       //Creates the heart icon for the top of the screen
       MayflowerImage heart = new MayflowerImage("img/Items/Heart.png");
       heart.scale(25,25);
       setImage(heart);
   }
   
   public void act(){
       
   }
}
