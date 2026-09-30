package com.rabqueues.config;


import org.springframework.amqp.core.*;
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
    return new Queue("hello", false);
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
    return new Queue(ALL_LOGS_QUEUE, true, false, false); // durable
    }
    @Bean
    public Queue errorsOnlyQueue() {
    return new Queue(ERRORS_ONLY_QUEUE, true, false, false); // durable
    }
    // 3. Declarar los Bindings
    // Cada binding une una cola a un exchange con una routing key. la routing key es la que determina qué mensajes van a qué colas.
    // en este caso las routing keys son "INFO", "WARNING" y "ERROR"
    @Bean
    public Binding bindAllLogsForInfo(DirectExchange exchange, Queue allLogsQueue) {

        return BindingBuilder.bind(allLogsQueue).to(exchange).with("INFO");

        }

        @Bean
        public Binding bindAllLogsForWarning(DirectExchange exchange, Queue allLogsQueue) {

        return BindingBuilder.bind(allLogsQueue).to(exchange).with("WARNING");

        }

        @Bean
        public Binding bindAllLogsForError(DirectExchange exchange, Queue allLogsQueue) {

        return BindingBuilder.bind(allLogsQueue).to(exchange).with("ERROR");

        }

        @Bean
        public Binding bindErrorsOnly(DirectExchange exchange, Queue errorsOnlyQueue) {

        return BindingBuilder.bind(errorsOnlyQueue).to(exchange).with("ERROR");

        }
}
