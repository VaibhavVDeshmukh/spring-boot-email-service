package com.stm.app.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;

public interface MailService {
	  
  void sendSimpleEmail(String[] to, String subject, String text);

  void sendHtmlEmail(String[] to, String subject, String htmlContent);
  
  void sendEmailWithAttachment(String[] to, String subject, String text, String pathToAttachment);
  
  void sendEmailWithInlineImage(String[] to, String subject, String htmlContent, String pathToImage, String imageId);

  void scheduleEmail(String[] to, String subject, String text, LocalDateTime scheduleTime);

  void sendEmailWithAttachments(String to, String subject, String text, List<MultipartFile> attachments);

  void sendEmailWithTemplate(String[] to, String subject, String templateName, Context context);


}
