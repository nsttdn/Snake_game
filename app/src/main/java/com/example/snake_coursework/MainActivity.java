package com.example.snake_coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Bitmap backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btnPlay(View v){

        Intent intent = new Intent(this, LevelSelection1.class);
        startActivity(intent);

    }
    public void btnPlayLevel2(View v){

        Intent intent = new Intent(this, LevelSelection2.class);
        startActivity(intent);

    }

}