package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

public class MemoryOrderRepository implements OrderRepository{
	public List<Order> findAll(Specification1<Order> spec){
		List<Order> allOrders =findAll();
		return allOrders.stream().filter(order -> spec.isSatisfiedBy(order)).toList();
	}
}
