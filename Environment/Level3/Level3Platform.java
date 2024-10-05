import mayflower.*;
public class Level3Platform extends Actor
{
   public Level3Platform(){
       //Creates the platform for level3
       MayflowerImage platform = new MayflowerImage("img/Level3Assets/Level3platform.png");
       platform.scale(90, 60);
       platform.crop(0, 0, 90, 18);
       setImage(platform);
       
   }
   
   public void act(){
       
   }
}
