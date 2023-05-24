package com.example.snake_coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class LevelSelection2 extends AppCompatActivity {

    SnakeGameLevel2 level2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        level2 = new SnakeGameLevel2(this, size);

        setContentView(level2);
    }
    @Override
    protected void onPause(){
        super.onPause();
        level2.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        level2.onResume();
    }
}