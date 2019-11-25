package com.example.parcialfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main menu of the game
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * Launches the gameplay
     * @param view
     */
    public void playGame (View view){
        startActivity(new Intent(this, GamePlay.class));
    }
}