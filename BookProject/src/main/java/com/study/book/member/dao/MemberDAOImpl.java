package com.study.book.member.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.book.member.model.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.study.book.member.memberMapper";

	// 회원 정보 입력
	@Override
	public void memberInsert(MemberDTO memberDTO) {
		sqlSession.insert(NAMESPACE + ".memberInsert", memberDTO);
	}

	// email_auth 인증 완료 처리
	@Override
	public void emailauthUpdate(String mem_id) {
		sqlSession.update(NAMESPACE + ".emailauthUpdate", mem_id);
	}
}