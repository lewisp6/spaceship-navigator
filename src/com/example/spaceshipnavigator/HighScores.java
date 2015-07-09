package com.example.spaceshipnavigator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.spaceshipnavigator.Database.ScoreDBContract;
import com.example.spaceshipnavigator.Database.ScoreHelper;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighScores extends ListActivity{
	String a = null;
    ScoreHelper scoreHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        scoreHelper = new ScoreHelper(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.high_scores);
		//BufferedReader inputReader;

	    
        try {
            SQLiteDatabase db = scoreHelper.getWritableDatabase();
            try{
            	String concat;
            	ArrayList<String> scoreList = new ArrayList<String>();
                Cursor cursor = db.rawQuery("Select * from " + ScoreDBContract.ScoreEntry.TABLE_NAME + 
                		" ORDER BY " + ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC", new String[]{});
                cursor.moveToFirst();
                int scoreIndex = cursor.getColumnIndex(ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE);
                int nameIndex = cursor.getColumnIndex(ScoreDBContract.ScoreEntry.COLUMN_NAME_PERSON);
                System.out.println( "Score column index = " + scoreIndex);
                while (!cursor.isAfterLast()) {
                    int score = cursor.getInt(scoreIndex);
                    String name = cursor.getString(nameIndex);
                    concat = name + " " + score;
                    scoreList.add(concat);
                    System.out.println(name +" : "+score);
                    boolean flag = cursor.moveToNext();
                }
                
                this.setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, scoreList));
            }   catch(Exception e) {
            }
            
            db.close();
        } catch (Exception e) {
        }
	}
}
