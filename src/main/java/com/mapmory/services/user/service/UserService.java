package com.mapmory.services.user.service;

import java.util.Map;

import com.mapmory.services.user.domain.User;

public interface UserService {
	
	/*
	 * List�� ���, Map���� ��� ����Ʈ�� �� ������ ��Ƽ� �ѱ��.
	 */
	public int addUser(User user);

	public User getUser(String userId);
	
	public int updateUserInfo(User user);
	
	public int updateProfile(User user);
	
	public int updateRecoverAccount(String userId);
	
	
	
	
	
	
	public String getId(String userName, String email);
	
	// 0: google, 1: naver, 2: kakao
	public String getSocialId(int type);
	

	
	
	public Map<String, Object> getTermsAndConditionsList();
	
	// file io ó���ϴ� ��ü�� ��� �����ұ�
	public Object getDetailTermsAndConditions();
}
