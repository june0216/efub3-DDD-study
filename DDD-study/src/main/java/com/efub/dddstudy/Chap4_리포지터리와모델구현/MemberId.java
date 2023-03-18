package com.efub.dddstudy.Chap4_리포지터리와모델구현;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class MemberId implements Serializable {
	@Column
	private String id;
}
