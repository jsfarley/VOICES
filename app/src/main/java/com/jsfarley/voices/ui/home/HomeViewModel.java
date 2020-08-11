package com.jsfarley.voices.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.jsfarley.voices.R;

import java.util.List;

public class HomeViewModel extends ViewModel {

	/*private MutableLiveData<String> mText;
	private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

	public HomeViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("Home Fragment Tabbed View");

	}

	public LiveData<String> getText() {
		return mText;
	}

	public void setIndex(int index) {
		mIndex.setValue(index);
	}*/

	public static class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>{
		LayoutInflater mLayoutInflater;
		Context mContext;
		List<Recent> allRecentItems;
		//default constructor
		public HomeRecyclerViewAdapter(Context context, List<Recent> recentItems) {
			this.mContext = context;
			this.allRecentItems = recentItems;
		}
		@NonNull
		@Override
		public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = mLayoutInflater.from(parent.getContext()).inflate(R.layout.recent_item_view, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder holder, final int position) {
			holder.articleTitle.setText(allRecentItems.get(position).getArticleTitle());
			holder.articleExcerpt.setText(allRecentItems.get(position).getArticleExcerpt());
			Glide.with(mContext)
					.load(allRecentItems.get(position).getArticleImage())
					.placeholder(R.drawable.placeholder)
					.into(holder.articleImage);
		}
		@Override
		public int getItemCount() {
			return allRecentItems.size();
		}
		public class ViewHolder extends RecyclerView.ViewHolder {
			View mView;
			TextView articleTitle;
			TextView articleExcerpt;
			ImageView articleImage;
			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				mView = itemView;
				articleTitle = itemView.findViewById(R.id.recentArticleTitle);
				articleExcerpt = itemView.findViewById(R.id.recentArticleExcerpt);
				articleImage = itemView.findViewById(R.id.recentArticleImage);
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Snackbar.make(view,"Item Clicked " + getAdapterPosition(), Snackbar.LENGTH_LONG).show();

						Bundle bundle = new Bundle();
						bundle.putSerializable("readArticle", allRecentItems.get(getAdapterPosition()));
						Intent intent = new Intent(mContext, RecentArticle.class);
						intent.putExtras(bundle);
						view.getContext().startActivity(intent);
					}
				});
			}
		}
	}
}