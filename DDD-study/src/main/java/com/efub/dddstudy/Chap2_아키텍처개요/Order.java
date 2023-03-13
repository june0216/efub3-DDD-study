package com.efub.dddstudy.Chap2_아키텍처개요;

import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderLine;
import com.efub.dddstudy.Chap1_도메인모델시작하기.OrderNo;
import com.efub.dddstudy.Chap1_도메인모델시작하기.Orderer;
import com.efub.dddstudy.Chap1_도메인모델시작하기.ShippingInfo;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity// 구현의 편리함을 위해 인프라스트럭처에 대한 의존을 일부 도메인에 넣은 코드
@Table(name = "TBL_ORDER")
public class Order {
	//주문 도메인 모델의 데이터
	private OrderNo id;// 식별자
	private Orderer orderer;
	private ShippingInfo shippingInfo;

	//도메인 모델 엔티티는 도메인 기능도 함께 제공
	public void changeShippingInfo(ShippingInfo newShippingInfo){
		checkShippingInfoChangeable();//루트 에그리거트가 관리한다. -> 배송지 변경 가능 여부 확인
		setShippingInfo(newShippingInfo); // 새로운 객체 할당
	}
	private void checkShippingInfoChangeable()
	{}// 배송지 정보를 변경할 수 있는지 여부를 확인하는 도메인 규칙


	private void setShippingInfo(ShippingInfo newShippingInfo){
		if(newShippingInfo == null) throw new IllegalArgumentException();
		this.shippingInfo = newShippingInfo;
	}


}
