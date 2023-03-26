package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class OrderSummaryRepository extends JpaRepository<OrderSummary, Integer> {
	List<OrderSummary> findAllByOrderByNumberDesc(String ordererId);//OrderBy로 정렬

	List<OrderSummary> findAllOrdererId(String ordererId, Sort sort);//Sort를 인자로 전
	//Sort sort = Sort.by("number").ascending();
}
