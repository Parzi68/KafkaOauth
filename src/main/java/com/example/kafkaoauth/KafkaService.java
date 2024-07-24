/**
 * This package contains the implementation of the KafkaService class, which provides functionality for sending and consuming messages using Apache Kafka.
 */
package com.example.kafkaoauth;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @KafkaListener(topics = "myTopic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
        messageQueue.offer(message);
    }

    public String consumeMessage(String topic) {
        try {
            // Poll the queue for a message, waiting up to 5 seconds
            String message = messageQueue.poll(5, TimeUnit.SECONDS);
            if (message != null) {
                return message;
            } else {
                return "No messages available to consume.";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error occurred while consuming message.";
        }
    }
}
