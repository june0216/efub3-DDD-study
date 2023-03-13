package com.efub.dddstudy.Chap1_도메인모델시작하기;

public class Receiver {
	private String name;
	private String phoneNumber;

	public Receiver(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public Receiver(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
