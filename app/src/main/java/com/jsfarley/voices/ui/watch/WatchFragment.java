package com.jsfarley.voices.ui.watch;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jsfarley.voices.MainActivity;
import com.jsfarley.voices.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WatchFragment extends Fragment  {
	RecyclerView mRecyclerView;
	final static int SpanCount = 1;
	private static final String TAG = MainActivity.class.getSimpleName();
	List<String> titles;
	List<Integer> images;
	List<Watch> allWatchItems;
	WatchViewModel.RecyclerViewAdapter mAdapter;
	//private WatchViewModel mWatchViewModel;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		//mWatchViewModel = ViewModelProviders.of(this).get(WatchViewModel.class);
		View root = inflater.inflate(R.layout.fragment_watch, container, false);
		titles = new ArrayList<>();
		images = new ArrayList<>();
		allWatchItems = new ArrayList<>();
		mAdapter = new WatchViewModel.RecyclerViewAdapter(this.getActivity(), allWatchItems);
		//RecyclerView
		mRecyclerView = (RecyclerView)root.findViewById(R.id.watch_gallery_view);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),SpanCount,
				GridLayoutManager.VERTICAL, false));
		mRecyclerView.setAdapter(mAdapter);

		getJsonData();
		return root;
	}
	//Networking with Volley
	private void getJsonData() {
		//Constants
		final String watchURL = "https://jsfarley.com/voices/api/watchdbConnection.php";
		RequestQueue requestQueue;
		requestQueue = Volley.newRequestQueue(this.getActivity());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
				watchURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//Log.d(TAG, "onResponse" + response);
				try{
					//need to extract json array first since there's only one
					JSONArray watchItems = response.getJSONArray("WatchItems");

					for(int i = 0; i< watchItems.length(); i++){
						JSONObject jsonObject = watchItems.getJSONObject(i);
						Watch watch = new Watch();
						watch.setID(jsonObject.getInt("ID"));
						watch.setWatchTitle(jsonObject.getString("watch_title"));
						watch.setWatchDescription(jsonObject.getString("watch_description"));
						watch.setWatchDate(jsonObject.getString("watch_date"));
						watch.setWatchSource(jsonObject.getString("watch_source"));
						watch.setWatchThumbnail(jsonObject.getString("watch_thumbnail"));
						//Log.d("Watch", "onResponse: " + watch.getWatchThumbnail());
						allWatchItems.add(watch);
						mAdapter.notifyDataSetChanged();
					}
				}catch (JSONException e){
					e.printStackTrace();
				}
			}
		}, //Handles error response if error persist.
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						//Toast.makeText(getApplicationContext(), "That didn't work", Toast.LENGTH_LONG).show();
						Log.d(TAG, "onErrorResponse" + error.getMessage());
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

}