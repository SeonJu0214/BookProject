package com.study.book.member.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.book.member.model.MemberDTO;
import com.study.book.member.service.MemberService;

@Controller
public class MemberController {
	@Inject
	MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 회원 가입 > 약관 동의 페이지로 이동
	@GetMapping(value = "/join/agreement")
	public String agreement() {
		return "join/agreement";
	}
	
	// 회원 가입 > 정보 입력 페이지로 이동
	@GetMapping(value = "/join/memberInsert")
	public String memberInsert(MemberDTO memberDTO) {
		return "join/memberInsert";
	}
	
	// 회원 가입 (회원 정보 입력)
	@PostMapping("/join/memberInsert")
	public String memberInsert(@ModelAttribute("memberDTO") @Valid MemberDTO memberDTO, Errors errors, Model model,
			RedirectAttributes rattr, HttpSession session) throws Exception {
		// 유효성 체크 ( validation )
		if(errors.hasErrors()) {
			logger.info("회원 가입 실패 (유효성 검사 :: 에러 발생)");
			// 회원가입 실패시, 입력 데이터를 유지
			model.addAttribute("memberDTO", memberDTO);
			
			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
			return "join/memberInsert";
		}
		
		Map<String, Object> resultMap = memberService.memberInsert(memberDTO);
		
		// 인증키를 세션에 넣기
		// key : mem_id, value : authKey
		session.setAttribute(memberDTO.getMem_id(), resultMap.get("authKey"));
		session.setMaxInactiveInterval(30*60); // 30분
		
		// 가입 실패 시(-1) join으로 이동, 성공시(0) index로 이동
		if ((int)resultMap.get("result")==0){
			// redirect 메시지 보내기
			rattr.addFlashAttribute("msg",resultMap.get("msg"));
			return "redirect:/index";
		}else {
			model.addAttribute("msg",resultMap.get("msg"));
			return "join/memberInsert";
		}
	}
	
	//이메일에서 인증 클릭했을때
	@RequestMapping("signUpConfirm")
	public String signUpConfirm(@RequestParam Map<String, String> map, 
			HttpSession session, RedirectAttributes rattr) throws Exception{
	    // mem_id, authKey가 일치할 경우 email_auth 업데이트
		logger.info(map.toString());
		
		String sessionAuthKey = (String) session.getAttribute(map.get("mem_id"));
		logger.info(sessionAuthKey); // 세션에 저장된 authKey
		
		if (sessionAuthKey==null) { // 인증 세션이 종료되면 key사라짐
			logger.info("세션 미존재");
			return "join/memberInsert";
		}
		
		if (sessionAuthKey.equals(map.get("authKey"))) {
			logger.info("메일 인증 성공");
			// member => email_auth 수정
			memberService.emailauthUpdate(map.get("mem_id"));
			rattr.addFlashAttribute("msg", "인증이 완료 되었습니다.");
		} else {
			rattr.addFlashAttribute("msg", "인증키가 일치하지 않습니다.");
		}
		
		// redirect는 주소 변경
		// URL 매핑 정보
		return "redirect:/index";
	}
}