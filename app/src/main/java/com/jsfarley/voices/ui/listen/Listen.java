package com.jsfarley.voices.ui.listen;

import java.io.Serializable;

public class Listen implements Serializable {
	private String trackTitle;
	private String trackExcerpt;
	private String trackDate;
	private String trackSource;
	private String trackThumbnail;
	private int ID;

	public String getTrackTitle() {
		return trackTitle;
	}

	public void setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
	}

	public String getTrackExcerpt() {
		return trackExcerpt;
	}

	public void setTrackExcerpt(String trackExcerpt) {
		this.trackExcerpt = trackExcerpt;
	}

	public String getTrackDate() {
		return trackDate;
	}

	public void setTrackDate(String trackDate) {
		this.trackDate = trackDate;
	}

	public String getTrackSource() {
		return trackSource;
	}

	public void setTrackSource(String trackSource) {
		this.trackSource = trackSource;
	}

	public String getTrackThumbnail() {
		return trackThumbnail;
	}

	public void setTrackThumbnail(String trackThumbnail) {
		this.trackThumbnail = trackThumbnail;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
}
