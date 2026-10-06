package com.rabqueues.controller;


import com.rabqueues.DTOs.EmailPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
@CrossOrigin(origins = "*") // Evita problemas de CORS con tu frontend de React
public class EmailProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<String> enqueueEmail(@RequestBody EmailPayload payload) {
        // Publica el objeto mapeado directamente al exchange de RabbitMQ
        rabbitTemplate.convertAndSend("emailExchange", "emailRoutingKey", payload);
        
        return ResponseEntity.ok("Email encolado exitosamente en RabbitMQ");
    }
}
