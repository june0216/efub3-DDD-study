package com.efub.dddstudy.Chap3_애그리거트;


import com.efub.dddstudy.Chap1_도메인모델시작하기.Money;
import com.efub.dddstudy.Chap2_아키텍처개요.OrderLine;

import java.util.List;

public class OrderLines {
	private List<OrderLine> lines;

	public Money getTotalAmounts(){}
	public void changeOrderLines(List<OrderLine> newLines)
	{
		this.lines = lines;
	}

}
