import mayflower.*;
public class MovingObjects extends AnimatedActor
{
    //Declares all of the instance variables of the enemies
    private Animation walkRight;
    private Animation idle;
    private String currentAction;
    private Animation walkLeft;
    private String direction;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private Timer time;
    //Creates the instance variables
    public MovingObjects(){
        walkRight = null;
        idle = null;
        currentAction = null;
        walkLeft = null;
        idleLeft = null;
        fall = null;
        fallLeft = null;
        //Randomizes the direction of the enemy after sapawn
        if((int)(Math.random() * 2) == 0){
            direction = "right";
        }
        else{
            direction = "left";
        }
    }
    
    public void act(){
        //Gets the coordinates of the moving objects
        super.act();
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        int backgroundWidth = 1164;
        int backgroundLength = 650;
        String newAction = null;
        //Continually walks right until touching the right border
         if(direction.equals("right") && (x + w < backgroundWidth)){
              setLocation(x + 1, y);
              newAction = "walkRight";
              if(isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))
                  setLocation(x - 1, y);
            }
        //Continually walks left until touching the left border
         if(direction.equals("left") && (x > 0)){
              setLocation(x - 1, y);
              newAction = "walkLeft";
              if(isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))
                  setLocation(x + 1, y);
            }
         //Flips the direction if touching left wall
         if(x == 0){
            direction = "right";
            newAction = "walkRight";
         }
         //Flips the direction if touching the right wall
         if(x + w == backgroundWidth){
             direction = "left";
             newAction = "walkLeft";
         }
        
        //Updates the animation of the enemy based on the newAction
        if( newAction != null && !newAction.equals(currentAction)){
            if(newAction.equals("idle"))
                setAnimation(idle);
            if(newAction.equals("walkRight"))
                setAnimation(walkRight);
            if(newAction.equals("walkLeft"))
                setAnimation(walkLeft);
            if(newAction.equals("idleLeft"))
                setAnimation(idleLeft);
            if(newAction.equals("fall"))
                setAnimation(fall);
            if(newAction.equals("fallLeft"))
                setAnimation(fallLeft);

            currentAction = newAction;
        }
   }
   //ALL OF THE MOVINGOBJECTS' SET ANIMATION METHODS
   public void setAnimation(Animation a){
       super.setAnimation(a);
   }
   
   public void setWalkRightAnimation(Animation ani){
       walkRight = ani;
   }
   
   public void setWalkLeftAnimation(Animation ani){
       walkLeft = ani;
   }
   
   public void setIdleAnimation(Animation ani){
       idle = ani;
   }
   
   public void setIdleLeftAnimation(Animation ani){
       idleLeft = ani;
   }
   
   public void setFallAnimation(Animation ani){
       fall = ani;
   }
   
   public void setFallLeftAnimation(Animation ani){
       fallLeft = ani;
   }
}

