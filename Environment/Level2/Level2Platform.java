import mayflower.*;
public class Level2Platform extends Actor
{
   public Level2Platform(){
       MayflowerImage platform2 = new MayflowerImage("img/Level2Assets/Level2platform.png");
        platform2.scale(90, 60);
        platform2.crop(0, 42, 90, 18);
       setImage(platform2);
   }
   
   public void act(){
       
   }
}
