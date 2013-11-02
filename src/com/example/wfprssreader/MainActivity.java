package com.example.wfprssreader;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements FeedFragment.OnFeedSelectedListener{
	public static Feed feed = new Feed("http://www.winnipegfreepress.com/rss/?path=%2Fworld");
	Feed feed_sports = null, feed_local = null, feed_world = null;
	public static FragmentActivity activity;
	private FeedFragment feed_fragment;
	int REQUEST_CODE_STANDARD = 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feeds_view);
		
		SpinnerAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.feed_list, android.R.layout.simple_spinner_dropdown_item);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		
		OnNavigationListener navigation_listener = new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				
				switch (itemPosition) {
				case 0:
					feed_world = (feed_world == null) ? new Feed("http://www.winnipegfreepress.com/rss/?path=%2Fworld") : feed_world; 
					feed = feed_world;
					break;
				case 1:
					feed_local = (feed_local == null) ? new Feed("http://www.winnipegfreepress.com/rss/?path=%2Flocal") : feed_local;
					feed = feed_local;
					break;
				case 2:
					feed_sports = (feed_sports == null) ?  new Feed("http://www.winnipegfreepress.com/rss/?path=%2Fsports%2Fhockey%2Fjets") : feed_sports;
					feed = feed_sports;
					break;
				}
				
				feed.refreshFeed();
				return true;
			}
		};
		
		actionBar.setListNavigationCallbacks(spinner_adapter, navigation_listener);
		
		activity = this;
		
		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
			
			feed_fragment = new FeedFragment();
			feed_fragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, feed_fragment).commit();
		}
	}

	@Override
	public void onArticleSelected(int position) {
		ArticleFragment article_fragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
		
		if (article_fragment != null) {
			article_fragment.updateArticleView(position);
		} else {
			ArticleFragment new_article_fragment = new ArticleFragment();
			Bundle args = new Bundle();
			args.putInt(ArticleFragment.ARG_POSITION, position);
			new_article_fragment.setArguments(args);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fragment_container, new_article_fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			
			break;
		case R.id.action_refresh:
			feed.refreshFeed();
			Toast.makeText(this, "Feed Refreshed", Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
