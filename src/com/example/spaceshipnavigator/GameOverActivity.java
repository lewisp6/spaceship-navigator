package com.example.spaceshipnavigator;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.spaceshipnavigator.Database.ScoreDBContract;
import com.example.spaceshipnavigator.Database.ScoreHelper;
import com.google.gson.Gson;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends Activity{
	ScoreHelper scoreHelper;
    String score;
    String[] scoreName = new String[2];
    Gson scores = new Gson();
    private String filename = "test.json";
    TextView scoreView;
    EditText userName;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        scoreHelper = new ScoreHelper(this);        
        setContentView(R.layout.custom);
        Bundle data = getIntent().getExtras();
        score = this.getIntent().getExtras().getString("score");
        System.out.println("EXTRAS = " + getIntent().getStringExtra("score"));
        scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText("Score: " + score);
        userName = (EditText) findViewById(R.id.editText1);
        SaveScore();
	}

	public void SaveScore(){
		Button save = (Button)findViewById(R.id.saveButton);
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                SQLiteDatabase db = scoreHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("Select * from " + ScoreDBContract.ScoreEntry.TABLE_NAME,
                        new String[]{});
                if(cursor.moveToFirst()){
	                try {
	                    scoreHelper.addEntry(db, userName.getText().toString(), "" + score);
	                     System.out.println("Populated Table");
	                    db.close();
	                } catch (Exception e) {
	                	System.out.println(e);
	                }
                }
                else{
                	scoreHelper.onCreate(db);
                	scoreHelper.addEntry(db, userName.getText().toString(), "" + score);
                    System.out.println("Populated Table");
                	db.close();
                }
			}
		});
	}
	
	public void restartGame(){
		Button restart = (Button)findViewById(R.id.restart);
			restart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Here");
				Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				GameOverActivity.this.startActivity(intent);
			}
		});
	}
	
	/**
     * Callback method defined by the View
     * @param v
     */
    public void finishDialog(View v) {
    	//SaveScore();
       /* GameOverActivity.this.finish();
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);*/
    }
}
