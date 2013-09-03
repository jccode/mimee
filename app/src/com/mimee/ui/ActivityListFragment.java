package com.mimee.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mimee.core.Event;

public class ActivityListFragment extends ListFragment {
	
	private UIService uiService = new UIService();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<String> titles = getTitles();
		setListAdapter(new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, 
				titles.toArray(new String[titles.size()])));
	}

	private List<String> getTitles() {
		List<Event> list = uiService.findMyInvitedActivities();
		List<String> titles = new ArrayList<String>();
		for(Event event : list) {
			titles.add(event.getTitle());
		}
		return titles;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
	}
}
