import mayflower.*;
public class Bolt extends Actor
{
    private int count;
    private MayflowerImage bolt;
    private Scientist scientist;
   public Bolt(){
       //Creates the bolt icon for when the scientist attacks. Checks if the direction should be left or right
       scientist = MyWorld.getScientist();
       bolt = new MayflowerImage("img/Scientist/AttackEffect.png");
       bolt.scale(50,15);       
       if(scientist.getDirection().equals("right"))
           bolt.mirrorHorizontally();
       setImage(bolt);   
       count = 5;
   }
   
   public void act(){
       //Makes the bolt appear only temporarily after the SPACE bar is pressed
      if(bolt != null){
          count--;
      }
      if(count == 0){
          World w = getWorld();
          w.removeObject(this);
      }
   }
   
   
}
