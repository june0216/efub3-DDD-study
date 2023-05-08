package com.efub.dddstudy.Chap10_이벤트;

public abstract class Event {

	private long timestamp;

	public Event() {
		this.timestamp = System.currentTimeMillis();
	}

	public long getTimestamp() {
		return timestamp;
	}

}
