/**
 * 
 */
package org.mjavacam.wrapper.tmdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

/**
 * @author SapnaRaver
 * 
 */
public class TMDb {
	private static String _apiKey;
	private final String BASE_API_URL = "http://api.themoviedb.org/3/";

	// This is the private fields for the configuration

	public TMDb() {
		setApiKey("3828ed361d9bc89c8cf878df2275c368");
	}

	public TMDb(String apiKey) {
		setApiKey(apiKey);
	}

	public String getApiKey() {
		return _apiKey;
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public String SearchMovieById(String movieId) throws IOException {
		String url = BASE_API_URL + "movie/" + stringEscape(movieId) + "?api_key="
				+ getApiKey();
		// return url;
		return getJSON(url);

	}

	private String stringEscape(String queryString)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(queryString, "UTF-8");
	}

	public String SearchMovieByTitle(String title) throws IOException {
		String url = BASE_API_URL + "search/movie?api_key=" + getApiKey()
				+ "&query=" + stringEscape(title);
		// return url;
		return getJSON(url);
	}

	public String SearchMovieByTitle(String title, int page) throws IOException {
		String url = BASE_API_URL + "search/movie?api_key=" + getApiKey()
				+ "&query=" + stringEscape(title) + "&page=" + page;
		// return url;
		return getJSON(url);
	}

	public String SearchMovieByTitle(String title, int page, String language)
			throws IOException {
		String url = BASE_API_URL + "search/movie?api_key=" + getApiKey()
				+ "&query=" + stringEscape(title) + "&page=" + page
				+ "&language=" + stringEscape(language);
		// return url;

		return getJSON(url);
	}

	public String SearchMovieByTitle(String title, int page, String language,
			Boolean includeAdult) throws IOException {
		String url = BASE_API_URL + "search/movie?api_key=" + getApiKey()
				+ "&query=" + stringEscape(title) + "&page=" + page
				+ "&language=" + stringEscape(language) + "&include_adult="
				+ includeAdult;
		// return url;
		return getJSON(url);
	}

	public String SearchMovieByTitle(String title, int page, String language,
			Boolean includeAdult, String year) throws IOException {
		String url = BASE_API_URL + "search/movie?api_key=" + getApiKey()
				+ "&query=" + stringEscape(title) + "&page=" + page
				+ "&language=" + stringEscape(language) + "&include_adult="
				+ includeAdult + stringEscape(year);
		// return url;

		return getJSON(url);
	}

	public String GetAllGenres() throws IOException {
		String url = BASE_API_URL + "genre/list" + "?api_key=" + getApiKey();
		// return url;
		return getJSON(url);

	}

	public String GetAllGenres(String language) throws IOException {
		String url = BASE_API_URL + "genre/list" + "?api_key=" + getApiKey()
				+ "&language=" + language;
		// return url;
		return getJSON(url);

	}

	public String GetAllMoviesByGenreId(String genreId) throws IOException {
		String url = BASE_API_URL + "genre/" + stringEscape(genreId) + "/movies"
				+ "?api_key=" + getApiKey();
		// return url;
		return getJSON(url);
	}

	/**
	 * JSON Parser http://stackoverflow.com/questions/10500775/parse-json-from-
	 * httpurlconnection-object
	 * 
	 * @param url
	 *            The url of the json result.
	 * @param timeout
	 *            The time before the query times out
	 * @return String of JSON data
	 * @throws IOException
	 */
	/*
	 * public String getJSON(String url) throws IOException { URL u = new
	 * URL(url); HttpURLConnection c = (HttpURLConnection) u.openConnection();
	 * try {
	 * 
	 * c.setRequestMethod("GET"); c.setRequestProperty("Content-length", "0");
	 * c.setUseCaches(false); c.setAllowUserInteraction(false);
	 * 
	 * c.connect(); int status = c.getResponseCode();
	 * 
	 * switch (status) { case 200: case 201: BufferedReader br = new
	 * BufferedReader(new InputStreamReader( c.getInputStream())); StringBuilder
	 * sb = new StringBuilder(); String line; while ((line = br.readLine()) !=
	 * null) { sb.append(line + "\n"); } br.close(); return sb.toString(); }
	 * 
	 * }finally{ c.disconnect(); } return null; }
	 */

	public String getJSON(String url) throws IOException {

		HttpGet post = new HttpGet(url);
		post.addHeader("accept", "application/json");
//		post.
		AndroidHttpClient client = AndroidHttpClient.newInstance("DATA");
		
		HttpResponse response = client.execute(post);
		client.close();
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		
		return result;

	}
}