package com.example.wfprssreader;

public class HeadlineItem {
	private String headline_title;
	private String headline_date;
	
	public HeadlineItem(String _headline_title, String _headline_date) {
		headline_title = _headline_title;
		headline_date = _headline_date;
	}
	
	public String getHeadlineTitle() {
		return headline_title;
	}
	
	public String getHeadlineDate() {
		return headline_date;
	}
	
	public void setHeadlineName(String _headline_title) {
		headline_title = _headline_title;
	}
	
	public void setHeadlineDate(String _headline_date) {
		headline_date = _headline_date;
	}
}
