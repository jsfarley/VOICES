package com.jsfarley.voices.ui.watch;

import java.io.Serializable;

public class Watch implements Serializable {
	private int ID;
	private String watchSource;
	private String watchTitle;
	private String watchDescription;
	private String watchDate;
	private String watchThumbnail;

	public String getWatchThumbnail() {
		return watchThumbnail;
	}

	public void setWatchThumbnail(String watchThumbnail) {
		this.watchThumbnail = watchThumbnail;
	}

	public String getWatchDate() {
		return watchDate;
	}

	public void setWatchDate(String watchDate) {
		this.watchDate = watchDate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getWatchSource() {
		return watchSource;
	}

	public void setWatchSource(String watchSource) {
		this.watchSource = watchSource;
	}

	public String getWatchTitle() {
		return watchTitle;
	}

	public void setWatchTitle(String watchTitle) {
		this.watchTitle = watchTitle;
	}

	public String getWatchDescription() {
		return watchDescription;
	}

	public void setWatchDescription(String watchDescription) {
		this.watchDescription = watchDescription;
	}


}
