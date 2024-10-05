import mayflower.*;
public class Animation
{
    private MayflowerImage[] frames;
    private int framerate;
    private int currentFrame;
    
    //Sets the animamation using array paramter
    public Animation(int rate, String[] files){
        framerate = rate;
        frames = new MayflowerImage[files.length];
        for(int i = 0; i < files.length; i++){
            frames[i] = new MayflowerImage (files[i]);
        }
        currentFrame = 0;
    }
    //Returns frame rate
    public int getFrameRate(){
        return framerate;
    }
    //Method for getting the next frame of the MayflowerImage array
    public MayflowerImage getNextFrame(){
        currentFrame++;
        if(currentFrame == frames.length)
            currentFrame = 0;
        MayflowerImage current = frames[currentFrame];
        return current;
       }
    //Scaling the size of the MayflowerImage
    public void scale (int w, int h){
        for(int i = 0; i < frames.length; i++){
            frames[i].scale(w,h);
        }
    }
    //Sets the transparency of the MayflowerImage
    public void setTransparency(int percent){
        for(int i = 0; i < frames.length; i++){
            frames[i].setTransparency(percent);
        }
    }
    //Flips the image horizontally
    public void mirrorHorizontally(){
        for(int i = 0; i < frames.length; i++){
            frames[i].mirrorHorizontally();
        }
    }
    //Sets the bounds of the MayflowerImage
    public void setBounds(int x, int y, int w, int h){
        for(int i = 0; i < frames.length; i++){
            frames[i].crop(x, y, w, h);
        }
    }
    }
