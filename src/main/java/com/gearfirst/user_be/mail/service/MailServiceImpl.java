package com.gearfirst.user_be.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendUserRegistrationMail(String toEmail, String name, String tempPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("[GearFirst] 신규 계정 안내");
            helper.setText(buildMailContent(name, tempPassword), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패: " + e.getMessage());
        }
    }
    private String buildMailContent(String name, String tempPassword) {
        return """
                <div style="font-family:Arial,sans-serif; line-height:1.6;">
                    <h2>안녕하세요, %s 님</h2>
                    <p>GearFirst 시스템에 새로운 계정이 생성되었습니다.</p>
                    <hr/>
                    <p><b>임시 비밀번호:</b> %s</p>
                    <hr/>
                    <p>처음 로그인 후 반드시 비밀번호를 변경해주세요.</p>
                    <p>감사합니다.<br/>GearFirst 운영팀 드림</p>
                </div>
                """.formatted(name, tempPassword);
    }
}
