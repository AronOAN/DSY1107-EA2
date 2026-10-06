package com.rabqueues.config;


import org.springframework.amqp.core.*;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitQueuesConfig {



        

        /**
     * Define la cola "hello"
     *
     * Esta cola es idempotente:
     * - Si no existe, la crea
     * - Si ya existe, la reutiliza
     *
     * durable=false: se borra si RabbitMQ se reinicia
     * (En producción, normalmente usarías durable=true)
     */
    @Bean
    public Queue helloQueue() {
    return new Queue("hello", true);
    }


    @Bean
    public Queue emailDlq() {           
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Binding emailDlqBinding() {
        return BindingBuilder.bind(emailDlq()).to(emailDlx()).with(DLX_ROUTING_KEY);
    }
    @Bean
    public DirectExchange emailDlx() {
        return new DirectExchange(DLX_EXCHANGE);
    }


    // ========== DLX (Dead Letter Exchange) ==========
    public static final String DLX_EXCHANGE = "email.dlx";
    public static final String DLQ_QUEUE = "emailDlq";
    public static final String DLX_ROUTING_KEY = "email.dead";



    @Bean
    public Queue emailQueue() {

        return QueueBuilder.durable("emailQueue")
                .withArgument("x-dead-letter-exchange",DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",DLX_ROUTING_KEY)
                .build();
    }

   // 2. Definición del Exchange (Direct)
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("emailExchange");
    }

        // 3. Unión (Binding) de la cola con el exchange usando la clave de enrutamiento
    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue)
                .to(emailExchange)
                .with("emailRoutingKey");
    }
        // 4. IMPORTANTE: Convertidor JSON para que Spring traduzca automáticamente 
    // los objetos Java (EmailPayload) desde y hacia JSON al comunicarse con RabbitMQ

        @Bean
        public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
        }
   // Más abajo en el mismo archivo, agregamos la configuración para el patrón de enrutamiento directo (Direct Exchange)


    
    public static final String EXCHANGE_NAME = "logs_direct_exchange";
    public static final String ALL_LOGS_QUEUE = "all_logs_queue";
    public static final String ERRORS_ONLY_QUEUE = "errors_only_queue";

    


    // 1. Declarar el Exchange
    @Bean
    public DirectExchange directExchange() {
        
    return new DirectExchange(EXCHANGE_NAME);
    }
    // 2. Declarar las Queues


    @Bean
    public Queue allLogsQueue() {
    return new Queue(ALL_LOGS_QUEUE, true); // durable
    }
    @Bean
    public Queue errorsOnlyQueue() {
    return new Queue(ERRORS_ONLY_QUEUE, true); // durable
    }
    // 3. Declarar los Bindings
    // Cada binding une una cola a un exchange con una routing key. la routing key es la que determina qué mensajes van a qué colas.
    // en este caso las routing keys son "INFO", "WARNING" y "ERROR"
    @Bean
    public Binding bindAllLogsForInfo() {

        return BindingBuilder.bind(allLogsQueue()).to(directExchange()).with(Nivel.INFO.routingKey());

        }

        @Bean
        public Binding bindAllLogsForWarning() {

        return BindingBuilder.bind(allLogsQueue()).to(directExchange()).with(Nivel.WARNING.routingKey());

        }

        @Bean
        public Binding bindAllLogsForError() {

        return BindingBuilder.bind(allLogsQueue()).to(directExchange()).with(Nivel.ERROR.routingKey());

        }

        @Bean
        public Binding bindErrorsOnly() {

        return BindingBuilder.bind(errorsOnlyQueue()).to(directExchange()).with(Nivel.ERROR.routingKey());

        }
}
