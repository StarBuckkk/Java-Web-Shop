package com.myspring.pro28.ex03;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
	@Autowired // mail-context.xml���� ������ ���� �ڵ����� ����
	 private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage preConfiguredMessage;
 
    @Async
	public void sendMail(String to, String subject, String body) {
      MimeMessage message = mailSender.createMimeMessage(); // MimeMessage Ÿ�� ��ü�� ����
      try {
		MimeMessageHelper messageHelper = 
		new MimeMessageHelper(message, true, "UTF-8"); // ������ ������ ���� MimeMessageHelper ��ü ����
		//messageHelper.setCc("zzzzzz@naver.com");
		messageHelper.setFrom("�۽���@naver.com", "ȫ�浿"); // ���� ���� �� ������ �̸����� ǥ�õǰ� �� / �������� ������ �۽� ���� �ּҰ� ǥ��
		messageHelper.setSubject(subject); // ����, ����ó, ������ ������ ������ ����
		messageHelper.setTo(to); 
		messageHelper.setText(body );
		mailSender.send(message);  
		
      }catch(Exception e){
		e.printStackTrace();
	  }
	}
 
	@Async
	public void sendPreConfiguredMail(String message) { // mail-context.xml���� �̸� ������ ���� �ּҷ� ���� ������ ����
	  SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
	  mailMessage.setText(message);
	  mailSender.send(mailMessage);
	}
}

