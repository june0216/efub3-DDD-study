package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import javax.persistence.*;

@Embeddable
public class Orderer {
	@Embedded
	@AttributeOverrides( // MemberId에 정의된 칼럼 이름을 변경하기 위해 사용
			@AttributeOverride(name= "id", column = @Column(name = "order_id"))
	)
	private MemberId memberId;

	@Column(name = "order_name")
	private String name;
}
