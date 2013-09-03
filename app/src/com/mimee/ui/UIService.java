package com.mimee.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mimee.core.Event;

public class UIService {

	public List<Event> findMyInvitedActivities() {
		List<Event> list = new ArrayList<Event>();
		list.add(createActivity("决战夏天-HGST足球比赛", "福田体育馆", "2013-08-21 17:00", 15.2));
		list.add(createActivity("吉他兴趣交流", "莲花山公园", "2013-08-22  09:30", 20.3));
		list.add(createActivity("非常勿扰-单身男女交流会", "喜来登酒店", "2013-08-25 19:00", 4.5));
		list.add(createActivity("纠结轮哥迷见面会", "福田音乐厅", "2013-08-19 15:00", 13.2));
		return list;
	}
	
	private Event createActivity(String title, String address, String startTime, double distance) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Event activity = new Event();
		activity.setTitle(title);
		activity.setAddress(address);
		try {
			activity.setStartTime(sdf.parse(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		activity.setDistance(distance);
		return activity;
	}
}
