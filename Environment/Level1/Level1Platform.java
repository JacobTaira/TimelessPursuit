import mayflower.*;
public class Level1Platform extends Actor
{
    public Level1Platform(){
        //Creates the platform for level1
        MayflowerImage platform1 = new MayflowerImage("img/Level1Assets/Level1platform.png");
        platform1.scale(90, 60);
        platform1.crop(0, 0, 90, 18);
        setImage(platform1);
    }
    
    public void act(){
        
    }
}
