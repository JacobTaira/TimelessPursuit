import mayflower.*;
public class Ammo extends Actor
{
   public Ammo(){
       //Creates the ammo icon for the top of the screen
       MayflowerImage ammo = new MayflowerImage("img/Items/Ammo.png");
       ammo.scale(25,25);
       ammo.crop(6, 0, 9, 25);
       setImage(ammo);
   }
   
   public void act(){
       
   }
}
