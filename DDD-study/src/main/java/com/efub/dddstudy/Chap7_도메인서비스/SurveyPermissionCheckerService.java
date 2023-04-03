package com.efub.dddstudy.Chap7_도메인서비스;

import javax.naming.NoPermissionException;

public class SurveyPermissionCheckerService {
	private SurveyPermossionChecker permossionChecker;

	public Long createSurvey(CreateSurveyRequest req){ //도메인 서비스를 이용해서 권한을 검사합니다.
		validate(req);
		if(!permossionChecker.hasUserCreationPermission(req.getRequestorId())){
			throw new NoPermissionException();
		}
	}
}
