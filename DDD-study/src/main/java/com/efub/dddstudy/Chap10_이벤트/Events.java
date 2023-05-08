package com.efub.dddstudy.Chap10_이벤트;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
	private static ApplicationEventPublisher publisher;

	static void setPublisher(ApplicationEventPublisher publisher) { // 객체를 받음
		Events.publisher = publisher;
	}

	public static void raise(Object event) { //Event의 raise() 메서드는 ApplicationEventPublisher가 제공하는 publishEvent()을 이용해서 발생시킨다.
		if (publisher != null) {
			publisher.publishEvent(event);
		}
	}
}
