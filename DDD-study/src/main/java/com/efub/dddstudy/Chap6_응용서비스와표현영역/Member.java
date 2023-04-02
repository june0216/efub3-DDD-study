package com.efub.dddstudy.Chap6_응용서비스와표현영역;

public class Member {
	public void changePassword(String oldpw, String newPw){
		if(!matchPassword(oldpw)) throw new IllegalArgumentException();
		setPassword(newPw);
	}

	public boolean matchPassword(String pw){
		return passwordEncoder.matches(pwd);
	}

	public void setPassword(String newPwd){
		if(isEmpty(newPwd)) throw new IllegalArgumentException("no new password");
		this.password = newPwd;
	}
}
