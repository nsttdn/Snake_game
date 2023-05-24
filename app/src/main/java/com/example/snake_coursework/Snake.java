package com.example.snake_coursework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

enum Heading{
    UP, DOWN, RIGHT, LEFT
}

public class Snake {
    private int BLOCK_SIZE;
    private Point movingRange;
    private Heading heading;
    private Bitmap snakeBodyBitmap;
    private Bitmap headRightBitmap;
    private Bitmap headLeftBitmap;
    private Bitmap headUpBitmap;
    private Bitmap headDownBitmap;

    private final ArrayList<Point> snakeSegments = new ArrayList<>();
    Snake(Context context, Point movingRange, int BLOCK_SIZE){
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.movingRange = movingRange;

        headRightBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.headright);
        headRightBitmap = Bitmap.createScaledBitmap(headRightBitmap, BLOCK_SIZE, BLOCK_SIZE, true);

        headLeftBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.headleft);
        headLeftBitmap = Bitmap.createScaledBitmap(headLeftBitmap, BLOCK_SIZE, BLOCK_SIZE, true);

        headUpBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.headup);
        headUpBitmap = Bitmap.createScaledBitmap(headUpBitmap, BLOCK_SIZE, BLOCK_SIZE, true);

        headDownBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.headdown);
        headDownBitmap = Bitmap.createScaledBitmap(headDownBitmap, BLOCK_SIZE, BLOCK_SIZE, true);

        snakeBodyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.body);
        snakeBodyBitmap = Bitmap.createScaledBitmap(snakeBodyBitmap, BLOCK_SIZE, BLOCK_SIZE, true);

        snakeSegments.add(new Point(20, 2));

        heading = Heading.LEFT;
    }

    boolean haveDinner(Point applePosition){
        if(applePosition.x == snakeSegments.get(0).x &&
                applePosition.y == snakeSegments.get(0).y){
            snakeSegments.add(new Point(-100, -100));
            return true;
        }
        return false;
    }
    void  moveSnake(){
        for (int i = snakeSegments.size()-1; i>=1; i--){
            snakeSegments.get(i).x = snakeSegments.get(i-1).x;
            snakeSegments.get(i).y = snakeSegments.get(i-1).y;
        }
        switch (heading){
            case RIGHT:
                snakeSegments.get(0).x+=1;
                if(snakeSegments.get(0).x> movingRange.x)
                    snakeSegments.get(0).x = 0;
                break;
            case LEFT:
                snakeSegments.get(0).x-=1;
                if(snakeSegments.get(0).x == -1)
                    snakeSegments.get(0).x = movingRange.x;
                break;
            case UP:
                snakeSegments.get(0).y-=1;
                if(snakeSegments.get(0).y == -1)
                    snakeSegments.get(0).y = movingRange.y;
                break;
            case DOWN:
                snakeSegments.get(0).y += 1;
                if(snakeSegments.get(0).y > movingRange.y)
                    snakeSegments.get(0).y = 0;
                break;
        }
    }
    void drawSnake(Canvas canvas, Paint paint){
        for(int i = snakeSegments.size()-1; i>=1; i--){
            canvas.drawBitmap(snakeBodyBitmap, snakeSegments.get(i).x*BLOCK_SIZE,
                    snakeSegments.get(i).y*BLOCK_SIZE,paint);
        }
        switch (heading){
            case RIGHT:
                canvas.drawBitmap(headRightBitmap, snakeSegments.get(0).x*BLOCK_SIZE,
                        snakeSegments.get(0).y * BLOCK_SIZE, paint);
                break;
            case LEFT:
                canvas.drawBitmap(headLeftBitmap, snakeSegments.get(0).x*BLOCK_SIZE,
                        snakeSegments.get(0).y * BLOCK_SIZE, paint);
                break;
            case DOWN:
                canvas.drawBitmap(headDownBitmap, snakeSegments.get(0).x*BLOCK_SIZE,
                        snakeSegments.get(0).y * BLOCK_SIZE, paint);
                break;
            case UP:  canvas.drawBitmap(headUpBitmap, snakeSegments.get(0).x*BLOCK_SIZE,
                    snakeSegments.get(0).y * BLOCK_SIZE, paint);
                break;
        }
    }
    Boolean isDead(){
        for (int i = snakeSegments.size()-1; i>0;i--){
            if(snakeSegments.get(i).x ==snakeSegments.get(0).x &&
                    snakeSegments.get(i).y == snakeSegments.get(0).y){
                return true;
            }
        }
        return false;
    }
    void reset(){
        snakeSegments.clear();
        snakeSegments.add(new Point(20, movingRange.y/2));
        heading = Heading.RIGHT;
    }
    void setMovingDirection(Heading h){
        heading = h;
    }
    Heading getMovingDirection(){
        return heading;
    }

}
