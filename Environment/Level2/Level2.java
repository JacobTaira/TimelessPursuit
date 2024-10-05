import mayflower.*;
public class Level2 extends World
{
    //Declares all of the instance variables
    private Scientist scientist;
    private String[][] tiles;
    private Knight knight;
    private AmmoBox ammo1;
    private AmmoBox ammo2;
    private Part2 part2;
    private Portal portal;
    private HeartBox heart1;
    private Spikes trap1;
    private Spikes trap2;
    private Spikes trap3;
    private Spikes trap4;
    private Bolt bolt;
    private Ladder ladder1a;
    private Ladder ladder1b;
    private Ladder ladder2a;
    private Ladder ladder2b;
    private Ladder ladder2c;
    private Ladder ladder2d;
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
      public Level2(Scientist thing)
    {
        //Constructor that sets the background and creates the tiles for the world. Calls all the methods to create each aspect of the world
        setBackground("img/Level2Assets/Level2bg.png");
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
            score.setTwo();
            addObject(score, 815, 0);
            scientist.setPartCollected(true);
            portal = new Portal(scientist);
            addObject(portal, 15, 50);
        }
        //If the Scientist runs into the portal, the next level is created and the portal is removed
        if(portal != null && portal.isColliding() && scientist.getPartCollected()){
            Mayflower.setWorld(new Level3(scientist));
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
        
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                if(tiles[i][j].equals("ground")) addObject(new Cobblestone(), (j * 97), (i * 100));
                    
                if(tiles[i][j].equals("platform")){
                    if(i == 2){
                       if(j== 1 || j == 3 || j==6 || j== 10 || j==11){
                        continue;
                        }
                       if(j==2){
                         addObject(new Level2Platform(), (j * 89) - 10, (i * 150) - 175);
                         continue;
                       }
                       if(j == 4 || j == 5){
                          addObject(new Level2Platform(), (j * 89) - 10, (i * 150) - 150); 
                          continue;
                       }
                       if(j==7){
                        addObject(new Level2Platform(), (j * 89)-55, (i * 150)-115);
                        continue;
                        }
                       if(j==8){
                        addObject(new Level2Platform(), (j * 89)- 10, (i * 150)-75);
                        continue;
                       }
                       if(j==9){
                         addObject(new Level2Platform(), (j * 89) + 50, (i * 150)-40);
                         continue;
                        }
                    }
                    
                    if(i == 3){
                      if(j==1){
                        addObject(new Level2Platform(), (j * 89)-10, (i * 150)-130);
                         continue;
                      }
                      if(j != 3){
                          if(j==11){}
                          else {continue;}
                      }
                      if(j != 11){
                          if(j==3){}
                          else {continue;}
                      }
                    }
                    
                    if(i == 4){
                      if(j==0 || j==1 || j == 3 || j == 4 || j==7 || j==9){
                          continue;
                      }
                      if(j==11){
                        addObject(new Level2Platform(), (j * 89) + 89, (i * 150) - 175);
                        continue;
                      }
                      if(j==2){
                        addObject(new Level2Platform(), (j * 89), (i * 150) - 125);
                        continue; 
                      }
                      if(j==8){
                        addObject(new Level2Platform(), (j * 89), (i * 150) - 150); 
                        continue;
                      }
                    }
                    
                    addObject(new Level2Platform(), j * 89, (i * 150) - 150); 
                 }
            }
        }
        
        trap1 = new Spikes();
        trap3 = new Spikes();
        trap4 = new Spikes();
        addObject(trap1, 400, 140);
        addObject(trap3, 530, 442); 
        addObject(trap4, 133, 142);

        ladder2a = new Ladder();
        ladder2b = new Ladder();
        ladder2c = new Ladder();
        ladder2d = new Ladder();
        addObject(ladder2a, 250, 300);
        addObject(ladder2b, 250, 375);
        addObject(ladder2c, 1070, 300);
        addObject(ladder2d, 1070, 350);
        
        ladder3a = new Ladder();
        addObject(ladder3a, 178-25, 475);
        
        ladder4b = new Ladder();
        addObject(ladder4b, 178-25, 525);
        
        score = new Score();
        score.setOne();
        addObject(score, 815, 0);
    }
    //Creates the scientist with powerUp ability and 4 starting enemies that are preset
    public void addCharacters(){
        scientist = new Scientist();
        knight = new Knight();       
        
        addObject(scientist, 50, 495);
        addObject(knight, 400, 25);
        addObject(new Knight(), 350, 495);
        addObject(new Knight(), 1000, 495);
        addObject(new Knight(), 712, 320);
        addObject(new Knight(), 1068, 35);
    }
    //Places 2 ammo boxes and a heart box randomly on the world. Places the part at a pre-set location
    public void addItems(){
        ammo1 = new AmmoBox();
        ammo2 = new AmmoBox();
        heart1 = new HeartBox();
        part2 = new Part2();
        
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
        addObject(part2, 79, 285);
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
        addObject(new Knight(), rand * 89, 10); 
    }
}
