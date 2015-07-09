package com.example.spaceshipnavigator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity{
	GameView view;
    static String CURRENT_SCORE = "Score";
    
    
	 	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        view = new GameView(this);

	        setContentView(view);
	        
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	  }
		@Override
	    public void onPause() {
	        super.onPause();
	        view.thread.running = false;  
	    }
		
		/* Save the users score onDestroy, the idea here was to save the state of the game including
		 * each ships current positions and each point of each line the user had currently drawn
		 * but I ran out of time before I could implement it*/
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState){
		    try {
	            savedInstanceState.putInt(CURRENT_SCORE, view.thread.gameState.score);
	            // savedInstanceState.put
	        } catch (Exception e) {
	            System.out.println("TestActivity: onSave" + e);
	        }
	        super.onSaveInstanceState(savedInstanceState);
		}
		
	    public void onRestoreInstanceState(Bundle savedInstanceState) {
	        // Always call the superclass so it can restore the view hierarchy
	        System.out.println("TestActivity: restoreInstanceState() " + savedInstanceState);
	        super.onRestoreInstanceState(savedInstanceState);
	        try {
	            view.thread.gameState.score = savedInstanceState.getInt(CURRENT_SCORE);
	        } catch (Exception e) {
	            System.out.println("TestActivity: onRestore" + e);
	        }
	    }
		
		
		
}
