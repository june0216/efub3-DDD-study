package com.efub.dddstudy.Chap10_이벤트;

import com.efub.dddstudy.Chap4_리포지터리와모델구현.ShippingInfo;
import org.springframework.context.event.EventListener;

public class ShippingInfoChangedHandler {

	@EventListener(ShippingInfoChangedHandler.class)
	public void handle(ShippingInfoChangedEvent evt){//같은 VM에서 동작하고 있다면
		Order order = orderRepository.findById(evt.getOrderNo());//이벤트가 필요한 정보를 담고 있지 않으면 DB로 조회
		shippingInfoSynchronizer.sync(
				evt.getOrderNumber(),
				evt.getNewShippingInfo()
		);
	}
}
