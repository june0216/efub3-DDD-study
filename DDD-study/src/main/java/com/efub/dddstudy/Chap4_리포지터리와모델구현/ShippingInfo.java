package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Receiver;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
@Embeddable
public class ShippingInfo {
	@Embedded
	@AttributeOverride({//Address와 다른 칼럼이름을 갖기 위해
			@AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zipcode")),
			@AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1")),
			@AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2"))
	})
	private Address address;

	@Column(name = "shipping_message")
	private String message;

	@Embedded
	private Receiver receiver;
}
