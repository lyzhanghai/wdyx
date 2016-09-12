package com.wdyx.weixin.web.art;

import java.util.List;

import com.wdyx.weixin.web.main.WebView;

public class Art extends WebView{
	private List<ArtReview> reviews;

	public List<ArtReview> getReviews() {
		return reviews;
	}

	public void setReviews(List<ArtReview> reviews) {
		this.reviews = reviews;
	}

}
