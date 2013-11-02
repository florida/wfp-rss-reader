package com.example.wfprssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
			
		} else if (current_position != -1){
			
		}
	}
	
	public void updateArticleView(int position){
		TextView article = (TextView) getActivity().findViewById(R.id.article);
		TextView article_link = (TextView) getActivity().findViewById(R.id.article_link);
		 
		article.setText(MainActivity.feed.getArticles().get(position));
		article_link.setText(MainActivity.feed.getLinks().get(position));
		
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
	}
}
