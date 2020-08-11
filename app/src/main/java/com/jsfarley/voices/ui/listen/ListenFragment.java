package com.jsfarley.voices.ui.listen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jsfarley.voices.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenFragment extends Fragment {
	private static final String TAG = "TAG";
	RecyclerView mRecyclerView;
    List<String> trackTitle;
    List<Integer> trackIcon;
    List<String> trackDate;
    List<String> trackExcerpt;
    List<Listen> allListenItems;
    ListenViewModel.ListenRecyclerViewAdapter mListenRecyclerViewAdapter;
	private ListenViewModel mListenViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
	                         ViewGroup container, Bundle savedInstanceState) {
		mListenViewModel = ViewModelProviders.of(this).get(ListenViewModel.class);
		View root = inflater.inflate(R.layout.fragment_listen, container, false);
		trackTitle = new ArrayList<>();
		trackIcon = new ArrayList<>();
		trackDate = new ArrayList<>();
		trackExcerpt = new ArrayList<>();
		allListenItems = new ArrayList<>();
		mListenRecyclerViewAdapter = new ListenViewModel.ListenRecyclerViewAdapter(this.getActivity(), allListenItems);

		//RecyclerView
		mRecyclerView = (RecyclerView)root.findViewById(R.id.listen_list_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),
				LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setAdapter(mListenRecyclerViewAdapter);

		getJsonData();

		return root;
	}
	//Networking with Volley
	private void getJsonData() {
		final String listenURL = "https://jsfarley.com/voices/api/listendbConnection.php";
		RequestQueue requestQueue;
		requestQueue = Volley.newRequestQueue(this.getActivity());
		final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
				listenURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//Log.d(TAG, "onResponse: " + response);
				try {
					JSONArray listenItems = response.getJSONArray("ListenItems");
					for (int i = 0; i < listenItems.length(); i++){
						JSONObject jsonObject = listenItems.getJSONObject(i);
						Listen listen = new Listen();
						listen.setID(jsonObject.getInt("ID"));
						listen.setTrackTitle(jsonObject.getString("listen_title"));
						listen.setTrackSource(jsonObject.getString("listen_source"));
						listen.setTrackThumbnail(jsonObject.getString("listen_thumbnail"));
						listen.setTrackExcerpt(jsonObject.getString("listen_description"));
						listen.setTrackDate(jsonObject.getString("listen_date"));
						allListenItems.add(listen);
						mListenRecyclerViewAdapter.notifyDataSetChanged();
					}

				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}, //Handles error response if error persist.
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "onErrorResponse" + error.getMessage());
					}
				});
		requestQueue.add(jsonObjectRequest);
	}
}