import mayflower.*;
public class PowerUp extends Actor
{
   public PowerUp(){
       //Creates the PowerUp is on text and sets the image
       MayflowerImage power = new MayflowerImage("img/PowerUp.png");
       power.scale(288,160);
       power.crop(0, 0, 200, 35);
       setImage(power);
   }
   
   public void act(){
       
   }
}
