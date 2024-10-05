import mayflower.*;
public class Level3 extends World
{    
    //Declares all instance variables for the level
    private Scientist scientist;
    private String[][] tiles;
    private Alien alien;
    private Alien alien2;
    private Alien alien3;
    private Alien alien4;
    private AmmoBox ammo1;
    private AmmoBox ammo2;
    private Part3 part3;
    private Portal portal;
    private HeartBox heart1;
    private HeartBox heart2;
    private Fire trap1;
    private Fire trap2;
    private Fire trap3;
    private Fire trap4;
    private Bolt bolt;
    private Ladder ladder1a;
    private Ladder ladder1b;
    private Ladder ladder2a;
    private Ladder ladder2b;
    private Ladder ladder3a;
    private Ladder ladder3b;
    private Ladder ladder4a;
    private Ladder ladder4b;
    private Ladder ladder5a;
    private Ladder ladder5b;
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
    public Level3(Scientist thing)
    {
        //Constructor that sets the background level and creates the tiles array. Calls methods to build the world
        setBackground("img/Level3Assets/Level3bg.png");
        tiles = new String[7][12];
        scientist = thing;
        buildWorld();
        addCharacters();
        addItems();
        addLives();
        addAmmo();
        Mayflower.showBounds(true);
        //Instance variables to keep track of indexes and numbers for the scientist
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
        //If the SPACE bar is pressed and the Scientist has ammo, a bolt is produced from the Scientist that deals damage. Ammo is gradually decreased
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
        //If the amount of lives decreases, remove a heart icon from the top
        if(scientist.getLives() == totalLives - 1){
           removeObject(hearts[scientist.getLives()]);
           totalLives--;
           heartIndex--;
         }
         //If ammo decreases by 30, an ammo icon is decreased from the top
         if(scientist.getAmmo() == totalAmmo - 30){
           removeObject(ammos[ammoIndex]);
           totalAmmo -= 30;
           ammoIndex--;
         }
         //If the Scientist is touching the ammoBox, the Scientist's ammo is fully replenished
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
        //If the Scientist is touching the heartBox, the Scientist's lives are fully replenished
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
        //If the Scientist is touching the part, increase the score and create the Portal.
        if(scientist.partCollected()){
            score.setThree();
            addObject(score, 815, 0);
            scientist.setPartCollected(true);
            portal = new Portal(scientist);
            addObject(portal, 30, 235);
        }
        //If the Scientist is touching the portal, create the winning screen
        if(portal != null && portal.isColliding() && scientist.getPartCollected()){
            Mayflower.setWorld(new winningScreen());
            scientist.setPartCollected(false);
        }
        //Timer for randomly spawning the enemies
        if(spawnTimer <= 0){
            spawn();
            spawnTimer = 500;
        }
        //Count for how long the power up lasts
        if(count > -10){
            count--;
        }
        //After the count is over, the Scientist's stats return back to normal
        if(count < 0){
            Scientist.setSpeed(3);
            Scientist.setJumpSpeed(5);
            Scientist.setDamage(1);
            removeObject(power);
            Scientist.setPowerUp(false);
        }
    }
    //Creates the world with the tiles array that sets the platforms, ground, and ladders
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
        //Builds the platforms and checks the conditionals to shift the platforms up/down/left/right or just not build them at all
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                //Creates the ground of the level
                if(tiles[i][j].equals("ground"))
                    addObject(new Metal(), (j * 97), (i * 100));
                //Builds platform for first level
                if(tiles[i][j].equals("platform")){
                    if(i == 2){

                           
                       if(j == 4)
                           continue;
                       if(j == 2 || j==3 || j ==5 || j ==6|| j==7||j==8){
                            addObject(new Level3Platform(), j * 89, (i * 150) - 150);  
                            continue;
                        }

                       if(j ==2 || j == 9 || j == 11){
                            addObject(new Level3Platform(), (j * 89) + 80, (i * 150) - 175  );
                            continue;
                        }
                    }
                    //Builds platform for second level
                    if(i == 3){
                        if(j == 2 || j == 5 || j == 9)
                            continue;
                        if(j == 11 || j==10){
                            addObject(new Level3Platform(), j * 86, (i * 150) - 175);  
                            continue;
                        }
                        if(j == 1 || j == 0 || j ==8){
                            addObject(new Level3Platform(), j * 86, (i * 150) - 125);  
                            continue;
                        }
                        if(j == 4 || j == 3 || j == 6 ){
                            addObject(new Level3Platform(), j * 86, (i * 150) - 150);  
                            continue;
                        }
                    }
                    //Builds platform for third level
                    if(i == 4){
                        if(j==8)
                            continue;
                        if(j == 4 || j == 5 || j == 9 || j==10){
                            addObject(new Level3Platform(), (j * 89), (i * 150) - 125  );
                            continue;
                        }
                        if(j==0 || j == 1 ||j == 6 || j== 7){
                            addObject(new Level3Platform(), (j * 89) + 89, (i * 150) - 150  );
                            continue;
                        }
                    }
                    }
            }
        }
        //Places traps on pre-set areas
        trap1 = new Fire();
        trap2 = new Fire();
        trap3 = new Fire();
        trap4 = new Fire();
        addObject(trap1, 630, 152);
        addObject(trap2, 293, 292);
        addObject(trap3, 470, 442); 
        addObject(trap4, 823, 430);
        //Builds ladders on the world
        ladder1a = new Ladder();
        ladder1b = new Ladder();
        addObject(ladder1a, 357, 150);
        addObject(ladder1b, 357, 225);
        ladder2a = new Ladder();
        ladder2b = new Ladder();
        addObject(ladder2a, 665, 325);
        addObject(ladder2b, 665, 376);
        ladder3a = new Ladder();
        ladder3b = new Ladder();
        addObject(ladder3a, 173, 325);
        addObject(ladder3b, 173, 376);
        ladder4a = new Ladder();
        ladder4b = new Ladder();
        addObject(ladder4a, 975, 475);
        addObject(ladder4b, 975, 525);
        ladder5a = new Ladder();
        ladder5b = new Ladder();
        addObject(ladder5a, 339, 475);
        addObject(ladder5b, 339, 525);
        //Creates the score board for amount of parts collected. Universal for all levels
        score = new Score();
        score.setTwo();
        addObject(score, 815, 0);
    }
    //Creates the scientist and aliens for the world at pre-set locations
    public void addCharacters(){
        scientist = new Scientist();
        alien = new Alien();
        alien2 = new Alien();
        alien3 = new Alien();
        alien4 = new Alien();
        
        addObject(scientist, 50, 495);
        addObject(alien, 400, 25);
        addObject(alien4, 100, 25);
        addObject(alien2, 800, 25);
        addObject(alien3, 1100, 25);
    }
    //Places 2 ammo boxes and 2 heart boxes on the world randomly. Places the Part3 at a preset location
    public void addItems(){
        ammo1 = new AmmoBox();
        ammo2 = new AmmoBox();
        heart1 = new HeartBox();
        heart2 = new HeartBox();
        part3 = new Part3();       
        
        boolean addedAmmo1 = false;
        boolean addedAmmo2 = false;
        boolean addedHeart1 = false;
        boolean addedHeart2 = false;
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
        while(!addedHeart2){
            int randRow = (int) (Math.random() * 3) + 2;
            int randCol = (int) (Math.random() * tiles[0].length);
            addObject(heart2, randCol * 89, (randRow * 150) - 75);
            if(heart2.isColliding()){
                removeObject(heart2);
                continue;
            }
            addedHeart2 = true;
        }
        addObject(part3, 1093, 95);
    }
    //Checks if SPACE bar is pressed
    public boolean attack(){
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
            return true;
        return false;
    }
    //Adds the lives icon on the top of the screen
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
    //Randomly spawns the enemies across the top of the screen
    public void spawn(){
        int rand = (int) (Math.random() * tiles.length);
        addObject(new Alien(), rand * 89, 10); 
    }
}

