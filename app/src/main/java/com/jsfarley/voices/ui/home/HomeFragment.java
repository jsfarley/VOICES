package com.jsfarley.voices.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.jsfarley.voices.R;
import com.jsfarley.voices.ui.watch.Watch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

	private static final String TAG ="TAG" ;
	private HomeViewModel homeViewModel;
	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	RecyclerView mRecyclerView;
	List<String> articleTitle;
	List<String> articleExcerpt;
	List<Integer> articleImage;
	List<Recent> allRecentItems;
	HomeViewModel.HomeRecyclerViewAdapter mAdapter;


	public View onCreateView(@NonNull LayoutInflater inflater,
	                         ViewGroup container, Bundle savedInstanceState) {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);
		articleTitle = new ArrayList<>();
		articleExcerpt = new ArrayList<>();
		articleImage = new ArrayList<>();
		allRecentItems = new ArrayList<>();

		mAdapter = new HomeViewModel.HomeRecyclerViewAdapter(this.getActivity(), allRecentItems);
		//RecyclerView
		mRecyclerView = (RecyclerView)root.findViewById(R.id.recent_articles_list_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setAdapter(mAdapter);

		getJsonData();

		return root;
	}

	private void getJsonData() {
		//Constants
		final String articleURL = "https://jsfarley.com/voices/api/dbConnection.php";
		RequestQueue requestQueue;
		requestQueue = Volley.newRequestQueue(this.getActivity());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
				articleURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//Log.d(TAG, "onResponse" + response);
				try{
					//need to extract json array first since there's only one
					JSONArray voicesPost = response.getJSONArray("VoicesPost");

					for(int i = 0; i< voicesPost.length(); i++){
						JSONObject jsonObject = voicesPost.getJSONObject(i);
						Recent recent = new Recent();
						recent.setArticleTitle(jsonObject.getString("Post_Title"));
						recent.setArticleDate(jsonObject.getString("Post_Date"));
						recent.setArticleExcerpt(jsonObject.getString("Post_Excerpt"));
						recent.setArticleContent(jsonObject.getString("Post_Content"));
						recent.setArticleImage(jsonObject.getString("Post_ImageURL"));
						allRecentItems.add(recent);
						mAdapter.notifyDataSetChanged();
						Log.d(TAG,"onResponse: " + recent);
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