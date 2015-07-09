package com.example.spaceshipnavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import com.example.spaceshipnavigator.Vector2d;

/*Asteroids class defines and draws the asteroids*/

public class Asteroids{
	GameHandler gameState = new GameHandler();
	Vector2d s, l, m, v, v1, v2 = new Vector2d(1 ,1);
    static Random ran = new Random();
    public Bitmap asteroid;
    public Rect bounds;
    float x, y, vX, vY;
	int active = 0;
	int i = 0, width, height;
	RectF bounding, shipArea;
	
	public Asteroids(Bitmap asteroidImage){
    	s = new Vector2d();
			this.asteroid = asteroidImage;
	    	this.v = new Vector2d(1 * (float)ran.nextGaussian(), 1 * (float)ran.nextGaussian());
	    	area();
	    	this.shipArea = bounding;
    	
			height = asteroid.getHeight();
	    	width = asteroid.getWidth();
	}

    public void update(Rect rect) {
             s.add(v);
             s.wrap(rect.width(), rect.height());
        }

    public void area(){
    	bounding = new RectF((float) (s.x + (width * 0.1)), (float) (s.y + (height * 0.1)), (float) ((s.x + width) - (width * 0.1)) , (float)((s.y + height) - (height * 0.1)));
    }
    
public void draw(Canvas c, Paint p) {
    		area();
    		c.drawBitmap(asteroid, s.x, s.y, null);
    		//c.drawRect(bounding, p);
    }
}

