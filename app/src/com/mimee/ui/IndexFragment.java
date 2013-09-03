package com.mimee.ui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mimee.R;


public class IndexFragment extends Fragment {
	
	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View rootView = inflater.inflate(R.layout.fragment_index, container, false);
		mViewPager = (ViewPager) rootView.findViewById(R.id.index_pager);
		
		ActionBarActivity activity = (ActionBarActivity) getActivity();
		final ActionBar bar = activity.getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		
		mTabsAdapter = new TabsAdapter(activity, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText("My Invited"), ActivityListFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Top Heat"), StubFragment.class, createBundle("Top Heat"));
		mTabsAdapter.addTab(bar.newTab().setText("Look Around"), StubFragment.class, createBundle("Look Around"));
		mTabsAdapter.addTab(bar.newTab().setText("City"), StubFragment.class, createBundle("City"));
		
		if(savedInstanceState != null) {
			bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		}
		
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		final ActionBar bar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		outState.putInt("tab", bar.getSelectedNavigationIndex());
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.action_bar_index, menu);
	}

	private Bundle createBundle(String name) {
		Bundle args = new Bundle();
		args.putString("name", name);
		return args;
	}

	public static class TabsAdapter extends FragmentPagerAdapter
		implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
		
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		
		static final class TabInfo {
			private final Class<?> clazz;
			private final Bundle args;
			
			public TabInfo(Class<?> clazz, Bundle args) {
				this.clazz = clazz;
				this.args = args;
			}
		}

		public TabsAdapter(ActionBarActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}
		
		public void addTab(ActionBar.Tab tab, Class<?> clazz, Bundle args) {
			TabInfo info = new TabInfo(clazz, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mTabs.size();
		}
		
		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clazz.getName(), info.args);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for(int i = 0, len = mTabs.size(); i < len; i++) {
				if(mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
