package com.rabqueues.receiver;


import com.rabqueues.DTOs.EmailPayload;

import tools.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailReceiverConsumer {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper; // El mapeador de Spring Boot

    @RabbitListener(queues = "emailQueue")
    public void processAndSendEmail(String rawPayload) {
        try {
            // Convierte el string plano enviado por la API REST de RabbitMQ al DTO de Java
            EmailPayload payload = objectMapper.readValue(rawPayload, EmailPayload.class);

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(payload.getTo()); 
            mail.setSubject(payload.getSubject());
            mail.setText(payload.getBody());
            
            mailSender.send(mail);
            System.out.println("Email enviado exitosamente a: " + payload.getTo());
            
        } catch (Exception e) {
            System.err.println("Error al procesar el JSON del correo: " + e.getMessage());
        }
    }
    @RabbitListener(queues = "emailDlq")
    public void processEmailDlq(EmailPayload payload) {
        System.out.println("[EMAIL DLQ] Email fallido: " + payload.getTo());
    }

}
