package com.jsfarley.voices.ui.watch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jsfarley.voices.R;



public class VideoPlayer extends AppCompatActivity {
	private static final String TAG ="TAG" ;
	ProgressBar mProgressBar;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player);
		mProgressBar = findViewById(R.id.progressBar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		//Make sure to access Fragment not ViewModel
		//Gets serialized data from array list with a key.
		assert data != null;
		Watch view = (Watch)data.getSerializable("watchVideo");
		assert view != null;
		getSupportActionBar().setTitle(view.getWatchTitle());

		final VideoView videoView = findViewById(R.id.videoView);
		TextView videoTitle = findViewById(R.id.videoTitle);
		TextView videoExcerpt = findViewById(R.id.videoExcerpt);

		//Log.d(TAG, "OnClick: " + view.getWatchTitle());
		videoTitle.setText(view.getWatchTitle());
		videoExcerpt.setText(view.getWatchDescription());

		try{
			MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(videoView);
			Uri videoURL = Uri.parse(view.getWatchSource());
			videoView.setMediaController(mediaController);
			videoView.setVideoURI(videoURL);
		}catch(Exception e){
			Log.e("Playback Error", e.getMessage());
			e.printStackTrace();
		}

		//Waits for video buffer to complete
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// videoView.requestFocus();
				videoView.start();
				mProgressBar.setVisibility(View.GONE);

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
