package com.example.tuoitre_rss;

public class SingleItem {
	private String _title;
	private String _description;
	private String _pubDate;
	private String _link;
	

	public String getTitle() { 
		return _title;
	}
	
	public String getDescription() {
		return _description; 
	}
	
	public String getPubDate() { 
		return _pubDate; 
	}
	public String getLink() {
		return _link; 
	}
	
	public SingleItem(String title, String description, String pubDate, String link) {
		_pubDate = pubDate;
		_description = description;
		_title = title;
		_link = link;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return _title;
	}
	
	
}
