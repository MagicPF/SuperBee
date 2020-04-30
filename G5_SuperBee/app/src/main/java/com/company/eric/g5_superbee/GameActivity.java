package com.company.eric.g5_superbee;

import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    ImageView bee,Spiderimg,Snakeimg;
    MediaPlayer Jump = null,myMus = null , GG = null;
    TextView score,pts;
    boolean start = false; // boolean flag to check whether the game is start by press the screen first
    Intent MyGameOver; // to start gameover activity with carrying the value of score so defined it here
    int fallingtime = 0, Pcolddown = 5, Scolddown = 15,Score = 0; // fallingtime spider CD time  Snake CD time
    boolean alive = true;// initialize the alive
    void MainThread(){// the main thread to maintain the running and checking status
        final int DEF_SLEEP_GAP = 10; // delay
        new Thread(new Runnable() {
            @Override
            public void run(){  // thread define
                while (!Thread.currentThread().isInterrupted() && alive){ // execute after dead or  been closed
                    try {
                        Thread.sleep(DEF_SLEEP_GAP); // delay
                        final ImageView Bee=(ImageView) findViewById(R.id.Bee); if (Bee==null) break; // define a current bee image
                        Bee.post(new Runnable() { // for handling UI with thread in Android
                            public void run() {
                                score.setText(Integer.toString(Score)); // update the text of score as the value of the integer score
                                Pcolddown--; // CD time --
                                Scolddown--;// CD time --
                                Sprite spi = new Sprite(Spiderimg,Pcolddown); // new a object
                                Sprite snk = new Sprite(Snakeimg,Scolddown);// new a object
                                if(Scolddown <= 0)
                                    Scolddown = 15;//reset the CD time
                                if(Pcolddown <= 0)//reset the CD tine
                                    Pcolddown = 10;
                                Bee.setY(Bee.getY() + (float) (5 * fallingtime * fallingtime * 0.0005)); // s = 1/2 * a * t ^ 2
                                fallingtime ++;// fallingtime is the "t"
                                spi.moveit(2); // spider will move faster
                                snk.moveit(1);// snake moving
                                if(spi.img.getX() < -100) { // been the left side of the screen
                                    spi.img.setY((int)(Math.random()*(2000-0+1))); //randomly appear
                                    spi.img.setX(1500); // reset the X
                                    Score++;// Because already arrived the left side and the game havent stoped which means the player avoid the spider successfully
                                }
                                if(snk.img.getX() < -100) {
                                    snk.img.setX(1500);
                                    Score++;
                                } // the same as the spider
                                Rect CRect=new Rect(),SpRect=new Rect(),SnRect=new Rect();
                                Bee.getHitRect(CRect); // get the rectangle zone of the roles
                                Spiderimg = spi.img;
                                Snakeimg = snk.img;
                                spi.img.getHitRect(SpRect);
                                snk.img.getHitRect(SnRect);
                                if(Bee.getY() >= 2000 || CRect.intersect(SpRect) || CRect.intersect(SnRect)) // if there is a intersection exist
                                    alive = false; // the bee dead
                            }
                        });
                    } catch (InterruptedException ie) {}
                }
                GG.start(); // execute the while loop which means dead
                MyGameOver = new Intent(GameActivity.this, Gameover.class);
                MyGameOver.putExtra("SCORE",Score); // start new activity and send the value of score
                startActivity(MyGameOver);
                finish();// finish the activity
                myMus.stop();//stop BGM
            }
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // initialization
        super.onCreate(savedInstanceState);
        start = false; // boolean flag for checking the game is start or not
        setContentView(R.layout.activity_game);
        pts = (TextView) findViewById(R.id.PTS); // press to start
        score = (TextView) findViewById(R.id.Mark); // score textview
        score.setText("0"); // the score begin with 0
        GG = MediaPlayer.create(this, R.raw.pppy); // initialization of GameOver sounds
        Jump = MediaPlayer.create(this, R.raw.jump_gbm); //initialization of jump sounds
        myMus = MediaPlayer.create(this, R.raw.game_bgm);//initialization of BGM
        bee = ((ImageView) findViewById(R.id.Bee)); //initialization of bee image
        Spiderimg = ((ImageView) findViewById(R.id.Spider));Spiderimg.setVisibility(View.INVISIBLE);Spiderimg.setX(1500);//initialize the spider image
        Snakeimg = ((ImageView) findViewById(R.id.Snake));Snakeimg.setVisibility(View.INVISIBLE);Snakeimg.setX(1500);//initialize the snake image
        alive = true; // set the bee is alive
        //MainThread();  there was the beginning of the game but after add the press to start function it moved to the ontouchevent
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // check whether User touch the screen
        if(event.getAction() == MotionEvent.ACTION_DOWN && start) { // if touched and it is not the first touch
            bee.setY(bee.getY() - 200); // the bee jump up
            if(bee.getY() < -50)// the bee cannot jump out the ceilling
                bee.setY(0);// set the bee to the Y:0
            fallingtime = 0;// restart falling
            Jump.start();// play the jumping sound
        }
        if(!start) {// first touch to start the game
            pts.setVisibility(View.INVISIBLE);
            MainThread();// start checking state
            start = true;// boolean flag become true that the game start
            myMus.start();myMus.setLooping(true); // start BGM
        }
        if(!alive) // if dead
            Toast.makeText(this, "DEAD", Toast.LENGTH_SHORT).show(); // Debug using
        return true;
    }
}
