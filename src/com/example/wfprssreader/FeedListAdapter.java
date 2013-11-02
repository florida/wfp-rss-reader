package com.example.wfprssreader;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedListAdapter extends BaseAdapter {
	public ArrayList<HeadlineItem> list_data;
	private LayoutInflater layout_inflater;
	public FeedListAdapter(Context context, ArrayList<HeadlineItem> _list_data) {
		list_data = _list_data;
		layout_inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list_data.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list_data.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layout_inflater.inflate(R.layout.headline_item_row,null);
			holder = new ViewHolder();
			holder.headlineTitle = (TextView) convertView.findViewById(R.id.headlines_title);
			holder.headlineDate = (TextView) convertView.findViewById(R.id.headlines_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.headlineTitle.setText(list_data.get(position).getHeadlineTitle());
		holder.headlineDate.setText(list_data.get(position).getHeadlineDate());
		
		return convertView;
	}
	
	public void refreshData(ArrayList<HeadlineItem> data) {
		list_data = data;
		notifyDataSetChanged();
	}
	
	static class ViewHolder {
		TextView headlineTitle;
		TextView headlineDate;
	}
}
