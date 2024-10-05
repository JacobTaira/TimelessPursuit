import mayflower.*;
public class Level1 extends World
{
    //Declares all of the instance variables
    private Scientist scientist;
    private String[][] tiles;
    private Caveman caveman;
    private AmmoBox ammo1;
    private AmmoBox ammo2;
    private Part1 part1;
    private Portal portal;
    private HeartBox heart1;
    private Level1Trap trap1;
    private Level1Trap trap2;
    private Level1Trap trap3;
    private Level1Trap trap4;
    private Bolt bolt;
    private Ladder ladder1a;
    private Ladder ladder1b;
    private Ladder ladder2a;
    private Ladder ladder2b;
    private Ladder ladder3a;
    private Ladder ladder3b;
    private Ladder ladder4a;
    private Ladder ladder4b;
    private int totalLives;
    private int totalAmmo;
    private int ammoIndex;
    private int heartIndex;
    private Heart[] hearts;
    private Ammo[] ammos;
    private int spawnTimer;
    private Score score;
    private int count;
    private PowerUp power;
      public Level1(Scientist thing)
    {
        //Constructor that sets the background and creates the tiles for the world. Calls all the methods to create each aspect of the world
        setBackground("img/Level1Assets/Level1bg.png");
        tiles = new String[7][12];
        scientist = thing;
        buildWorld();
        addCharacters();
        addItems();
        addLives();
        addAmmo();
        Mayflower.showBounds(true);
        //Different integers for the methods below, mostly tracking the count of something
        totalLives = scientist.getLives();
        totalAmmo = scientist.getAmmo();
        ammoIndex = 4;
        heartIndex = 2;
        spawnTimer = 500;
        showText("Enemies Killed: " + Scientist.getKills(), 30, 30, Color.BLACK);
    }
    
     public void act(){
        spawnTimer--;
        //If the Scientist gets 10+ kills, standing still, and the "P" key is pressed, the Scientist's speed and damage are boosted and has immunity for 10 seconds.
        if(Mayflower.isKeyDown(Keyboard.KEY_P) && Scientist.getKills() > Scientist.getKillCount() && (scientist.getCurrentAction().equals("idle") || scientist.getCurrentAction().equals("idleLeft"))){
            Scientist.setDamage(2);
            Scientist.setSpeed(5);
            Scientist.setJumpSpeed(6);
            scientist.setImmunity(1000);
            Scientist.setPowerUp(true);
            count = 1000;
            power = new PowerUp();
            addObject(power, 350, 0);
            addObject(new Explosion(), scientist.getX() - 30, scientist.getY() - 30);
            Scientist.increaseKillCount(10);
            System.out.println(Scientist.getKillCount());
        }
        //If the space bar is pressed and the Scientist has ammo, a bolt is fired depending on the direction. Ammo is gradually decreased
         if(attack() && scientist.getAmmo() > 0){
           if(scientist.getDirection().equals("right")){
               bolt = new Bolt();
               addObject(bolt, scientist.getX() + 31, scientist.getY() + 12);
               if(!Scientist.getPowerUp())
                   scientist.increaseAmmo(-1);
            }
           else{
               bolt = new Bolt();
               addObject(bolt, scientist.getX() - 50, scientist.getY() + 12);
               if(!Scientist.getPowerUp())
                   scientist.increaseAmmo(-1);
            }
        }
        //Checks if the Scientist's lives are less than before, then the health icon is decreased. Adjusts the indexes accordingly
        if(scientist.getLives() == totalLives - 1){
           removeObject(hearts[scientist.getLives()]);
           totalLives--;
           heartIndex--;
         }
         //Each ammo icon represents 30 ammo. If the Scientist's ammo is decreased by this factor, one of the ammo icons are removed
         if(scientist.getAmmo() == totalAmmo - 30){
           removeObject(ammos[ammoIndex]);
           totalAmmo -= 30;
           ammoIndex--;
         }
         //If the scientist touches the ammoBox, the ammo is replenished fully
         if(scientist.isTouchingAmmoBox()){
            scientist.setAmmo(150);
            
            while(ammoIndex > -1){
                removeObject(ammos[ammoIndex]);
                ammoIndex--;
            }
            addAmmo();
            ammoIndex = 4;
            totalAmmo = scientist.getAmmo();
        }
        //If the Scientist is touching the heartBox, the Scientist's health is replenished fully
        if(scientist.isTouchingHeartBox()){
            scientist.setLives(3);
            while(heartIndex > -1){
                removeObject(hearts[heartIndex]);
                totalLives--;
                heartIndex--;
            }
            addLives();
            totalLives = scientist.getLives();
            heartIndex = 2;
        }
        //If a part is collected, the parts score increases and the portal spawns in 
        if(scientist.partCollected()){
            score.setOne();
            addObject(score, 815, 0);
            scientist.setPartCollected(true);
            portal = new Portal(scientist);
            addObject(portal, 1078, 35);
        }
        //If the Scientist runs into the portal, the next level is created and the portal is removed
        if(portal != null && portal.isColliding() && scientist.getPartCollected()){
            Mayflower.setWorld(new Level2(scientist));
            scientist.setPartCollected(false);
        }
        //Spawns an enemy every 5 seconds
        if(spawnTimer <= 0){
            spawn();
            spawnTimer = 500;
        }
        //Count for how long the powerUp lasts for
        if(count > -10){
            count--;
        }
        //If the count has ended, the Scientist returns to normal speed and damage and is no longer invincible
        if(count < 0){
            Scientist.setSpeed(3);
            Scientist.setJumpSpeed(5);
            Scientist.setDamage(1);
            removeObject(power);
            Scientist.setPowerUp(false);
        }
    }
    //Uses the preset tiles to build the world with platforms, the ground, and ladders
     public void buildWorld(){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length;j++){
                tiles[i][j] = "";
            }
        }
        
        for(int j = 0; j < tiles[0].length;j++){
             tiles[4][j] = "platform";
             tiles[3][j] = "platform";
             tiles[2][j] = "platform";
             tiles[6][j] = "ground";
            }
        //Checks each row/column of the tiles array to build/not build or shift the the platform/block up/down/left/right
         for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                //Builds the ground
                if(tiles[i][j].equals("ground"))
                    addObject(new Grass(), (j * 97), (i * 100));
                if(tiles[i][j].equals("platform")){
                    //Builds first level of platforms
                    if(i == 2){
                        if(j == 0){
                           addObject(new Level1Platform(), j * 89, (i * 150) - 175);  
                           continue;
                        }
                           
                        if(j == 1 || j == 4 || j == 6)
                           continue;
                        if(j == 5){
                            addObject(new Level1Platform(), j * 89, (i * 150) - 130);  
                            continue;
                        }
                        if(j == 9){
                            addObject(new Level1Platform(), j * 95, (i * 150) - 150);  
                            continue;
                        }
                        if(j == 10 || j == 11){
                            addObject(new Level1Platform(), (j * 89) + 80, (i * 150) - 175  );
                            continue;
                        }
                    }
                    //Builds second level of platforms
                    if(i == 3){
                        if(j == 2 || j == 10 || j == 5 || j == 6)
                            continue;
                        if(j == 11){
                            addObject(new Level1Platform(), j * 86, (i * 150) - 175);  
                            continue;
                        }
                    }
                    //Builds third level of platforms
                    if(i == 4){
                        if(j == 3 || j == 10 || j == 7)
                            continue;
                        if(j == 8 || j == 9){
                            addObject(new Level1Platform(), (j * 89), (i * 150) - 125  );
                            continue;
                        }
                        if(j == 11){
                            addObject(new Level1Platform(), (j * 89) + 89, (i * 150) - 175  );
                            continue;
                        }
                        
                    }
                        addObject(new Level1Platform(), j * 89, (i * 150) - 150); 
                    }
            }
        }
        //Places traps on the world
        trap1 = new Level1Trap();
        trap2 = new Level1Trap();
        trap3 = new Level1Trap();
        trap4 = new Level1Trap();
        addObject(trap1, 800, 292);
        addObject(trap2, 346, 292);
        addObject(trap3, 530, 442); 
        addObject(trap4, 263, 142);
        //Builds ladders on the world
        ladder1a = new Ladder();
        ladder1b = new Ladder();
        addObject(ladder1a, 421, 170);
        addObject(ladder1b, 421, 225);
        ladder2a = new Ladder();
        ladder2b = new Ladder();
        addObject(ladder2a, 599, 300);
        addObject(ladder2b, 599, 375);
        ladder3a = new Ladder();
        ladder3b = new Ladder();
        addObject(ladder3a, 243, 300);
        addObject(ladder3b, 243, 375);
        ladder4a = new Ladder();
        ladder4b = new Ladder();
        addObject(ladder4a, 890, 475);
        addObject(ladder4b, 890, 525);
        //Creates the score for amount of parts collected
        score = new Score();
        score.setZero();
        addObject(score, 815, 0);
    }
    //Creates the scientist with 0 kills and powerUp ability and 4 starting enemies that are preset
    public void addCharacters(){
        scientist = new Scientist();
        caveman = new Caveman();
        Scientist.setKills(0);       
        
        addObject(scientist, 50, 495);
        addObject(caveman, 400, 25);
        addObject(new Caveman(), 350, 495);
        addObject(new Caveman(), 1000, 495);
        addObject(new Caveman(), 712, 320);
        addObject(new Caveman(), 1068, 35);
    }
    //Places 2 ammo boxes and a heart box randomly on the world. Places the part at a pre-set location
    public void addItems(){
        ammo1 = new AmmoBox();
        ammo2 = new AmmoBox();
        heart1 = new HeartBox();
        part1 = new Part1();
        
        boolean addedAmmo1 = false;
        boolean addedAmmo2 = false;
        boolean addedHeart1 = false;
        while(!addedAmmo1){
            int randRow = (int) (Math.random() * 3) + 2;
            int randCol = (int) (Math.random() * tiles[0].length);
            addObject(ammo1, randCol * 89, randRow * 150);
            if(ammo1.isColliding()){
                removeObject(ammo1);
                continue;
            }
            addedAmmo1 = true;
        }
        while(!addedAmmo2){
            int randRow = (int) (Math.random() * 3) + 2;
            int randCol = (int) (Math.random() * tiles[0].length);
            addObject(ammo2, randCol * 89, (randRow * 150) - 75);
            if(ammo2.isColliding()){
                removeObject(ammo2);
                continue;
            }
            addedAmmo2 = true;
        }
        while(!addedHeart1){
            int randRow = (int) (Math.random() * 3) + 2;
            int randCol = (int) (Math.random() * tiles[0].length);
            addObject(heart1, randCol * 89, (randRow * 150) - 75);
            if(heart1.isColliding()){
                removeObject(heart1);
                continue;
            }
            addedHeart1 = true;
        }
        addObject(part1, 1100, 385);
    }
    //Checks if space bar is pressed
    public boolean attack(){
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
            return true;
        return false;
    }
    //Adds the lives icons on the health bars
    public void addLives(){
        int count = 1;
        int xIndex = 610;
       hearts = new Heart[3];
       for(int i = 0; i < hearts.length; i++){
        hearts[i] = new Heart();
      }
        for(int i = 0; count <= 3; i++){
            addObject(hearts[i], xIndex, 3);
            xIndex += 30;
            count++;
        }
    }
    //Adds the ammo icons on the top of the screen
    public void addAmmo(){
        int count = 1;
        int xIndex = 720;
        ammos = new Ammo[5];
        for(int i = 0; i < ammos.length; i++){
            ammos[i] = new Ammo();
        }
        for(int i = 0; count <= 5; i++){
            addObject(ammos[i], xIndex, 3);
            xIndex += 15;
            count++;
        }
    }
    //Spawns an enemy randomly on the top of the screen
    public void spawn(){
        int rand = (int) (Math.random() * tiles.length);
        addObject(new Caveman(), rand * 89, 10); 
    }
}
