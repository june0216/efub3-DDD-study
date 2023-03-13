package com.efub.dddstudy.Chap2_아키텍처개요;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Order;

public class CancelOrderService {
	private OrderRepository orderRepository;

	@Transactional
	public cancelOrder(String orderId){
		Order order = orderRepository.findOrderById(orderId);
		if(order == null) throw new OrderNotFoundException(orderId);
		order.cancel();//도메인 모델에 로직 수행을 위임한다.
	}
}
