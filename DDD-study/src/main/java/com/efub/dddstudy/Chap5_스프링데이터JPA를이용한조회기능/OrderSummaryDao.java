package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OrderSummaryDao extends Repository<OrderSummary, String> {
	List<OrderSummary> findByOrdererId(String ordererId, Sort sort);
	List<OrderSummary> findAll(Specification<OrderSummary> spec, Sort sort);

	@Query(// 완전한 클래스 이름을 지정 , 괄호 안에 생성자에 인자로 전달할 값을 지정한다.
			"""
select new com.myshop.order.query.dto.OrderView(  
     o.number, o.state, m.name, m,id, p.name)
     from Order o join o.orderLines ol, Member m, Product fetch all properties 
     where o.orderer.memberId.id = :ordererId
     and o.orderer.memberId.id = m.id
     and index(ol)= 0
     and ol.productId.id = p.id
     order by o.number.number desc
"""
	)
	List<OrderView> findOrderView(String ordererId);

}
