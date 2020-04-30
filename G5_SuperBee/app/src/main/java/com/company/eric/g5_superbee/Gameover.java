package com.company.eric.g5_superbee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Gameover extends AppCompatActivity {
    int SCORE;
    public void Gobackhahaha(View view){ // a onclick function for back to the main activity
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        SCORE = getIntent().getIntExtra("SCORE",SCORE); // get the score value
        TextView Score = (TextView)findViewById(R.id.score); // set a textview for score
        Score.setText(Integer.toString(SCORE));//display the score
    }
}
