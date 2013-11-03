package com.example.wfprssreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
	final static String ARG_POSITION = "position";
	int current_position = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			current_position = savedInstanceState.getInt(ARG_POSITION);
		}
		
		return inflater.inflate(R.layout.articles_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			updateArticleView(args.getInt(ARG_POSITION));
		} else if (current_position != -1){
			updateArticleView(current_position);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) { 
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION, current_position);
	}
	
	public void updateArticleView(int position){
		TextView article = (TextView) getActivity().findViewById(R.id.article);
		TextView article_link = (TextView) getActivity().findViewById(R.id.article_link);
		 
		article.setText(MainActivity.feed.getArticles().get(position));
		
		current_position = position;
		
		if (article_link != null) {
			article_link.setOnClickListener(new OnClickListener() {
				private String link = ((TextView) getActivity().findViewById(R.id.article_link)).getText().toString();
				
				@Override
				public void onClick(View v) {
					Intent browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
					startActivity(browser_intent);
				}
			});
		}
		String article_font_color_key = (String) getResources().getText(R.string.pref_article_font_color);
		String article_font_size_key = (String) getResources().getText(R.string.pref_article_font_size);
		String article_background_color_key = (String) getResources().getText(R.string.pref_article_background_color);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.activity);
		article.setTextColor(settings.getInt(article_font_color_key, Color.BLACK));
		article.setTextSize(TypedValue.COMPLEX_UNIT_SP, settings.getInt(article_font_size_key, 14));
		article.setBackgroundColor(settings.getInt(article_background_color_key, Color.WHITE));
	}
}
