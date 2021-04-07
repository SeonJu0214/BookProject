package com.study.book.member.service;

import java.util.Map;

import org.springframework.validation.Errors;

import com.study.book.member.model.MemberDTO;

public interface MemberService {
	// 유효성 체크 ( validation )
	public Map<String, String> validateHandling(Errors errors);
	
	// 회원 정보 입력
	public Map<String, Object> memberInsert(MemberDTO memberDTO) throws Exception;
	
	// email_auth 인증 완료 처리
	public void emailauthUpdate(String mem_id);
}