import mayflower.*;

public class Score extends Actor
{
    private MayflowerImage[] scores;
    public Score(){
        //Creates an array for the 4 scores after collecting a part.
        scores = new MayflowerImage[4];
        for(int i = 0; i < scores.length; i++){
            scores[i] = new MayflowerImage("img/Scores/Parts" + i + ".png");
            scores[i].scale(288, 160);
            scores[i].crop(0, 0, 288, 35);
        }
    }
    
    public void act(){
        
    }
    //ALL THE SETTER METHODS TO UPDATE THE SCORE AFTER COLLECTING A PART
    public void setZero(){
        setImage(scores[0]);
    }
    
    public void setOne(){
        setImage(scores[1]);
    }
    
    public void setTwo(){
        setImage(scores[2]);
    }
    
    public void setThree(){
        setImage(scores[3]);
    }
}
