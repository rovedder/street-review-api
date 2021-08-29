package br.com.street.reviews.service;

import javax.mail.MessagingException;

import br.com.street.reviews.service.helper.Mail;

public interface SendEmailService {
	
	void sendMail(Mail mail) throws MessagingException;
	
}
