package com.ia.dell.springbootsample.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String subject;
	private String body;	
	private List<String> recipients;	
} 