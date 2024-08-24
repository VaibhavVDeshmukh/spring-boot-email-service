package com.stm.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Component;

import com.stm.app.dto.ScheduledEmail;
import com.stm.app.services.MailService;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@EnableScheduling
public class EmailScheduler {
	
	 @Autowired
	private MailService mailService;

    private final static ConcurrentMap<String, ScheduledEmail> scheduledEmails = new ConcurrentHashMap<>();

    public static void scheduleEmail(String[] to, String subject, String text, LocalDateTime scheduleTime) {
        ScheduledEmail email = new ScheduledEmail(to, subject, text, scheduleTime);
        try {
			scheduledEmails.put(email.getId(), email);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Scheduled(fixedDelay = 60000) // Check every minute
    public void sendScheduledEmails() {
        LocalDateTime now = LocalDateTime.now();
        scheduledEmails.values().removeIf(email -> {
            if (email.getScheduleTime().isBefore(now)) {
            	mailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getText());
                return true;
            }
            return false;
        });
    }
}
