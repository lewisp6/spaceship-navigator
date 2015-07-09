package com.example.spaceshipnavigator;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceHolder;

//import com.example.simplesurfacetest.Sprite;
import com.example.spaceshipnavigator.GameView;

/*Class to run thread*/

public class GameThread extends Thread{
	GameView gameView;
	GameHandler gameState;
	DestinationPoints DestPoint = new DestinationPoints();
		boolean running;
	    int delay = 50;
	    
	    public GameThread(GameView gameView) {
	    	gameState = new GameHandler();
	        this.gameView = gameView;
	        running = true;
	        gameState.initShips(gameView.largeShip, gameView.mediumShip, gameView.smallShip);
	        
	        start();
	    }

	    public void run() {
	        while (running) {
	            Canvas c = null;
	            SurfaceHolder surfaceHolder = gameView.getHolder();
	            // not sure whether this null check is actually needed ...
	            gameState.timer(delay, gameView.largeShip, gameView.mediumShip, gameView.smallShip, gameView.asteroid);
	            if (surfaceHolder != null) {
	                update(surfaceHolder.getSurfaceFrame());

	                try {
	                    c = surfaceHolder.lockCanvas(null);
	                    draw(surfaceHolder, c);
	                } finally {
	                    // do this in a finally so that if an exception is thrown
	                    // during the above, we don't leave the Surface in an
	                    // inconsistent state
	                    if (c != null) {
	                        surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	            }
	            try {sleep(delay);} catch (Exception e) {}
	        }
	        System.out.println("TestView: exiting TestThread.run()");
	    }

	    public void draw(SurfaceHolder surfaceHolder, Canvas c) {
	        // draw the background
	        Rect rect = surfaceHolder.getSurfaceFrame();

	        //c.drawRect(surfaceHolder.getSurfaceFrame(), bg);
	        c.drawBitmap(gameView.spaceBackground, 0, 0, null);
	        // and the movable objects
	        DestPoint.draw(c, gameView.bluePlanet, gameView.greenPlanet, gameView.orangePlanet, tp);
	        for (Ships ship : gameState.ships) ship.draw(c, gg);
	        for (Asteroids asteroid : gameState.asteroids) asteroid.draw(c, gg);
	        drawText(rect, c);
	    }
	    
	    public void drawText(Rect rect, Canvas c) {
	        tp.setTextSize(rect.height() / 20);
	        tp.setTextAlign(Paint.Align.LEFT);
	        c.drawText("Score = " + gameState.score, 30, 40, tp);
	    }

	    public void update(Rect rect) {
	        // update the game objects etc
	    	gameState.gameOver();
	    	gameState.asteroidCollision();
	    	if (gameState.gameOver != 1){
		         for (Ships ship : gameState.ships) ship.update(rect);
		         for (Asteroids asteroid : gameState.asteroids) asteroid.update(rect);
		         gameState.increaseScore(DestPoint.lPlanetSuccessArea, DestPoint.mPlanetSuccessArea, DestPoint.sPlanetSuccessArea);
	    	}
	    	else{
	    		gameView.gameOverState();
	    	}
	    }

	    static Paint bg = new Paint();
	    static Paint gg = new Paint();
	    static Paint tp = new Paint();

	    
	    static {
	        bg.setColor(Color.BLACK);
	        bg.setStyle(Paint.Style.FILL);
	        gg.setColor(Color.GREEN);
	        gg.setStyle(Paint.Style.STROKE);
	        gg.setAntiAlias(true);
	        tp.setColor(Color.WHITE);
	        tp.setStyle(Paint.Style.FILL);
	        tp.setAntiAlias(true);
	        tp.setTextSize(30);
	    }

}
