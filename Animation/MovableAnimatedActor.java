import mayflower.*;
public class MovableAnimatedActor extends AnimatedActor
{
   //Declares all instance variables
    private Animation walkRight;
    private Animation idle;
    private String currentAction;
    private Animation walkLeft;
    private String direction;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private Animation jumpRight;
    private Animation jumpLeft;
    private Animation attackRight;
    private Animation attackLeft;
    private Animation climb;
    private int jumpCount;
    
    //Constructor for all actions
    public MovableAnimatedActor(){
        walkRight = null;
        idle = null;
        currentAction = null;
        walkLeft = null;
        idleLeft = null;
        fall = null;
        fallLeft = null;
        direction = "right";
        attackRight = null;
        attackLeft = null;
        climb = null;
        jumpCount = 20;
    }
    
    public void act(){
        //Gets the actor's coordinates
        super.act();
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        int backgroundWidth = 1164;
        int backgroundLength = 650;
        String newAction = null;
        if(currentAction == null){
            newAction = "idle";
        }
        
        //Moves right if key is pressed, jumps to the right if up is pressed, actor can't run through a wall/off the screen
        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && (x + w < backgroundWidth)){
            setLocation(x + Scientist.getSpeed(), y);
            direction = "right";
            newAction = "walkRight";
            if(isFalling()){
                newAction = "fall";
            }
            if(jumpCount >= 0 && Mayflower.isKeyDown(Keyboard.KEY_UP) && (y > 0)){
                setLocation(x + Scientist.getSpeed(), y - Scientist.getJumpSpeed());
                jumpCount--;
                newAction = "jumpRight";
            }
            if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))
                setLocation(x - Scientist.getSpeed(), y);
        }
        //Moves left if key is pressed, jumps to the left if up is pressed, actor can't run through a wall/off the screen
        else  if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && (x > 0)){
            setLocation(x - Scientist.getSpeed(), y);
            direction = "left";
            newAction = "walkLeft";
            if(isFalling()){
                newAction = "fallLeft";
            }
            if(jumpCount >= 0 && Mayflower.isKeyDown(Keyboard.KEY_UP) && (y > 0)){
                setLocation(x - Scientist.getSpeed(), y -  Scientist.getJumpSpeed());
                jumpCount--;
                newAction = "jumpLeft";
            }
            if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))                
                setLocation(x + Scientist.getSpeed(), y);
        }
        //Jumps straight up for a period of time if key is pressed
        else if (jumpCount >= 0 && Mayflower.isKeyDown(Keyboard.KEY_UP) && !isTouching(Ladder.class) && !(Mayflower.isKeyDown(Keyboard.KEY_RIGHT) || Mayflower.isKeyDown(Keyboard.KEY_LEFT)) && (y > 0)){
            setLocation(x, y -  Scientist.getJumpSpeed()); 
            jumpCount--;
            if(direction.equals("right"))
                newAction = "jumpRight";
            else
                newAction = "jumpLeft";
            if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))
                setLocation(x, y + 2);
        }
        //Climbs the ladder if up is pressed. 
        else if(isTouching(Ladder.class) && Mayflower.isKeyDown(Keyboard.KEY_UP) && !(Mayflower.isKeyDown(Keyboard.KEY_RIGHT) || Mayflower.isKeyDown(Keyboard.KEY_LEFT))){
            setLocation(x, y -  Scientist.getJumpSpeed());
            newAction = "climb";
        }
        //Actor falls down if key is pressed, actor can't fall through the floor/off the screen
        else  if (Mayflower.isKeyDown(Keyboard.KEY_DOWN) && (h + y < 650)){
            setLocation(x, y + Scientist.getSpeed()); 
            newAction = "walkDown";
            if(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))                
                setLocation(x, y - Scientist.getSpeed());
        }
        //Checks if the actor is falling and sets the action to left/right depending on direction
        else if(isFalling() && !(isTouching(Grass.class) || isTouching(Cobblestone.class) || isTouching(Metal.class) || isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class))){
            if(direction.equals("right"))
                newAction = "fall";
            else
                newAction = "fallLeft";
        }
        //Displays the attack animation if SPACE is pressed
        else if(Mayflower.isKeyDown(Keyboard.KEY_SPACE)){
            if(direction.equals("right")){
                newAction = "attackRight";
            }
            else{
                newAction = "attackLeft";
            }
        }
        //If nothing is pressed, the actor idles
        else {
            newAction = "idle";
            if(direction != null && direction.equals("left")){
                newAction = "idleLeft";
            }
        }
        //Maks sure actor doesn't get stuck in the platform
        if((isTouching(Level1Platform.class) || isTouching(Level2Platform.class) || isTouching(Level3Platform.class)) && isTouching(Ladder.class) && (!Mayflower.isKeyDown(Keyboard.KEY_UP))){
            if(Mayflower.isKeyDown(Keyboard.KEY_RIGHT))
                setLocation(x - 3, y);
            else if (Mayflower.isKeyDown(Keyboard.KEY_LEFT))
                setLocation(x + 3, y);
            setLocation(x, y + 3);
        }
        
        //Sets the new animation appropriately
        if( newAction != null && !newAction.equals(currentAction)){
            if(newAction.equals("idle")){
                setAnimation(idle);
                jumpCount = 20;
            }
            else if(newAction.equals("walkRight")){
                setAnimation(walkRight);
                jumpCount = 20;
            }
            else if(newAction.equals("walkLeft")){
                setAnimation(walkLeft);
                jumpCount = 20;
            }
            else if(newAction.equals("idleLeft")){
                setAnimation(idleLeft);
                jumpCount = 20;
            }
            else if(newAction.equals("jumpRight")){
                setAnimation(jumpRight);
            }
            else if(newAction.equals("jumpLeft")){
                setAnimation(jumpLeft);
            }
            else if(newAction.equals("fall")){
                setAnimation(fall);
                jumpCount = -1;
            }
            else if(newAction.equals("climb")){
                setAnimation(climb);
                jumpCount = -1;
            }
            else if(newAction.equals("fallLeft")){
                setAnimation(fallLeft);
                jumpCount = -1;
            }
            else if(newAction.equals("attackRight")){
                setAnimation(attackRight);
                jumpCount = 20;
            }
            else if(newAction.equals("attackLeft")){
                setAnimation(attackLeft);
                jumpCount = 20;
            }
            currentAction = newAction;
        }
   }
   //Returns what the currentAction is
   public String getCurrentAction(){
       return currentAction;
   }
   
   //ALL OF THE SET ANIMATION METHODS FOR THE MOVABLE ACTOR
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
   
   public void setJumpRightAnimation(Animation ani){
       jumpRight = ani;
   }
   
   public void setJumpLeftAnimation(Animation ani){
       jumpLeft = ani;
   }
   
   public void setFallLeftAnimation(Animation ani){
       fallLeft = ani;
   }
   
   public void setAttackRightAnimation(Animation ani){
       attackRight = ani;
   }
   
   public void setAttackLeftAnimation(Animation ani){
       attackLeft = ani;
   }
   
   public void setClimbAnimation(Animation ani){
       climb = ani;
   }
   
   public String getDirection(){
       return direction;
   }
}

