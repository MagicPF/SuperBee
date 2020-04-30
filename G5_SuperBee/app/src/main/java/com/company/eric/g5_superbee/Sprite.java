package com.company.eric.g5_superbee;



import android.view.View;
import android.widget.ImageView;

public class Sprite {
    ImageView img;

    public Sprite(ImageView S,int c){
        // the spider and snake are mostly the same expect the moving speed and image
        img = S; // set the image
        //img.setY((int)(Math.random()*(2000-0+1)));
        if(c == 0)
            img.setVisibility(View.VISIBLE); // CD time up  appear
    }
    public void moveit(int spd){
        img.setX(img.getX() - 10 * spd + 2); // moving
    }
}
