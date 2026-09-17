package com.rabqueues.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rabqueues.config.RabbitQueuesConfig;



@Service
public class LogConsumer {

    @RabbitListener(queues = RabbitQueuesConfig.ALL_LOGS_QUEUE)
    public void receiveAllLogs(String message) {
    System.out.println("[MONITOR GENERAL] Log recibido: " + message);

    }
    
    @RabbitListener(queues = RabbitQueuesConfig.ERRORS_ONLY_QUEUE)

    public void receiveErrorLogs(String message) {
    // Simular una alerta
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println("[ALERTA CRÍTICA] Error detectado: no hay error es un log de prueba" + message);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    
    }
}

