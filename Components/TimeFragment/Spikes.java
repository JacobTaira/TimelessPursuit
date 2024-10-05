import mayflower.*;

public class Spikes extends GravityActor
{

    public Spikes()
    {
         MayflowerImage trap = new MayflowerImage("img/Level2Assets/Level2poison.png");
         trap.scale(37,20);
         trap.crop(0, 12, 37, 8);
         setImage(trap);
    }

     public void act(){
       super.act();
       //If touching the Scientist, the number of lives decreases by one and the Scientist has immunity for a bit of time before taking damage again. If the lives is 0, the losing screen is displayed
       if(isTouching(Scientist.class)){
            Object a = getOneIntersectingObject(Scientist.class);
            Scientist scientist = (Scientist) a;
            World w = getWorld();
            if(scientist.getImmunity()){
                scientist.increaseLives(1);
            }
            scientist.increaseLives(-1);
            if(scientist.getLives() <= 0){
                 scientist.act();
                w.removeObject(scientist);
                 Mayflower.setWorld(new losingScreen());
             }
            if(scientist.getImmunity()){
                
            }
            else
                scientist.setImmunity(100);
                
       }
    }
   
}
