package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Table(name = "purchate_order")
@Access(AccessType.FIELD)
public class Order {
	@Embedded
	private OrderNo number;

	@Embedded
	private Orderer orderer;

	@Embedded
	private ShippingInfo shippingInfo;
}
