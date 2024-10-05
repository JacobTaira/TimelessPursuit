import mayflower.*;
public class Caveman extends MovingObjects
{
    //Declares the instance variables for the enemy movement
    private Animation walkRight;
    private Animation idle;
    private Animation walkLeft;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private int health;
    public Caveman(){
        //Creates the image animations for the Caveman. Sets health to 15
        health = 15;
        String[] files = new String[6];
        for(int i = 0; i < files.length; i++){
            int index = i + 1;
            files[i] = ("img/Level1Assets/Caveman/Caveman" + index + ".png");
        }
        walkRight = new Animation(50, files);
        walkRight.scale(35, 60);
        walkRight.mirrorHorizontally();
        walkRight.setBounds(0, 2, 35, 56);
        setWalkRightAnimation(walkRight);
        
        walkLeft = new Animation(50, files);
        walkLeft.scale(35, 60);
        walkLeft.setBounds(0, 2, 35, 56);
        setWalkLeftAnimation(walkLeft);
        }
        
    public void act()
        {
            super.act();
            //If the caveman is touching the scientist, lives decreases by one. The scientist gets immunity for some time. IF lives = 0, losing screen is displayed
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
       //If the caveman is hit by the bolt, decrease its health. If health = 0, the Caveman dies
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
