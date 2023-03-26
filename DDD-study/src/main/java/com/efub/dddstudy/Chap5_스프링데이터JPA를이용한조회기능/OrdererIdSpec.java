package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Predicate;

public class OrdererIdSpec implements Specification<OrderSummary>{//OrderSummary에 대한 검색 조건
	private String ordererId;

	public OrdererIdSpec(String ordererId){
		this.ordererId = ordererId;
	}
	@Override
	Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb){
		return cb.equal(root.get(OrderSummary_.orderId), ordererId);//생성자로 전달 받은 orererId와 같은지 비교

	}
}
