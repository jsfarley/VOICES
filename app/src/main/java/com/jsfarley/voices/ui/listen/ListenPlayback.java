package com.jsfarley.voices.ui.listen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.jsfarley.voices.MainActivity;
import com.jsfarley.voices.R;

import java.io.IOException;


public class ListenPlayback extends AppCompatActivity  {
	private SeekBar mSeekBar;
	private MediaPlayer mMediaPlayer;
	private TextView trackTitle;
	private TextView trackExcerpt;
	private ImageView trackThumbnailView;
	private ImageView trackPlayButton;
	private Util mUtilities;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listen_playback);
		mSeekBar = findViewById(R.id.seekBar);
		trackTitle = findViewById(R.id.listenTrackTitle);
		trackExcerpt = findViewById(R.id.listenTrackExcerpt);
		trackThumbnailView = findViewById(R.id.trackThumbnail);
		trackPlayButton = findViewById(R.id.play_pauseButton);

		//Navigate back to fragment
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//get intents from click to individual playback
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		//Make sure to access Fragment class not ViewModel Fragment class.
		Listen view = (Listen)data.getSerializable("listenPlayback");
		getSupportActionBar().setTitle(view.getTrackTitle());

		//Sets track_source to a string and starts async task for mediaplayer.
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
		}
		String url = view.getTrackSource();
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(url);
			mMediaPlayer.prepareAsync();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				mSeekBar.setMax(mMediaPlayer.getDuration());
				playMusic();
			}
		});
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				onStop();
			}
		});
		//sets up the seekbar action
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (mMediaPlayer !=null && fromUser){
					mMediaPlayer.seekTo(progress);
					mSeekBar.setProgress(progress);
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			//make sure to add removeCallback otherwise will crash when trying to exit
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				handler.removeCallbacks(null);
			}
		});
		//Seek bar runs on UI Thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mMediaPlayer != null) {
					try {
						if (mMediaPlayer.isPlaying()) {
							Message message = new Message();
							message.what = mMediaPlayer.getCurrentPosition();
							handler.sendMessage(message);
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		trackTitle.setText(view.getTrackTitle());
		trackExcerpt.setText(view.getTrackExcerpt());
		Glide.with(this.getApplicationContext())
				.load(view.getTrackThumbnail())
				.placeholder(R.drawable.placeholder)
				.into(trackThumbnailView);
	}
	private void playMusic() {
		trackPlayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if( mMediaPlayer != null && mMediaPlayer.isPlaying()){
					mMediaPlayer.pause();
					trackPlayButton.setImageResource(R.drawable.play_button);
				} else{
					assert mMediaPlayer != null;
					mMediaPlayer.start();
					trackPlayButton.setImageResource(R.drawable.pause_button);
				}
			}
		});
	}
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(@NonNull Message msg) {
			mSeekBar.setProgress(msg.what);
		}
	};
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
			if (mMediaPlayer.isPlaying() || mMediaPlayer != null){
				mMediaPlayer.stop();
				mMediaPlayer.release();
				if (handler != null){
					handler.removeCallbacks(null);
				}
			}
		}
		finish();
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		super.onResume();
		playMusic();
	}
	@Override
	protected void onPause() {
		super.onPause();
		mMediaPlayer.pause();
	}
	@Override
	protected void onStop() {
		super.onStop();
		releasePlayer();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releasePlayer();
	}
	private void releasePlayer() {
		if (mMediaPlayer != null){
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		if (handler != null){
			handler.removeCallbacks(null);
		}
	}
}
