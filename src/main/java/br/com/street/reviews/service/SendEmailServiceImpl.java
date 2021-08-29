package br.com.street.reviews.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.street.reviews.Application;
import br.com.street.reviews.service.helper.Mail;

@Service
public class SendEmailServiceImpl implements SendEmailService{
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	private final JavaMailSender javaMailSender;

    public SendEmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
	@Override
	public void sendMail(Mail mail) {
		try { 
			MimeMessage msg = javaMailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	        helper.setTo(mail.getEmail());

	        helper.setSubject(mail.getSubject());

	        helper.setText(mail.getMessage(), true);

	        helper.addAttachment("review-1.pdf", new ClassPathResource("review-1.pdf"));

	        javaMailSender.send(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

}
