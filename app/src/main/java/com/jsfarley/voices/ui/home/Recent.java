package com.jsfarley.voices.ui.home;

import java.io.Serializable;

public class Recent implements Serializable {
	private String articleTitle;
	private String articleDate;
	private String articleImage;
	private String articleContent;
	private String articleExcerpt;

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleExcerpt() {
		return articleExcerpt;
	}

	public void setArticleExcerpt(String articleExcerpt) {
		this.articleExcerpt = articleExcerpt;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}

	public String getArticleImage() {
		return articleImage;
	}

	public void setArticleImage(String articleImage) {
		this.articleImage = articleImage;
	}
}
