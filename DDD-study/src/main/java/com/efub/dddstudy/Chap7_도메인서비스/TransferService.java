package com.efub.dddstudy.Chap7_도메인서비스;

import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;

public class TransferService {
	public void transfer(Account fromAcc, Account toAcc, Money amounts){
		fromAcc.withdraw(amounts);
		toAcc.credit(amounts);
	}
}
