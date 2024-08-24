package com.stm.app.controller;

import com.stm.app.dto.MailRequest;
import com.stm.app.dto.TemplateEmailRequest;
import com.stm.app.services.MailService;

import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send-simple")
    public String sendSimpleMail(@RequestParam String[] to, @RequestParam String subject, @RequestParam String text) {
        mailService.sendSimpleEmail(to, subject, text);
        return "Simple email sent successfully";
    }

    @PostMapping("/send-html")
    public String sendHtmlMail(@RequestBody MailRequest mailRequest) {
        mailService.sendHtmlEmail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getHtmlContent());
        return "HTML email sent successfully";
    }

    @PostMapping("/send-attachment")
    public String sendEmailWithAttachment(@RequestParam String[] to, @RequestParam String subject, @RequestParam String text, @RequestParam String pathToAttachment) {
        mailService.sendEmailWithAttachment(to, subject, text, pathToAttachment);
        return "Email with attachment sent successfully";
    }
    
    @PostMapping("/send-email-with-attachments")
    public ResponseEntity<String> sendEmailWithAttachments(
        @RequestParam String to,
        @RequestParam String subject,
        @RequestParam String text,
        @RequestParam List<MultipartFile> attachments
    ) {
        mailService.sendEmailWithAttachments(to, subject, text, attachments);
        return ResponseEntity.ok("Email sent with attachments");
    }

    @PostMapping("/send-inline-image")
    public String sendEmailWithInlineImage(@RequestParam String[] to, @RequestParam String subject, @RequestParam String htmlContent, @RequestParam String pathToImage, @RequestParam String imageId) {
        mailService.sendEmailWithInlineImage(to, subject, htmlContent, pathToImage, imageId);
        return "Email with inline image sent successfully";
    }
    
    @PostMapping("/schedule-email")
    public ResponseEntity<String> scheduleEmail(
        @RequestParam String[] to,
        @RequestParam String subject,
        @RequestParam String text,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduleTime
    ) {
        mailService.scheduleEmail(to, subject, text, scheduleTime);
        return ResponseEntity.ok("Email scheduled");
    }
    
    @PostMapping("/send-template-mail")
    public ResponseEntity<String> sendEmailWithTemplate(@RequestBody TemplateEmailRequest request) {
        Map<String, Object> model = new HashMap<>();
		model.put("name", request.getName());
		model.put("ctaUrl", request.getCtaUrl());
		Context context = new Context();
		context.setVariables(model);
		mailService.sendEmailWithTemplate(request.getTo(), request.getSubject(), "welcome-email", context);
		return ResponseEntity.ok("Email sent successfully");
    }
}
