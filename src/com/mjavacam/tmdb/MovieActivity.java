package com.mjavacam.tmdb;

import java.io.IOException;
import java.util.ArrayList;

import org.mjavacam.wrapper.tmdb.TMDb;
import org.mjavacam.wrapper.tmdb.collections.MovieList;
import org.mjavacam.wrapper.tmdb.collections.Results;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidhive.imagefromurl.ImageLoader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Change the view to grid view
// Images of the movie and Added the capability to load image from url

// Change the view to grid view
// Images of the movie and 
@SuppressLint("DefaultLocale")
public class MovieActivity extends Activity {
	Context context;
	ArrayAdapter<?> adapter;
	ListView lvMovies;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		context = this; 
		lvMovies = (ListView) findViewById(R.id.listView1);
		GetMovieList data = new GetMovieList();
		data.execute();
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}
	
	private class GetMovieList extends AsyncTask<Void, Void, String>{
		TMDb tmdb = new TMDb();
		@Override
		protected String doInBackground(Void... params) {
			String data = "";
			Intent intent = getIntent();
			String genreId = intent.getExtras().getString("genreId");
			try {
				data = tmdb.GetAllMoviesByGenreId(genreId);
				Log.d("Movies",data);
				return data;
			} catch (IOException e) {
				Log.d("ERROR",e.getMessage());
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			ObjectMapper mapper = new ObjectMapper();
			MovieList moviesByGenre = null;
			//final ArrayList<Results> movies = new ArrayList<Results>();
			ArrayList<String> movies = new ArrayList<String>();
			try {
				if(!result.isEmpty())
					moviesByGenre = mapper.readValue(result, MovieList.class);
			} catch (JsonParseException e) {
				Log.d("ERROR",e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				Log.d("ERROR",e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("ERROR",e.getMessage());
				e.printStackTrace();
			}
			
			//movies.addAll(moviesByGenre.getResults());
			
			
			  for(int i =0;i < moviesByGenre.getResults().size(); i++){
			  		movies.add(moviesByGenre.getResults().get(i).getTitle().toString());
			}
			
			adapter = new ArrayAdapter<String>(context, R.layout.list_item,R.id.tvText,movies);
			// TODO Add the capability to load image from url here
//			adapter = new ArrayAdapter<Results>(context,R.layout.list_item,R.id.tvText,movies);
//					{
//				@Override
//				public View getView(int position, View convertView,
//						ViewGroup parent) {
//					// TODO Inflate layout here and lazy load images here
//					int loader = R.drawable.ic_launcher;
//					ImageView image = (ImageView) findViewById(R.id.image);
//					String url = tmdb.GetMoviePosterUrl(movies.get(0));
//					ImageLoader imgLoader = new ImageLoader(context);
//					imgLoader.DisplayImage(url, loader, image);
//					
//					return super.getView(position, convertView, parent);
//				}
//				
//			};
			
			adapter.notifyDataSetChanged();
			lvMovies.setAdapter(adapter);
		}
		
	}

}
