package com.ia.dell.springbootsample;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ia.dell.springbootsample.listener.EmailListener;

@SpringBootApplication
public class RabbitMQApplication {
    public static final String EMAIL_QUEUE = "emailQueue";

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(EMAIL_QUEUE);
        container.setMessageListener(listenerAdapter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(EmailListener listener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "receiveMessage");
		adapter.setMessageConverter(jsonMessageConverter());
		return adapter;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


	public static void main(String[] args) {
		SpringApplication.run(RabbitMQApplication.class, args);
	}
}
