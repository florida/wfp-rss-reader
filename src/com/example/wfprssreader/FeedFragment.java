package com.example.wfprssreader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FeedFragment extends Fragment{
	OnFeedSelectedListener callback;
	private FeedListAdapter adapter;
	ListView lv;
	public interface OnFeedSelectedListener {
		public void onArticleSelected(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.feed_headlines_view,container, false);
		final ListView listview = (ListView) view.findViewById(R.id.feed_headlines_list);
		
		lv = listview;
		adapter = new FeedListAdapter(getActivity(), MainActivity.feed.getHeadlineItems());
		
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				callback.onArticleSelected(position);
				listview.setItemChecked(position, true);
			}
		});
		
		changeFeedPreference();
		
		return view;
	}
	
	public void changeFeedPreference() {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.activity);
		String feed_background_color_key = (String) getResources().getText(R.string.pref_feed_background_color);
		lv.setBackgroundColor(settings.getInt(feed_background_color_key, Color.WHITE));
		}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			callback = (OnFeedSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implement OnFeedSelectedListener");
		}
	}
	
	public void refreshData(ArrayList<HeadlineItem> data) {
		adapter.refreshData(new ArrayList<HeadlineItem>(data));
	}
}
