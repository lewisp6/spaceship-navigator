package com.example.spaceshipnavigator;

import java.util.concurrent.CyclicBarrier;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Display;

public class DestinationPoints {
    float lRad, mRad, sRad;
    float lX, lY, mX, mY, sX, sY, lHeight, mHeight, sHeight, lWidth, mWidth, sWidth;
	Paint mp = new Paint();

	RectF destinationRect = new RectF();
	RectF lPlanetSuccessArea = new RectF();
	RectF mPlanetSuccessArea = new RectF();
	RectF sPlanetSuccessArea = new RectF();

    public void largeDestination(Bitmap bPlanet){    		
    	lHeight = bPlanet.getHeight();
    	lWidth = bPlanet.getWidth();
    	lX = 202;
    	lY = 211;
    	lPlanetSuccessArea = successArea(lX, lY, lWidth, lHeight);
    	mp.setColor(Color.GREEN);
    	mp.setStyle(Paint.Style.STROKE);
    }
    
    public void mediumDestination(Bitmap gPlanet){
    	mHeight = gPlanet.getHeight();
    	mWidth = gPlanet.getWidth();
    	mX = 400;
    	mY = 400;
    	mPlanetSuccessArea = successArea(mX, mY, mWidth, mHeight);
    	mp.setColor(Color.GREEN);
    	mp.setStyle(Paint.Style.STROKE);
    }
    
    public void smallDestination(Bitmap oPlanet){
    	sHeight = oPlanet.getHeight();
    	sWidth = oPlanet.getWidth();
    	sX = 650;
    	sY= 250;
    	sPlanetSuccessArea = successArea(sX, sY, sWidth, sHeight);
    	mp.setColor(Color.GREEN);
    	mp.setStyle(Paint.Style.STROKE);
    }
    
    public RectF successArea(float x, float y, float width, float height){
    	destinationRect = new RectF((float) (x + (width * 0.5)), (float) (y + (height * 0.5)), (float) ((x + width) - (width * 0.5)) , (float)((y + height) - (height * 0.5)));
    	return destinationRect;
    }
    
    public void draw(Canvas c, Bitmap bPlanet, Bitmap gPlanet, Bitmap oPlanet, Paint tp){
    	int bx = 0, byy = 0;
    	
    	largeDestination(bPlanet);	
    	mediumDestination(gPlanet);
    	smallDestination(oPlanet);

    	c.drawBitmap(bPlanet, lX, lY, null);
    	c.drawBitmap(gPlanet, mX, mY, null);
    	c.drawBitmap(oPlanet, sX, sY, null);
    	//Draw the bounding box for the destination points
    	//c.drawRect(lPlanetSuccessArea, mp);
    	
    }
    
}

