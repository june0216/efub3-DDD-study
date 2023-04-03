package com.efub.dddstudy.Chap7_도메인서비스;

public interface SurveyPermossionChecker {
	boolean hasUserCreationPermission(String userId);
}
