package com.efub.dddstudy.Chap5_스프링데이터JPA를이용한조회기능;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OrderSummary.class)
public class OrderSummary_ {
	public static volatile SingularAttribute<OrderSummary, String> number;
}
