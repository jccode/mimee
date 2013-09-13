package com.mimee.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mimee.R;
import com.mimee.core.Event;
import com.mimee.provider.UIProvider;
import com.mimee.util.ImageCache.ImageCacheParams;
import com.mimee.util.ImageFetcher;
import com.mimee.util.RecyclingImageView;

public class ActivityListFragment extends ListFragment {
	
	private static final String IMAGE_CACHE_DIR = "thumbs";
	private UIProvider uiService = new UIProvider();
	private ActivityRowAdapter mAdapter;
	
	private int mImageThumbSize;
	private ImageFetcher mImageFetcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.event_img_width);
		
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(.25f);
		
		mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		List<Event> events = getEvents();
		mAdapter = new ActivityRowAdapter(getActivity(), events);
		setListAdapter(mAdapter);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
					mImageFetcher.setPauseWork(true);
				} else {
					mImageFetcher.setPauseWork(false);
				}
			}
			
		});
	}

	private List<Event> getEvents() {
		return uiService.findMyInvitedActivities();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setPauseWork(false);
		mImageFetcher.setExitTasksEarly(true);
		mImageFetcher.flushCache();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
	}
	
	private class ActivityRowAdapter extends BaseAdapter {
		
		private Context mContext;
		private List<Event> mEvents;
		private LayoutInflater inflater = null;
		private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		public ActivityRowAdapter(Context context, List<Event> events) {
			mContext = context;
			mEvents = events;
			inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			return mEvents.size();
		}

		@Override
		public Object getItem(int postion) {
			return mEvents.get(postion);
		}

		@Override
		public long getItemId(int postion) {
			return postion;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if(view == null) 
				view = inflater.inflate(R.layout.events_list_item, null);
			
			RecyclingImageView imageView = (RecyclingImageView) view.findViewById(R.id.recyclingImageView1);
			TextView topic = (TextView) view.findViewById(R.id.event_topic);
			TextView distance = (TextView) view.findViewById(R.id.event_distance);
			TextView eventTime = (TextView) view.findViewById(R.id.event_time);
			TextView address = (TextView) view.findViewById(R.id.address);
			
			Event ev = mEvents.get(position);
			topic.setText(ev.getTitle());
			distance.setText(ev.getDistance()+" km");
			eventTime.setText(sdf.format(ev.getStartTime()));
			address.setText(ev.getAddress());
			
			Log.w("mimee", ev.getPhoto());
			mImageFetcher.loadImage(ev.getPhoto(), imageView);
			
			return view;
		}
		
	}
}


