import mayflower.*;

public class Fire extends AnimatedActor
{
    
    public Fire() 
    //Creates the animation for the fire trap
    {
        String[] pics = new String[6];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = new String("img/Level3Assets/Fire/Explosion_" + (i + 1) + ".png");
        }
        Animation fiery = new Animation(50,pics);
        fiery.scale(20,18);
        
        setAnimation(fiery);
    }
    public void act()
    {
        //Makes sure fire doesn't fall through the ground
        super.act();
        int x = getX();
        int y = getY();
        if(isTouching(Metal.class) || isTouching(Level3Platform.class)){
            setLocation(x, y - 3);
        }
        //If the scientist is touching the fire, he takes damage but gains immunity for a brief amount of time. If lives = 0, the losing screen is displayed
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
