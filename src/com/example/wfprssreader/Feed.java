package com.example.wfprssreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class Feed {
	//public static int count = 0;
	private String name = "";
	private String url = "";
	private ArrayList<String> feed_headlines = new ArrayList<String>();
	private ArrayList<String> articles = new ArrayList<String>();
	private ArrayList<String> dates = new ArrayList<String>();
	private ArrayList<String> links = new ArrayList<String>();
	private ArrayList<HeadlineItem> headline_items = new ArrayList<HeadlineItem>();
	RSSFeeder feeder;
	
	public Feed (String _url) {
		url = _url;
		refreshFeed();
	}
	
	public void refreshFeed() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public ArrayList<String> getFeedHeadlines() {
		return feed_headlines;
	}
	
	public ArrayList<String> getArticles() {
		return articles;
	}
	
	public ArrayList<String> getLinks() {
		return links;
	}
	
	public ArrayList<String> getDates() {
		return dates;
	}
	
	public ArrayList<HeadlineItem> getHeadlineItems(){
		return headline_items;
	}
	
	
	public class RSSFeeder extends AsyncTask<Void, Void, Void> {
		SAXHandler handler;
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL xml_file = new URL(url);
				BufferedReader in = new BufferedReader(new InputStreamReader(xml_file.openStream()));
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				handler = new SAXHandler();
				sp.parse(new InputSource(in), handler);
			} catch (MalformedURLException e) {
				Log.e("RSSFeederException", e.getMessage());
			} catch (IOException e) {
				Log.e("RSSFeederException", e.getMessage());
			} catch (SAXException e) {
				Log.e("RSSFeederException", e.getMessage());
			} catch (ParserConfigurationException e) {
				Log.e("RSSFeederException", e.getMessage());
			}
			return null;
		}
		
		public void populateHeadlineItems(){
			for (int i = 0; i < feed_headlines.size(); i++){
				headline_items.add(new HeadlineItem(feed_headlines.get(i), dates.get(i)));
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			FeedFragment result_frag = (FeedFragment) MainActivity.activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			
			if (result_frag == null) {
				result_frag = (FeedFragment) MainActivity.activity.getSupportFragmentManager().findFragmentById(R.id.feed_fragment);
			}
			
			feed_headlines = handler.getFeedHeadlines();
			articles = handler.getArticles();
			links = handler.getLinks();
			dates = handler.getDates();
			populateHeadlineItems();
			result_frag.refreshData(headline_items);
		}
	}	
}