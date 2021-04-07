package com.study.book.member.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.study.book.member.dao.MemberDAO;
import com.study.book.member.model.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberDAO memberDAO;
	
	@Autowired
	private MailSendService mailSendService;
	
	// 암호화 필드 선언 ( DI )
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	// 유효성 체크 ( validation )
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<String, String>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

	// 회원 정보 입력
	@Override
	public Map<String, Object> memberInsert(MemberDTO memberDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 비밀번호 암호화
		memberDTO.setMem_pw(bcryptPasswordEncoder.encode(memberDTO.getMem_pw()));
		
		memberDAO.memberInsert(memberDTO);
		//이메일 전송
		String authKey = mailSendService.sendAuthMail(memberDTO.getMem_email(),memberDTO.getMem_id());
				
		resultMap.put("authKey", authKey); // 이메일 인증키
		resultMap.put("msg", "회원 가입 완료");
		resultMap.put("result", 0); // 가입 성공
		return resultMap;
	}

	// email_auth 인증 완료 처리
	@Override
	public void emailauthUpdate(String mem_id) {
		memberDAO.emailauthUpdate(mem_id);
	}
}