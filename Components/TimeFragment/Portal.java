import mayflower.*;
public class Portal extends Actor
{
   public Portal(Scientist thing){
       //Creates the image for the portal to move between levels
       MayflowerImage portal = new MayflowerImage("img/Items/Portal.png");
       portal.scale(70,90);
       setImage(portal);
   }
   
   public void act(){
       
}
//Checks if the scientist moved into the portal
public boolean isColliding(){
    if(isTouching(Scientist.class)){
        return true;
    }
    return false;
}
}
