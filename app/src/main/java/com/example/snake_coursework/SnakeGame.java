package com.example.snake_coursework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


public class SnakeGame extends SurfaceView implements Runnable {
    private Thread thread;
    private Boolean isThreadRunning;
    private int score = 0;

    private final int NUM_BLOCK_WIDE = 40;
    private int blockSize;
    private int NUM_BLOCK_HIGH;

    private Bitmap backgroundImage;
    private SurfaceHolder surfaceHolder;

    private long nextFrameTime;

    private Boolean paused = true;

    private Canvas canvas;
    private Paint paint = new Paint();

    private GestureDetector gestureDetector;
    private float x1, x2, y1, y2;
    private float abs_x, abs_y;
    static final int MIN_DISTANCE = 100;

    private Apple apple;
    private Snake snake;
    SnakeGame(Context context, Point screenSize){
        super(context);
        blockSize = screenSize.x/NUM_BLOCK_WIDE;
        NUM_BLOCK_HIGH = screenSize.y/blockSize;

        backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        backgroundImage = Bitmap.createScaledBitmap(backgroundImage, screenSize.x, screenSize.y, false);

        apple = new Apple(context, NUM_BLOCK_HIGH, NUM_BLOCK_WIDE, blockSize);
        snake = new Snake(context, new Point(NUM_BLOCK_WIDE, NUM_BLOCK_HIGH), blockSize);

        gestureDetector = new GestureDetector(getContext(), getGestureLiner());

        surfaceHolder = getHolder();
        startNextGame();

    }
    void startNextGame(){
        score = 0;

        apple.setApplePosition();

        snake.moveSnake();

        nextFrameTime = System.currentTimeMillis();
    }
    @Override
    public void run() {
        while(isThreadRunning){
            if(updateRequired()) {
                if (!paused) {
                    update();
                }
                draw();
            }
        }
    }
    private Boolean updateRequired(){
        if(System.currentTimeMillis()> nextFrameTime){
            nextFrameTime += 150;
            return true;
        }
        return false;

    }
    private void update(){
        snake.moveSnake();

        if(snake.isDead())
            paused = true;
        if(snake.haveDinner(apple.getPositionOfApple())){
            score ++;
            apple.setApplePosition();
        }
    }
    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(backgroundImage, 0, 0, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(120);
            canvas.drawText(""+ score, 1, 120,paint);
            if(!paused){
                apple.drawApple(canvas, paint);
                snake.drawSnake(canvas, paint);
            }
            else{
                paint.setTextSize(250);
                paint.setColor(Color.BLACK);
                canvas.drawText("tap to play", 250,250, paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private GestureDetector.OnGestureListener getGestureLiner(){
        return  new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                if(paused){
                    paused = false;
                    score = 0;
                    snake.reset();
                }
                return false;
            }

            @Override
            public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {

            }

            @Override
            public boolean onFling(@NonNull MotionEvent downEvent, @NonNull MotionEvent upEvent, float velocityX, float velocityY) {
                x1 = downEvent.getX();
                y1 = downEvent.getY();
                x2= upEvent.getX();
                y2 = upEvent.getY();

                abs_x = Math.abs(x2 - x1);
                abs_y = Math.abs(y2 - y1);

                if(abs_y > MIN_DISTANCE || abs_x > MIN_DISTANCE){
                    if(abs_y > abs_x){
                        if(snake.getMovingDirection() == Heading.RIGHT || snake.getMovingDirection() == Heading.LEFT){
                            if(y1 > y2)
                                snake.setMovingDirection(Heading.UP);
                            else
                                snake.setMovingDirection(Heading.DOWN);
                        }
                    }
                    else if(abs_x > MIN_DISTANCE){
                        if(snake.getMovingDirection() == Heading.UP || snake.getMovingDirection() == Heading.DOWN){
                            if(x1 > x2)
                                snake.setMovingDirection(Heading.LEFT);
                            else
                                snake.setMovingDirection(Heading.RIGHT);
                        }
                    }
                }
                return false;
            }
        };
    }
    public void onResume(){
        isThreadRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    public void onPause(){
        isThreadRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
