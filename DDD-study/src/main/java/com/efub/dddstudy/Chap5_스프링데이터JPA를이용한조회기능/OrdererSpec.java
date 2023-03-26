package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import javax.persistence.criteria.Order;

public class OrdererSpec implements Specification1<Order> {//Order 애그리거트 객체가 특정 고객의 주문인지 확인하는 스펙
	private String ordererId;

	public OrdererSpec(String ordererId){
		this.ordererId = ordererId;
	}
	public boolean isStatisfiedBy(Order agg){//검사 대상 객체가 조건을 충족하면 true를 리턴하고 그렇지 않으면 false를 리턴합니다.
		return agg.getOrdererId().getMemberId().getId().equals(ordererId);
	}

}
