package com.mjavacam.tmdb;

import java.io.IOException;
import java.util.ArrayList;

import org.mjavacam.wrapper.tmdb.TMDb;
import org.mjavacam.wrapper.tmdb.collections.GenreList;
import org.mjavacam.wrapper.tmdb.movie.Genre;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnClickListener {

	ArrayAdapter<Genre> adapter;
	ListView lvGenres;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvGenres = (ListView) findViewById(R.id.lvGenres);
		
		lvGenres.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TwoLineListItem row = (TwoLineListItem) view;
				String genreId = row.getText2().getText().toString();
				Intent intent = new Intent(MainActivity.this,MovieActivity.class);
				intent.putExtra("genreId", genreId);
				Log.d("LISTVIEW_ID",genreId);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		// if (v.getId() == R.id.btnLoad) {
		final LoadData data = new LoadData();
		context = this;
		Log.d("TAG", "Button Pressed!");
		lvGenres = (ListView) findViewById(R.id.lvGenres);
		data.execute();

		// }// end of if

	}
	
	private class LoadData extends AsyncTask<Void, Void, String> {

		@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		@Override
		protected String doInBackground(Void... params) {

			final TMDb tmdb = new TMDb();
			String data = "";
			try {
				TrafficStats.setThreadStatsTag(0xF00D);
				data = tmdb.GetAllGenres();
				// Log.d("TAG-DATA", data);

				// Log.d("TAG", "RUN STARTED!");
				return data;
			} catch (IOException e) {
				Log.d("ERROR", e.getMessage());
				e.printStackTrace();
			} finally {
				TrafficStats.clearThreadStatsTag();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(context, "FETCHING DATA", Toast.LENGTH_SHORT).show();
			ObjectMapper mapper = new ObjectMapper();
			try {
				lvGenres = (ListView) findViewById(R.id.lvGenres);
				GenreList list = mapper.readValue(result, GenreList.class);
				final ArrayList<Genre> genres = new ArrayList<Genre>();
				for (int i = 0; i < list.getGenres().size(); i++) {
					genres.add(list.getGenres().get(i));
				}
				// adapter = new
				// ArrayAdapter<String>(context,R.layout.list_item,R.id.tvText,genres);
				adapter = new ArrayAdapter<Genre>(context,
						android.R.layout.simple_list_item_2,genres) {

					@Override
					public View getView(int position, View convertView,ViewGroup parent) {
						TwoLineListItem row;
						if(convertView == null){
							LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
						}else{
							row = (TwoLineListItem) convertView;
						}
						Genre data = genres.get(position);
						row.getText1().setTextColor(R.style.AppTheme);
						row.getText1().setText(data.getName());
//						ColorStateList color = new ColorStateList(states, colors)
					
						row.getText2().setText(data.getId().toString());
						row.getText2().setTextColor(R.style.AppTheme);
						return row;
					}

				};
				
				adapter.notifyDataSetChanged();
				lvGenres.setAdapter(adapter);
				Log.d("act", result);
				Log.d("FROM ARRAY", genres.get(0).toString());
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
