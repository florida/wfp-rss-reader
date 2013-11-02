package com.example.wfprssreader;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
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
	
	public interface OnFeedSelectedListener {
		public void onArticleSelected(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.feed_headlines_view,container, false);
		final ListView listview = (ListView) view.findViewById(R.id.feed_headlines_list);
		
		adapter = new FeedListAdapter(getActivity(), MainActivity.feed.getHeadlineItems());
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				callback.onArticleSelected(position);
				listview.setItemChecked(position, true);
			}
			
		});
		
		return super.onCreateView(inflater, container, savedInstanceState);
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
