package com.jsfarley.voices.ui.listen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jsfarley.voices.R;
import com.jsfarley.voices.ui.watch.VideoPlayer;

import java.util.List;

public class ListenViewModel extends ViewModel {
	//private MutableLiveData<String> mText;
	//Default constructor
	public ListenViewModel() {
		//mText = new MutableLiveData<>();d
		//mText.setValue("Listen Fragment List View");
	}

	/*public LiveData<String> getText() {
		return mText;
	}*/

	 public static class ListenRecyclerViewAdapter extends RecyclerView.Adapter<ListenRecyclerViewAdapter.ViewHolder>{
		Context mContext;
		List<Listen> allListenItems;
		LayoutInflater mLayoutInflater;

		public ListenRecyclerViewAdapter(Context context, List<Listen> listeners){
			this.mContext = context;
			this.allListenItems = listeners;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = mLayoutInflater.from(parent.getContext()).inflate(R.layout.listen_listview_item, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
			holder.trackTitle.setText(allListenItems.get(position).getTrackTitle());
			//Glide loads thumbnail image from network call
			Glide.with(mContext)
					.load(allListenItems.get(position).getTrackThumbnail())
					.placeholder(R.drawable.placeholder)
					.into(holder.trackIcon);
			holder.trackExcerpt.setText(allListenItems.get(position).getTrackExcerpt());
			holder.trackDate.setText(allListenItems.get(position).getTrackDate());
		}

		@Override
		public int getItemCount() {
			return allListenItems.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			TextView trackTitle;
			TextView trackExcerpt;
			TextView trackDate;
			ImageView trackIcon;
			View mView;

			public ViewHolder(@NonNull View itemView){
				super(itemView);
				mView = itemView;
				trackTitle = itemView.findViewById(R.id.trackTitle);
				trackExcerpt = itemView.findViewById(R.id.trackExcerpt);
				trackIcon = itemView.findViewById(R.id.trackThumbnail);
				trackDate = itemView.findViewById(R.id.trackDate);

				//TODO: Bind list item view to listen_playback.xml
				//Sets onClickListener (will transition to next screen) for now displays toast message
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//Toast.makeText(view.getContext(), "Track Selected " + getAdapterPosition(), Toast.LENGTH_LONG).show();
						Bundle bundle = new Bundle();
						bundle.putSerializable("listenPlayback", allListenItems.get(getAdapterPosition()));
						Intent intent = new Intent(mContext, ListenPlayback.class);
						intent.putExtras(bundle);
						view.getContext().startActivity(intent);

					}
				});

			}
		}
	}
}