import mayflower.*;
public class GravityActor extends Actor
{
    //Always simulates gravity unless the actor is touching a block
   public void act(){
       setLocation(getX(), getY() + 3);
       if(isBlocked())
           setLocation(getX(), getY() - 3);
   }
   //Checks if the actor is touching a block
   public boolean isBlocked(){
       if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))
           return true;
       return false;
   }
   //Checks if the actor is falling or not by calling the isBlocked() method
   public boolean isFalling(){
       boolean ret;
       setLocation(getX(), getY() +3);
       ret = isBlocked();
       setLocation(getX(), getY() - 3);
       return !ret;
   }
}
