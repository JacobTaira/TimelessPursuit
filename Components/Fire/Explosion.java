import mayflower.*;

public class Explosion extends AnimatedActor
{
    private int count;
    private Animation explode;
    public Explosion() 
    //Creates the animation for the Explosion
    {
        String[] pics = new String[9];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = new String("img/Items/Explosion_blue_circle/Explosion_blue_circle" + (i + 1) + ".png");
        }
        Animation explode = new Animation(50,pics);
        explode.scale(150,150);
        explode.setBounds(30, 40, 85, 75);
        setAnimation(explode);
        //Sets counter for how long the explosion should be on screen
        count = 40;
    }
    public void act()
    {
        super.act();
        //Makes sure Explosion doesn't fall through the ground
        if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class)){
            setLocation(getX(), getY()- 3);
        }
         //Counts down the timer
         if(count > -1000){count--;}
         //If the timer isDone, remove the explosion
       if(count <= 0){
          World w = getWorld();
          w.removeObject(this);
        }
        
    }
}
