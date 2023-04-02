package com.efub.dddstudy.Chap7_도메인서비스;

import org.springframework.transaction.annotation.Transactional;

public class OrderService {
	private DiscountCalcalculationService discountCalcalculationService;

	@Transactional
	public OrderNo placeOrder(OrderRequest orderRequest){
		OrderNo orderNo = orderRepository.nextId();
		Order order = createOrder(orderNo, orderRequest);
		orderRepository.save(order);
		return orderNo; // 응용 서비스에서 실행 후 표현 영역에서 필요한 값 리턴
	}

	private Order createOrder(OrderNo orderNo, OrderRequest orderReq){
		Member member = findMember(orderReq.getOrdererId());
		Order order = new Order(orderNo, orderReq.getOrderLines(),
				orderReq.getCoupons(), createOrder(member),
				orderReq.getShippingInfo());
		orer.calculationAmounts(this.discountCalcalculationService, member.getGrade());
		return order;
	}
}
