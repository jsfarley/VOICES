package com.jsfarley.voices;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class Splash extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadSplash();
	}

	private void loadSplash() {
		EasySplashScreen easySplashScreen = new EasySplashScreen(Splash.this)
				.withFullScreen()
				.withTargetActivity(MainActivity.class)
				.withBackgroundColor(Color.parseColor("#242426"))
				.withLogo(R.drawable.voices_light_logo)
				.withSplashTimeOut(3000);
		View easySplash = easySplashScreen.create();
		setContentView(easySplash);
	}
}
