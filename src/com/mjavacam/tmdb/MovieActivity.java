package com.mjavacam.tmdb;

import java.io.IOException;

import org.mjavacam.wrapper.tmdb.TMDb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MovieActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}
	
	private class GetMovieList extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			TMDb tmdb = new TMDb();
			String data = "";
			Intent intent = getIntent();
			String genreId = intent.getExtras().getString("genreId");
			try {
				data = tmdb.GetAllMoviesByGenreId(genreId);
			} catch (IOException e) {
				Log.d("ERROR",e.getMessage());
				e.printStackTrace();
			}
			
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//TextView tvText = findViewById(R.id.)
		}
		
	}

}
