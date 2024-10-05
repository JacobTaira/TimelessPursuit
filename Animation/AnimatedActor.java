import mayflower.*;
public class AnimatedActor extends GravityActor
{
    private Animation animation;
    private Timer animationTimer;
    //Sets a timer to animate the actor frame by frame
    public AnimatedActor(){
       animationTimer = new Timer(100000000);
    }
    public void setAnimation(Animation a){
        animation = a;
    }
    //Continually shifts to next frame after time is done
    public void act(){
        super.act();
        if(animation != null){
            if(animationTimer.isDone()){
          animationTimer.reset();
         MayflowerImage image = animation.getNextFrame();
          setImage(image);
        }
        }
    }
}
