package com.study.book.member.dao;

import com.study.book.member.model.MemberDTO;

public interface MemberDAO {
	// 회원 정보 입력
	public void memberInsert(MemberDTO memberDTO);
	
	// email_auth 인증 완료 처리
	public void emailauthUpdate(String mem_id);
}