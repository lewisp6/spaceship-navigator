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

/*Ships class defines and draws the ships*/

public class Ships{
	//v = speed 
	GameHandler gameState = new GameHandler();
	Vector2d s, l, m, v, v1, v2 = new Vector2d(1 ,1);
    static Random ran = new Random();
    public Bitmap lShip, mShip, sShip, ship;
    public Rect bounds;
    float x, y, vX, vY;
	double random = Math.random();
    float[] values = new float[10];
	int i = 0, width, height, active = 0, iCurStep = 0, speed;
	CopyOnWriteArrayList<PointF> points = new CopyOnWriteArrayList<PointF>();
	PathMeasure pm;
	String shipType;
	RectF bounding, shipArea;
	Path path = new Path();
	PointF initial = new PointF();
	
	public Ships(Bitmap largeShip, Bitmap mediumShip, Bitmap smallShip){
    	s = new Vector2d();

		if (random <= 0.33){
			this.ship = smallShip;
	    	this.v = new Vector2d(1 * (float)ran.nextGaussian(), 1 * (float)ran.nextGaussian());
	    	this.shipType = "s";
	    	area();
	    	this.shipArea = bounding;
	    	this.speed = 3;
    	}
    	else if (random <= 0.66 && random > 0.33){
    		this.ship = mediumShip;
	    	this.v = new Vector2d(1 * (float)ran.nextGaussian(), 1 * (float)ran.nextGaussian());
	    	this.shipType = "m";
	    	area();
	    	this.shipArea = bounding;
	    	this.speed = 2;
    	}
    	else if (random > 0.66){
    		this.ship = largeShip;
	    	this.v = new Vector2d(1 * (float)ran.nextGaussian(), 1 * (float)ran.nextGaussian());
	    	this.shipType = "l";
	    	area();
	    	this.shipArea = bounding;
	    	this.speed = 1;
    	}
			height = ship.getHeight();
	    	width = ship.getWidth();
	}

    public void update(Rect rect) {
    			s.add(v);
            	s.wrap(rect.width(), rect.height());
        }

    public void storePoints(PointF p){
    	if (points.isEmpty()){
    		initial.x = s.x + width/2;
    		initial.y = s.y + height/2;
    		points.add(initial);
    	}
    	else{
    		points.add(p);
    	}
    }
    
    public void removePoints(){
    		points.clear();
    		iCurStep = 0;
    }
    
    public void area(){
    	bounding = new RectF((float) (s.x + (width * 0.1)), (float) (s.y + (height * 0.1)), (float) ((s.x + width) - (width * 0.1)) , (float)((s.y + height) - (height * 0.1)));
    }
    
public void draw(Canvas c, Paint p) {
	PointF lastPoint;
	if (points.isEmpty()){
    		area();

    		c.drawBitmap(ship, s.x, s.y, null);
    	}
    	
	else{
		
		Path path = new Path();
	    boolean first = true;
	    for(PointF point : points){
	        if(first){
	            first = false;
	            path.moveTo(point.x, point.y);
	        }
	        else{
	            path.lineTo(point.x, point.y);
	        }
	    }
	    lastPoint = points.get(points.size()-1);
	    c.drawPath(path, p);
		Matrix mxTransform = new Matrix();
	    PathMeasure pm = new PathMeasure(path, false);
	    float fSegmentLen = speed;//speed

	    if (iCurStep <= 1000) {
	        pm.getMatrix(fSegmentLen * iCurStep, mxTransform,
	            PathMeasure.POSITION_MATRIX_FLAG);
	        shipFollowPath(c, mxTransform, null, p, lastPoint, points);
	        iCurStep++;
	    } else {
	        iCurStep = 0;
	        points.clear();
	    };    	   
	}
    }
    
    public void shipFollowPath (Canvas c, Matrix mxTransform, Object object, Paint p, PointF lastPoint, CopyOnWriteArrayList<PointF> points2){
    	mxTransform.getValues(values);
    	x = values[2];
    	y = values[5];
    	s.x = x-(ship.getWidth()/2);
    	s.y = y-(ship.getHeight()/2);


    	Matrix matrix = new Matrix();
    	matrix.postTranslate(s.x, s.y);
    	area();
    	c.drawBitmap(ship, matrix, null);	
    	if (s.x == lastPoint.x - (ship.getWidth()/2) && s.y == lastPoint.y - (ship.getHeight()/2)){
    		System.out.println("Reached");
    		removePoints();
    	}
    }
    
    public float calculateAngle(float spriteX, float spriteY, float destX, float destY){
    	float angle = 0;
    	angle = (float) Math.toDegrees(Math.atan((spriteX - destX)/(spriteY - destY)));
    	
    	return angle;
    }
}

