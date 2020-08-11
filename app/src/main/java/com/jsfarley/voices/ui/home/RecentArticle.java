package com.jsfarley.voices.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.jsfarley.voices.R;


public class RecentArticle extends AppCompatActivity {
	ImageView articleImage;
	TextView articleTitle;
	TextView articleDate;
	TextView articleContent;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);
		articleImage = findViewById(R.id.articleImage);
		articleTitle = findViewById(R.id.articleTitle);
		articleDate = findViewById(R.id.articleDate);
		articleContent = findViewById(R.id.articleContent);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Intents and bundle
		Intent intent = getIntent();
		Bundle bundleData = intent.getExtras();
		assert bundleData != null;
		Recent view = (Recent)bundleData.getSerializable("readArticle");
		assert view != null;
		getSupportActionBar().setTitle(view.getArticleTitle());
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		RecentArticle recentArticle = new RecentArticle();


		//Accesses Recent Class and Gets the title, image, etc. and loads from json networking call
		articleTitle.setText(view.getArticleTitle());
		articleDate.setText(view.getArticleDate());
		articleContent.setText(view.getArticleContent());
		Glide.with(this.getApplicationContext())
				.load(view.getArticleImage())
				.placeholder(R.drawable.placeholder)
				.into(articleImage);
		//articleImage.setImageResource(view.getArticleImage());

	}
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
