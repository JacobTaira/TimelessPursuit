import mayflower.*;
public class Alien extends MovingObjects
{
    //Declares all of the instance variables for the Alien
    private Animation walkRight;
    private Animation idle;
    private Animation walkLeft;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private int health;
    public Alien(){
        //Creates the animation images for the alien. Sets the health to 35
        health = 35;
        String[] files = new String[4];
        for(int i = 0; i < files.length; i++){
            int index = i + 1;
            files[i] = ("img/Level3Assets/Aliens/Alien" + index + ".png");
        }
        walkRight = new Animation(50, files);
        walkRight.scale(35, 60);
        walkRight.setBounds(3, 0, 29, 56);
        setWalkRightAnimation(walkRight);
        
        walkLeft = new Animation(50, files);
        walkLeft.scale(35, 60);
        walkLeft.mirrorHorizontally();
        walkLeft.setBounds(0, 2, 35, 56);
        setWalkLeftAnimation(walkLeft);
        }
        
    public void act()
        {
            super.act();
            //If the alien is touching the scientist, decrease lives by one. The scientist gains immunity for some time. If lives = 0, losing screen is displayed.
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
       //If the alien is hit by the bolt, decrease its health gradually. If health = 0, the alien dies.
            if(isTouching(Bolt.class)){
                health -= Scientist.getDamage();
                if(health <= 0){
                    World w = getWorld();
                    w.removeObject(this);
                    Scientist.increaseKills();
                    w.removeText(30,30);
                    w.showText("Enemies Killed: " + Scientist.getKills(), 30, 30, Color.BLACK);
                }
            }
      }
}