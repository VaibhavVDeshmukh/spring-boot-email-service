package com.stm.app.dto;

public class TemplateEmailRequest {
	
	private String[] to;
    private String subject;
    private String name;
    private String ctaUrl;
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCtaUrl() {
		return ctaUrl;
	}
	public void setCtaUrl(String ctaUrl) {
		this.ctaUrl = ctaUrl;
	}
    
}
