package com.jsfarley.voices.ui.watch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jsfarley.voices.R;

import java.util.List;

public class WatchViewModel extends ViewModel {
	//
	//Default constructor
	public WatchViewModel() {
	}

  public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
	  LayoutInflater mLayoutInflater;
	  Context mContext;
	  List<Watch> allWatchItems;

	  public RecyclerViewAdapter(Context context, List<Watch> watch){
	  	this.allWatchItems = watch;
		  this.mContext = context;
	  }

	  @NonNull
	  @Override
	  public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	  	View view = mLayoutInflater.from(parent.getContext()).inflate(R.layout.watch_gridview_item, parent, false);
		  return new ViewHolder(view);
	  }

	  @Override
	  public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
		//holder.cardTitle.setText(titles.get(position));
		holder.cardTitle.setText(allWatchItems.get(position).getWatchTitle());
		  Glide.with(mContext)
				  .load(allWatchItems.get(position).getWatchThumbnail())
				  .placeholder(R.drawable.ic_menu_slideshow)
				  .into(holder.gridIcon);
		//Log.d("TAG", "loadImage: " + allWatchItems.get(position).getWatchThumbnail());
		//TODO:(not really) binds intent view to new view. optional to have in onBindViewHolder or in ViewHolder
		 /* holder.mView.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View view) {
				  //Snackbar.make(view, "Clicked " + allWatchItems.get(position).getWatchTitle(), Snackbar.LENGTH_LONG).show();
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("watchVideo", allWatchItems.get(position));
				  Intent intent = new Intent(mContext, VideoPlayer.class);
				  intent.putExtras(bundle);
				  view.getContext().startActivity(intent);

			  }
		  });*/
	  }

	  @Override
	  public int getItemCount() {
		  return allWatchItems.size();
	  }
	  public class ViewHolder extends RecyclerView.ViewHolder{
	  	    View mView;
			TextView cardTitle;
			ImageView gridIcon;

		  public ViewHolder(@NonNull View itemView) {
			  super(itemView);
			  mView = itemView;
			  cardTitle = itemView.findViewById(R.id.cardTextView);
			  gridIcon = itemView.findViewById(R.id.cardImageView);
			  //click into grid item
			  itemView.setOnClickListener(new View.OnClickListener() {
				  @Override
				  public void onClick(View view) {
					 // Toast.makeText(view.getContext(), "Clicked" + getAdapterPosition(), Toast.LENGTH_LONG).show();
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("watchVideo", allWatchItems.get(getAdapterPosition()));
				  Intent intent = new Intent(mContext, VideoPlayer.class);
				  intent.putExtras(bundle);
				  view.getContext().startActivity(intent);

				  }
			  });
		  }
	  }
  }
}