package com.stm.app.services;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.stm.app.utils.EmailScheduler;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService  {
	
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;

    
    @Value("${mail.from.address}")
    private String fromAddress; // Injected from application.properties
	
	@Override
	public void sendSimpleEmail(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);	
	}
	
	@Override
	public void sendHtmlEmail(String[] to, String subject, String htmlContent) {
		MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
		
	}
	
	@Override
	public void sendEmailWithAttachment(String[] to, String subject, String text, String pathToAttachment) {
		 MimeMessage message = mailSender.createMimeMessage();

	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(fromAddress);
	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(text);

	            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
	            helper.addAttachment(file.getFilename(), file);

	            mailSender.send(message);
	        } catch (MessagingException e) {
	            throw new RuntimeException("Failed to send email with attachment", e);
	        }
		
	}

	@Override
	public void sendEmailWithInlineImage(String[] to, String subject, String htmlContent, String pathToImage,
			String imageId) {
		MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            FileSystemResource res = new FileSystemResource(new File(pathToImage));
            helper.addInline(imageId, res);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with inline image", e);
        }
		
	}

	@Override
	public void scheduleEmail(String[] to, String subject, String text, LocalDateTime scheduleTime) {
		EmailScheduler.scheduleEmail(to, subject, text, scheduleTime);		
	}

	@Override
    public void sendEmailWithAttachments(String to, String subject, String text, List<MultipartFile> attachments) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            
            messageHelper.setFrom(fromAddress);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true); // true indicates HTML content

            // Add attachments
            for (MultipartFile file : attachments) {
                messageHelper.addAttachment(file.getOriginalFilename(), file);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }
	
	@Override
	public void sendEmailWithTemplate(String[] to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(mimeMessage, true);
		

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        String htmlContent = templateEngine.process(templateName, context);
        messageHelper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
		} catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
		}
    }
}
