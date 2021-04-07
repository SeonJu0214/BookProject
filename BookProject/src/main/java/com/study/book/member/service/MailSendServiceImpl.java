package com.study.book.member.service;

import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSendServiceImpl implements MailSendService{
    @Autowired
    private JavaMailSenderImpl mailSender;

    private int size;
    
    // 인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    // 인증 코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    // 인증 메일 보내기
    @Override
    public String sendAuthMail( String email, String mem_id) {
        // 6자리 난수 인증 번호 생성
        String authKey = getKey(6);

        // 인증 메일 보내기
        MimeMessage mail = mailSender.createMimeMessage();
        String mailContent = "<h1>[이메일 인증]</h1><br>"+mem_id +"<p>님 반갑습니다.아래 링크를 클릭해서 이메일 인증을 완료 해주세요</p>";
        mailContent += "<a href='http://192.168.0.90:8080/mem/signUpConfirm?mem_id=";
        mailContent += mem_id + "&authKey=" + authKey + "' target='_blenk'>이메일 인증 확인(여기를 눌러주세요)</a>";

        try {
            mail.setSubject("회원 가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail); // 메일 보내기
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return authKey;
    }
}