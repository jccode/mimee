package com.mimee.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.mimee.core.Event;

public class UIProvider {

	public List<Event> findMyInvitedActivities() {
		return createRandomActivities(10);
	}
	
	List<Event> createActivities(int size) {
		List<Event> list = new ArrayList<Event>();
		if(size <= 0) return list;
		if(size > 10) size = 10;
		for(int i = 0; i < size; i++) {
			list.add(newActivity(SampleData.event[i], SampleData.address[i], 
					SampleData.datetime[i], SampleData.distance[i], Images.imageThumbUrls[i]));
		}
		return list;
	}
	
	List<Event> createRandomActivities(int size) {
		List<Event> list = new ArrayList<Event>();
		if(size <= 0) return list;
		int len = SampleData.event.length;
		Random rand = new Random();
		for(int i = 0; i < size; i++) {
			list.add(newActivity(
					SampleData.event[rand.nextInt(len)], 
					SampleData.address[rand.nextInt(len)], 
					SampleData.datetime[rand.nextInt(len)], 
					SampleData.distance[rand.nextInt(len)], 
					Images.imageThumbUrls[rand.nextInt(len)]
					));
		}
		return list;
	}
	
	private Event newActivity(String title, String address, String startTime, double distance, String imageUrl) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
		Event activity = new Event();
		activity.setTitle(title);
		activity.setAddress(address);
		try {
			activity.setStartTime(sdf.parse(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		activity.setDistance(distance);
		activity.setPhoto(imageUrl);
		return activity;
	}
}
