package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaOrderRepository implements OrderRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Order findById(OrderNo id)//애그리거트 조회
	{
		return entityManager.find(Order.class, id);
	}

	@Override
	public void save(Order order)
	{
		entityManager.persist(order);//애그리거트 저장
	}

}
