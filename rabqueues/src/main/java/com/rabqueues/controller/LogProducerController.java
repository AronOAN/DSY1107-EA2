package com.rabqueues.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rabqueues.DTOs.LogMessageDto;
import com.rabqueues.config.RabbitQueuesConfig;



@RestController
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde vite
public class LogProducerController {
 
 
    @Autowired
 private RabbitTemplate rabbitTemplate;
 
 
 @PostMapping("/log")
 public String sendLog(@RequestBody LogMessageDto logMessage) {
 // La routing key es el nivel del log (INFO, ERROR, etc.)
 rabbitTemplate.convertAndSend(
 RabbitQueuesConfig.EXCHANGE_NAME,
 logMessage.getLevel(),
 logMessage.getMessage()
 );
 return "Log enviado: " + logMessage.getMessage();
 }
 
 // DTO para el cuerpo de la petición en los DTO


}