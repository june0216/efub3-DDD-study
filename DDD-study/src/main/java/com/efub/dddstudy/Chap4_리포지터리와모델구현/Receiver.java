package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Receiver {
	@Column(name = "receiver_name")
	private String name;

	@Column(name = "receiver_phone")
	private String phone;

	protected Receiver(){}

	public Receiver(String name, String phone)
	{
		this.name = name;
		this.phone = phone;
	}
}
