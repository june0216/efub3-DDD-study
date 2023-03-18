package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderNo;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface OrderRepository extends Repository<Order, OrderNo> {
	Optional<Order> findById(OrderNo id);
	public void save(Order order);
}
