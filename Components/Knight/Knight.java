import mayflower.*;
public class Knight extends MovingObjects
{
    //Declares the instance variables for the Knight
   private Animation walkRight;
    private Animation idle;
    private Animation walkLeft;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private int health;
    public Knight(){
        //Creates the animation images for the knight. Sets its health to 25
        health = 25;
        String[] files = new String[7];
        for(int i = 0; i < files.length; i++){
            int index = i + 1;
            files[i] = ("img/Level2Assets/Knight/Run" + index + ".png");
        }
        walkRight = new Animation(50, files);
        walkRight.scale(35, 60);
        setWalkRightAnimation(walkRight);
        
        walkLeft = new Animation(50, files);
        walkLeft.scale(35, 60);
        walkLeft.mirrorHorizontally();
        setWalkLeftAnimation(walkLeft);
        }
        
    public void act()
        {
            super.act();
            //If the knight is touching the scientist, decrease the livs by one. The scientist gains immunity for some time. If lives = 0, losing screen is displayed.
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
       //If the knight is hit by the bolt, decrease its health gradually. If health = 0, the knight dies
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
