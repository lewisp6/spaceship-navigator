package com.example.spaceshipnavigator;

import java.util.ArrayList;
import java.util.List;

import com.example.spaceshipnavigator.GameActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Rect;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	GameThread thread;
	GameHandler gameState;
	DestinationPoints dp;
	Context context;
	Canvas c;
	int score = 0;
	Bitmap largeShip = BitmapFactory.decodeResource(getResources(), R.drawable.space_ship);
	Bitmap mediumShip = BitmapFactory.decodeResource(getResources(), R.drawable.m_ship);
	Bitmap smallShip = BitmapFactory.decodeResource(getResources(), R.drawable.s_ship);
	Bitmap bluePlanet = BitmapFactory.decodeResource(getResources(), R.drawable.blue_planet);
	Bitmap greenPlanet = BitmapFactory.decodeResource(getResources(), R.drawable.green_planet);
	Bitmap orangePlanet = BitmapFactory.decodeResource(getResources(), R.drawable.orange_planet);
	Bitmap asteroid = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
	Bitmap spaceBackground = BitmapFactory.decodeResource(getResources(), R.drawable.space_background);
	Ships ship = new Ships(largeShip, mediumShip, smallShip);
	
	public GameView(GameActivity gameActivity) {
		super(gameActivity);
		SurfaceHolder holder = getHolder();
	        holder.addCallback(this);
	        setFocusable(true);
	        requestFocus();
	}
	
	public void gameOverState(){
		startDialog();
	}
	
	public void startDialog(){
		int score = thread.gameState.score;
		Context context = getContext();
		Intent intent = new Intent(context, GameOverActivity.class);
		intent.putExtra("score", Integer.toString(score));
		context.startActivity(intent);
		thread.running = false;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		 System.out.println("TestActivity: TestView.surfaceCreated() ");
	        thread = new GameThread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder,  int format, int width, int height) {
        System.out.println("TestView.surfaceChanged() " + width + " : " + height);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		System.out.println("TestView.surfaceDestroyed()");
        // we have to tell thread to shut down & wait for it to finish, or else
        // however, this does not always work, it seems that by this
        // time the Thread might already be in some strange state ...
        // the problem arises when thread.join() just hangs
        // set retry to false in the line below to skip that
        boolean retry = true;
        thread.running = false;
        if (!retry) System.out.println("TestView not bothering to join");
        while (retry) {
            try {
                System.out.println("TestView waiting to join = " + thread.running);
                thread.join();
                retry = false;
                System.out.println("TestView joined, running = " + thread.running);
            } catch (InterruptedException e) {
                System.out.println("TestView.surfaceDestroyed() " + e);
            }
        }
		
	}
	
	public boolean onTouchEvent(MotionEvent e){
		PointF point = new PointF();
		gameState = new GameHandler();
		float sX = ship.s.x, sY = ship.s.y;
		float x = e.getX();
		float y = e.getY();
		point.x = e.getX();
		point.y = e.getY();
		int action = e.getAction();
		if (e.getAction() == MotionEvent.ACTION_DOWN){
			thread.gameState.handleTap(x, y, point);
			for (Ships s : thread.gameState.ships){
				if(s.active == 1){
					s.storePoints(point);
				}
			}
		}
		
		for (Ships s : thread.gameState.ships){
			if(s.active == 1){
				s.storePoints(point);
			}
		}
		
		return true;
	}
	

	
}
