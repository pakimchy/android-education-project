package org.tacademy.basic.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class CalendarManager {

	public interface CalendarComparable<T> {
		public int compareDate(int year, int month, int day);
		public int compareToUsingCalendar(T another);
	}
	
	public static class NoComparableObjectException extends Exception {

		public NoComparableObjectException(String detailMessage) {
			super(detailMessage);
			// TODO Auto-generated constructor stub
		}

		
	}
	
	private static CalendarManager instance;
	
	private Calendar mCalendar;
	private Calendar mWeekCalendar;
	
	private ArrayList mData = new ArrayList();
	
	public static CalendarManager getInstance() {
		if (instance == null) {
			instance = new CalendarManager();
		}
		return instance;
	}
	
	private CalendarManager() {
		mCalendar = Calendar.getInstance();
		mWeekCalendar = Calendar.getInstance();
	}
	
	public void setDataObject(ArrayList data) throws NoComparableObjectException {
		for (int i = 0; i < data.size(); i++) {
			Object o = data.get(i);
			if (!(o instanceof CalendarComparable)) {
				throw new NoComparableObjectException("Object not implements CalendarComparable");
			}
		}
		mData.clear();
		mData.addAll(data);
		Collections.sort(mData,new Comparator() {

			@Override
			public int compare(Object lhs, Object rhs) {
				// TODO Auto-generated method stub
				CalendarComparable clhs = (CalendarComparable)lhs;
				CalendarComparable crhs = (CalendarComparable)rhs;
				return clhs.compareToUsingCalendar(crhs);
			}			
		});
	}
	
	public CalendarData getPrevWeekCalendarData() {
		mWeekCalendar.add(Calendar.WEEK_OF_YEAR, -1);
		return getWeekCalendarData();
	}
	public CalendarData getNextWeekCalendarData() {
		mWeekCalendar.add(Calendar.WEEK_OF_YEAR, 1);
		return getWeekCalendarData();
	}
	public CalendarData getWeekCalendarData() {
		CalendarData data = new CalendarData();
		mWeekCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		data.year = mWeekCalendar.get(Calendar.YEAR);
		data.month = mWeekCalendar.get(Calendar.MONTH);
		data.weekOfMonth = mWeekCalendar.get(Calendar.WEEK_OF_MONTH);
		data.weekOfYear = mWeekCalendar.get(Calendar.WEEK_OF_YEAR);
		for (int i = 0; i < 7 ; i++) {
			CalendarItem item = new CalendarItem();
			item.year =mWeekCalendar.get(Calendar.YEAR);
			item.month = mWeekCalendar.get(Calendar.MONTH);
			item.dayOfMonth = mWeekCalendar.get(Calendar.DAY_OF_MONTH);
			item.dayOfWeek = mWeekCalendar.get(Calendar.DAY_OF_WEEK);
			item.inMonth = true;
			data.days.add(item);
			mWeekCalendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		mWeekCalendar.add(Calendar.WEEK_OF_YEAR, -1);
		for(int calendarIndex = 0,dataIndex = 0; calendarIndex < data.days.size() && dataIndex < mData.size(); ) {
			CalendarComparable cc = (CalendarComparable) mData.get(dataIndex);
			CalendarItem item = data.days.get(calendarIndex);
			int compare = cc.compareDate(item.year, item.month, item.dayOfMonth);
			if (compare < 0) {
				dataIndex++;
			} else if (compare > 0) {
				calendarIndex++;
			} else {
				item.items.add(cc);
				dataIndex++;
			}
		}		
		return data;
	}
	
	public CalendarData getCalendarData(int year, int month) {
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return getCalendarData();
	}
	
	public CalendarData getLastMonthCalendarData() {
		mCalendar.add(Calendar.MONTH, -1);
		return getCalendarData();
	}
	
	public CalendarData getNextMonthCalendarData() {
		mCalendar.add(Calendar.MONTH, 1);
		return getCalendarData();
	}
	
	public CalendarData getCalendarData() {
		CalendarData data = new CalendarData();
		int currentMonthYear, currentMonth, lastMonthYear, lastMonth, nextMonthYear, nextMonth;
		
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		int thisMonthLastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		mCalendar.add(Calendar.MONTH, -1);
		int lastMonthLastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastMonthYear = mCalendar.get(Calendar.YEAR);
		lastMonth = mCalendar.get(Calendar.MONTH);
		
		mCalendar.add(Calendar.MONTH, 2);
		nextMonthYear = mCalendar.get(Calendar.YEAR);
		nextMonth = mCalendar.get(Calendar.MONTH);
		
		mCalendar.add(Calendar.MONTH, -1);
		currentMonthYear = mCalendar.get(Calendar.YEAR);
		currentMonth = mCalendar.get(Calendar.MONTH);
		
		data.year = currentMonthYear;
		data.month = currentMonth;
		
		for (int i = Calendar.SUNDAY ; i < dayOfWeek; i++) {
			CalendarItem item = new CalendarItem();
			item.year = lastMonthYear;
			item.month = lastMonth;
			item.dayOfWeek = i;
			item.dayOfMonth = lastMonthLastDay - dayOfWeek + i + 1;
			item.inMonth = false;
			data.days.add(item);
		}
		
		for (int i = 0 ; i < thisMonthLastDay; i++) {
			CalendarItem item = new CalendarItem();
			item.year = currentMonthYear;
			item.month = currentMonth;
			item.dayOfWeek = Calendar.SUNDAY + ((i + dayOfWeek - Calendar.SUNDAY) % 7);
			item.dayOfMonth = i + 1;
			item.inMonth = true;
			data.days.add(item);
		}
		
		int startNextWeek =  Calendar.SUNDAY + ((dayOfWeek - Calendar.SUNDAY + thisMonthLastDay) % 7);
		int count = (Calendar.SATURDAY + 1 - startNextWeek) % 7;
		for (int i = 0 ; i < count ; i++) {
			CalendarItem item = new CalendarItem();
			item.year = nextMonthYear;
			item.month = nextMonth;
			item.dayOfWeek = i + startNextWeek;
			item.dayOfMonth = i + 1;
			item.inMonth = false;
			data.days.add(item);
		}
		
		
		for(int calendarIndex = 0,dataIndex = 0; calendarIndex < data.days.size() && dataIndex < mData.size(); ) {
			CalendarComparable cc = (CalendarComparable) mData.get(dataIndex);
			CalendarItem item = data.days.get(calendarIndex);
			int compare = cc.compareDate(item.year, item.month, item.dayOfMonth);
			if (compare < 0) {
				dataIndex++;
			} else if (compare > 0) {
				calendarIndex++;
			} else {
				item.items.add(cc);
				dataIndex++;
			}
		}
		
		return data;
	}
}
