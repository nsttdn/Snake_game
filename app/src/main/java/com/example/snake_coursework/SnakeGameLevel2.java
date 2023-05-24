package com.example.snake_coursework;

import android.content.Context;
import android.graphics.Point;

public class SnakeGameLevel2 extends SnakeGame {

    private long nextFastFrameTime;

    public SnakeGameLevel2(Context context, Point screenSize) {
        super(context, screenSize);
        nextFastFrameTime = System.currentTimeMillis();
    }

    @Override
    void startNextGame() {
        super.startNextGame();
        nextFastFrameTime = System.currentTimeMillis();
    }

    protected boolean updateRequired() {
        if (System.currentTimeMillis() > nextFastFrameTime) {
            nextFastFrameTime += 50;
            return true;
        }
        return false;
    }
}