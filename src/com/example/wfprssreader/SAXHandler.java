package com.example.wfprssreader;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	private ArrayList<String> feed_headlines = new ArrayList<String>();
	private ArrayList<String> articles = new ArrayList<String>();
	private ArrayList<String> links = new ArrayList<String>();
	private ArrayList<String> dates = new ArrayList<String>();
	private StringBuffer accumulator = new StringBuffer(1024);
	private String pattern = "title|description|link|lastBuildDate|pubDate";
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if (qName.matches(pattern))
			accumulator.setLength(0);
		

	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		accumulator.append(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("title")) {
			feed_headlines.add(accumulator.toString().trim());
		} else if (qName.equals("description")) {
			articles.add(accumulator.toString().trim());
		} else if (qName.equals("link")) {
			links.add(accumulator.toString().trim());
		} else if (qName.equals("lastBuildDate") || qName.equals("pubDate")) {
			dates.add(accumulator.toString().trim());
		}
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
}
