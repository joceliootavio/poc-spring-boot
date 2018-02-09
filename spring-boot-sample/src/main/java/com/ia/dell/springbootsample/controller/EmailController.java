package com.ia.dell.springbootsample.controller;

import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ia.dell.springbootsample.config.RabbitMQConfig;
import com.ia.dell.springbootsample.controller.dto.Email;
import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.service.UserService;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
	
	private static final Logger LOGGER = Logger.getLogger(EmailController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;	

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> send(@RequestBody Email input) {
		LOGGER.debug("recebi os dados para envio de email: " + input);
		if (input.getRecipients() == null || input.getRecipients().isEmpty())
			input.setRecipients(
					userService.findAdmins()
						.stream()
						.map(User::getEmail)
						.collect(Collectors.toList()));
		
		rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, input);
		return ResponseEntity.ok().build();
	}
}
