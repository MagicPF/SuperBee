package com.company.eric.g5_superbee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public void aboutus(View view){// start aboutus activity
        startActivity(new Intent(this,AboutUs.class));
    }
    public void StartGame(View view){// start game activity
        startActivity(new Intent(this,GameActivity.class));
    }
    public void Story(View view){// start story activity
        startActivity(new Intent(this,StoryActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//initialization
        setContentView(R.layout.activity_main);
    }
}

