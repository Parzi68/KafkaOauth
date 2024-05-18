package com.example.kafkaoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@CrossOrigin
public class KafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping("/produce")
    public ResponseEntity<String> produceMessageGet(@RequestParam("topic") String topic, @RequestParam("message") String message) {
        kafkaService.sendMessage(topic, message);
        return ResponseEntity.ok("Message sent to Kafka topic: " + topic);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessageToKafkaTopic(@RequestParam("topic") String topic, @RequestParam("message") String message) {
        kafkaService.sendMessage(topic, message);
        return ResponseEntity.ok("Message sent to Kafka Topic: "+ topic);
    }
}
