package com.example.spaceshipnavigator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class GameHandler {
	GameView gameView;
	Ships ship;
	Asteroids asteroid;
	public CopyOnWriteArrayList<Ships> ships;
	ArrayList<Asteroids> asteroids;
	ArrayList<PointF> points = new ArrayList<PointF>();
	DestinationPoints dp = new DestinationPoints();
	int timer = 0, asteroidTimer = 0, nSprites = 1, gameOver, score;
	RectF one, two;

	public void initShips(Bitmap lShip, Bitmap mShip, Bitmap sShip) {
		ships = new CopyOnWriteArrayList<Ships>();
		asteroids = new ArrayList<Asteroids>();
		for (int i = 0; i < nSprites; i++) {
			System.out.println("HELLO");
			ships.add(new Ships(lShip, mShip, sShip));
		}
	}

	public void timer(int delay, Bitmap lShip, Bitmap mShip, Bitmap sSHip, Bitmap a) {
		timer += delay;
		asteroidTimer += delay;
		if (ships.size() <= 15) {
			if (score <= 2 && timer == 7000) {
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			}
			if (score > 2 && score <= 10 && timer == 6000) {
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			} else if (score > 10 && score <= 20 && timer == 5000) {
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			} else if (score >= 20 && score < 30 && timer == 3000) {
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			} else if (score >= 30 && timer == 2000){
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			}
			else if(timer > 8000){
				ships.add(new Ships(lShip, mShip, sSHip));
				timer = 0;
			}
		}
		if (asteroidTimer == 15000){
			asteroids.removeAll(asteroids);
		}
		if (asteroidTimer == 30000){
			asteroidTimer = 0;
			asteroids.add(new Asteroids(a));
		}
		
	}

	public boolean handleTap(float x, float y, PointF point) {
		for (Ships s : ships) {
			System.out.println("Ship type : " + s.shipType);
			if (x >= s.s.x && x < (s.s.x + s.width) && y >= s.s.y
					&& y < (s.s.y + s.height)) {
				if (s.points.isEmpty()) {
					s.active = 1;
				} else {
					s.removePoints();
					s.active = 1;
				}
			} else {
				s.active = 0;
			}
		}
		return true;
	}

	public void increaseScore(RectF lShipBound, RectF mShipBound, RectF sShipBound) {

		boolean lB = false;
		boolean mB = false;
		boolean sB = false;
		for (Ships s : ships) {
			if (s.shipType == "l") {
				lB = checkLarge(s.bounding, lShipBound);
				if (lB == true) {
					ships.remove(s);
					score += 1;
				}
			}
			if (s.shipType == "m") {
				mB = checkMedium(s.bounding, mShipBound);
				if (mB == true) {
					ships.remove(s);
					score += 1;
				}
			}
			if (s.shipType == "s") {
				sB = checkSmall(s.bounding, sShipBound);
				if (sB == true) {
					ships.remove(s);
					score += 1;
				}
			}

		}
	}

	public void gameOver() {
		for (int i = 0; i < ships.size(); i++) {
			for (int i2 = 0; i2 < ships.size(); i2++) {
				if (i != i2) {
					if (RectF.intersects(ships.get(i).bounding, ships.get(i2).bounding)){
						gameOver = 1;
						System.out.println("Game over");
					}
				}
			}
		}
	}
	
	public void asteroidCollision(){
		for (int i = 0; i < ships.size(); i++) {
			for (int i2 = 0; i2 < asteroids.size(); i2++) {
					if (RectF.intersects(ships.get(i).bounding, asteroids.get(i2).bounding)){
						gameOver = 1;
						System.out.println("Game over");
				}
			}
		}
	}

	public boolean checkLarge(RectF bounding, RectF planetBound) {
		boolean t = false;
		if (RectF.intersects(planetBound, bounding)){
			t = true;
			return t;
		} else {
			return t;
		}
	}
	
	public boolean checkMedium(RectF bounding, RectF planetBound){
		boolean t2 = false;
		if (RectF.intersects(planetBound, bounding)){
			t2 = true;
			return t2;
		} else {
			return t2;
		}
	}
	
	public boolean checkSmall(RectF bounding, RectF planetBound){
		boolean t3 = false;
		if (RectF.intersects(planetBound, bounding)){
			t3 = true;
			return t3;
		} else {
			return t3;
		}
	}
}
