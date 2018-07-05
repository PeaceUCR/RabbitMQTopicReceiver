package com.example.demo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class RabbitMQConfig {

    public static final String queue1Name = "queue1";
    public static final String routingKey1= "adam.he.#";

    public static final String queue2Name = "queue2";
    public static final String routingKey2= "ping.he.#";

    public static final String topicExchangeName = "my-rabbit-exchange";

    //need this queue, otherwise the exception will be here
    //declare before use
    //https://stackoverflow.com/questions/3457305/how-can-i-check-whether-a-rabbitmq-message-queue-exists-or-not
    @Bean(name = "q1")
    Queue queue1(){
        return new Queue(queue1Name, false)  ;
    }

    @Bean(name = "q2")
    Queue queue2(){
        return new Queue(queue2Name, false)  ;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //this line can receive multiple queues with the same listener
        container.setQueueNames(queue1Name,queue2Name);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        //https://docs.spring.io/spring-amqp/docs/current/api/org/springframework/amqp/rabbit/listener/adapter/MessageListenerAdapter.html
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        Map queueMethodMap = new HashMap();
        queueMethodMap.put(queue1Name, "receiveMessage1");
        queueMethodMap.put(queue2Name, "receiveMessage2");
        adapter.setQueueOrTagToMethodName(queueMethodMap);
        return adapter;
    }

}
