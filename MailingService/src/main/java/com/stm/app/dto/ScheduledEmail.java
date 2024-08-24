package com.stm.app.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduledEmail {

    private final String id;
    private final String[] to;
    private final String subject;
    private final String text;
    private final LocalDateTime scheduleTime;

    public ScheduledEmail(String[] to, String subject, String text, LocalDateTime scheduleTime) {
        this.id = UUID.randomUUID().toString();
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.scheduleTime = scheduleTime;
    }

    public String getId() {
        return id;
    }

    public String[] getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }
}
