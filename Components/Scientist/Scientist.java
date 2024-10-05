import mayflower.*;
public class Scientist extends MovableAnimatedActor
{
    //Declares all of the instance variables for the scientist
    private Animation walkRight;
    private Animation idle;
    private Animation walkLeft;
    private Animation idleLeft;
    private Animation fall;
    private Animation fallLeft;
    private Animation jumpRight;
    private Animation jumpLeft;
    private Animation attackRight;
    private Animation attackLeft;
    private Animation climb;
    private int lives;
    private int ammo; 
    private boolean partCollected;
    //Static instance variables for the powerUp and damage taken
    private static int immunity;
    private static int kills = 0;
    private static int speed = 3;
    private static int jumpSpeed = 5;
    private static int damage = 1;
    private static int killCount = 9;
    private static boolean powerUp = false;
    public Scientist(){
        //Creates the instance variables for the scientist
        lives = 3;
        ammo = 150;
        immunity = 0;
        partCollected = false;
        //Creates the walk left and right animation images
        String[] files = new String[4];
        for(int i = 0; i < files.length; i++){
            int index = i + 1;
            files[i] = ("img/Scientist/Run" + index + ".png");
        }
        walkRight = new Animation(50, files);
        walkRight.scale(35, 60);
        walkRight.setBounds(2, 0, 31, 55);
        setWalkRightAnimation(walkRight);
        
        walkLeft = new Animation(50, files);
        walkLeft.mirrorHorizontally();
        walkLeft.scale(35, 60);
        walkLeft.setBounds(2, 0, 31, 55);
        setWalkLeftAnimation(walkLeft);
        //Creates the idle left and right animation images
        String[] idleFiles = new String[4];
        for(int i = 0; i < idleFiles.length; i++){
            int index = i + 1;
            idleFiles[i] = ("img/Scientist/Idle" + index + ".png");
        }
        idle = new Animation(50, idleFiles);
        idle.scale(35, 60);
        idle.setBounds(2, 0, 31, 55);
        setIdleAnimation(idle);
        
        idleLeft = new Animation(50, idleFiles);
        idleLeft.scale(35, 60);
        idleLeft.mirrorHorizontally();
        idleLeft.setBounds(2, 0, 31, 55);
        setIdleLeftAnimation(idleLeft);
        //Creates the fall right and fall left and jump right and jump left animation images
        String[] fallFiles = new String [4];
        for(int i = 0; i < fallFiles.length; i++){
            int index = i + 1;
            fallFiles[i] = ("img/Scientist/Jump" + index + ".png");
        }
        fall = new Animation (50, fallFiles);
        fall.scale(35, 60);
        fall.setBounds(2, 0, 31, 55);
        setFallAnimation(fall);
        
        fallLeft = new Animation(50, fallFiles);
        fallLeft.scale(35, 60);
        fallLeft.setBounds(2, 0, 31, 55);
        fallLeft.mirrorHorizontally();
        setFallLeftAnimation(fallLeft);
        
        jumpRight = new Animation(50, fallFiles);
        jumpRight.scale(35, 60);
        jumpRight.setBounds(2, 0, 31, 55);
        setJumpRightAnimation(jumpRight);
        
        jumpLeft = new Animation(50, fallFiles);
        jumpLeft.scale(35, 60);
        jumpLeft.setBounds(2, 0, 31, 55);
        jumpLeft.mirrorHorizontally();
        setJumpLeftAnimation(jumpLeft);
        //Creates the attack right and attack left animation imageds
        String[] attackFiles = new String[1]; 
        for(int i = 0; i < attackFiles.length; i++){
            attackFiles[i] = ("img/Scientist/Attack4.png");
        }
        attackRight = new Animation (50, attackFiles);
        attackRight.scale(35,60);
        attackRight.mirrorHorizontally();
        attackRight.setBounds(2, 0, 31, 55);
        setAttackLeftAnimation(attackRight);
        
        attackLeft = new Animation (50, attackFiles);
        attackLeft.scale(35,60);
        attackLeft.setBounds(2, 0, 31, 55);
        setAttackRightAnimation(attackLeft);
        //Creates the climbing animation for the ladders
        String[] climbFiles = new String[4];
        for(int i = 0; i < climbFiles.length; i++){
            int index = i + 1;
            climbFiles[i] = ("img/Scientist/Climb" + index + ".png");        
        }
        climb = new Animation (50, climbFiles);
        climb.scale(25,50);
        setClimbAnimation(climb);
        }
        
    public void act()
        {
            //Creates a brief immunity time after taking damage
            super.act();
            if(immunity > -100){
              immunity--;  
            }
      }
    //Increasing lives method
    public void increaseAmmo(int num){
        ammo += num;
    }
    //Returning lives method
    public int getLives(){
        return lives;
    }
    //Returning ammo method
    public int getAmmo(){
        return ammo;
    }
    //Increasing lives method
    public void increaseLives(int num){
        lives += num;
    }
    //Setting lives method
    public void setLives(int num){
        lives = num;
    }
    //Setting ammo method
    public void setAmmo(int num){
        ammo = num;
    }
    //Checks if the part has been collected or not
    public boolean getPartCollected(){
        return partCollected;
    }
    //Sets the part collected to be true or false
    public void setPartCollected(boolean thing){
        partCollected = thing;
    }
    //Sets the time for immunity after taking damage
    public void setImmunity(int num){
        immunity = num;
    }
    //Returns if the scientist has immunity or not
    public boolean getImmunity(){
        if(immunity > 0)
            return true;
        return false;
    }
    //Checks if the scientist is touching the ammo box. If so, remove the ammo box and return true. Else return false
    public boolean isTouchingAmmoBox(){
        if(isTouching(AmmoBox.class)){
            Object a = getOneIntersectingObject(AmmoBox.class);
            AmmoBox ammo = (AmmoBox) a;
            World w = getWorld();
            w.removeObject(ammo);
            return true;
       }
       return false;
    }
    //Checks if the scientist is touching the heart box. IF so, remove the health box and return true. Else return false
    public boolean isTouchingHeartBox(){
        if(isTouching(HeartBox.class)){
            Object a = getOneIntersectingObject(HeartBox.class);
            HeartBox heart = (HeartBox) a;
            World w = getWorld();
            w.removeObject(heart);
            return true;
        }
        return false;
    }
    //Checks if the scientist is touching any of the parts. 
    public boolean partCollected(){
        if(isTouching(Part1.class)){
            Object a = getOneIntersectingObject(Part1.class);
            Part1 part = (Part1) a;
            World w = getWorld();
            w.removeObject(part);
            return true;
        }
        if(isTouching(Part2.class)){
            Object a = getOneIntersectingObject(Part2.class);
            Part2 part = (Part2) a;
            World w = getWorld();
            w.removeObject(part);
            return true;
        }
        if(isTouching(Part3.class)){
            Object a = getOneIntersectingObject(Part3.class);
            Part3 part = (Part3) a;
            World w = getWorld();
            w.removeObject(part);
            return true;
        }
        return false;
    }
    //Setter and getter for kills
    public static void increaseKills(){
        kills++;
    }
    
    public static int getKills(){
        return kills;
    }
    
    public static void setKills(int num){
        kills = num;
    }
    //Setter and getter for damage
    public static void setDamage(int num){
        damage = num;
    }
    
    public static int getDamage(){
        return damage;
    }
    //Setter and getter for speeds
    public static void setSpeed(int num){
        speed = num;
    }
    
    public static int getSpeed(){
        return speed;
    }
    
    public static void setJumpSpeed(int num){
        jumpSpeed = num;
    }
    
    public static int getJumpSpeed(){
        return jumpSpeed;
    }
    //Setter and getter for total kill count to keep track of the powerUp kill threshold
    public static int getKillCount(){
        return killCount;
    }
    
    public static void increaseKillCount(int num){
        killCount += num;
    }
    //Setter and getter to keep track of powerUp
    public static boolean getPowerUp(){
        return powerUp;
    }
    
    public static void setPowerUp(boolean bool){
        powerUp = bool;
    }
}
