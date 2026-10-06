package com.rabqueues.consumer;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabqueues.DTOs.EmailPayload;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;




    @RabbitListener(queues = "emailQueue")
    public void consumeEmailMessage(EmailPayload message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)  throws IOException {

        if (message.getTo() == null|| message.getSubject() == null|| message.getBody() == null) {

            System.err.println("Mensaje incompleto. Enviando a DLQ. DeliveryTag: "+ deliveryTag);

            channel.basicReject(deliveryTag, false);
            return;
        }

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(message.getTo());
        mail.setSubject(message.getSubject());
        mail.setText(message.getBody());

        System.out.println( deliveryTag + " - Enviando correo a: " + message.getTo());

        mailSender.send(mail);

        channel.basicAck(deliveryTag, false);

        System.out.println("Email enviado correctamente a: " + message.getTo());
    }
}
