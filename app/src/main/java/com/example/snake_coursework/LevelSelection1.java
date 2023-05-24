
package com.example.snake_coursework;

        import androidx.appcompat.app.AppCompatActivity;

        import android.graphics.Point;
        import android.os.Bundle;
        import android.view.Display;

public class LevelSelection1 extends AppCompatActivity {

    SnakeGame snakeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        snakeGame = new SnakeGame(this, size);

        setContentView(snakeGame);
    }
    @Override
    protected void onPause(){
        super.onPause();
        snakeGame.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        snakeGame.onResume();
    }
}