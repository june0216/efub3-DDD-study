package com.efub.dddstudy.Chap2_아키텍처개요;

public interface OrderRepository {
	Order findByNumber(OrderNumber number);
	void save(Order order);
	void delete(Order order);
}
