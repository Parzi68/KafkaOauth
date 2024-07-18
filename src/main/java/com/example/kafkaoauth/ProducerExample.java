//package com.example.kafkaoauth;
//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import java.util.Properties;
//
//public class ProducerExample {
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
////        props.put("security.protocol", "SASL_PLAINTEXT");
////        props.put("sasl.mechanism.inter.broker.protocol","OAUTHBEARER");
////        props.put("sasl.mechanism", "OAUTHBEARER");
////        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
////        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
////        props.put("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required;");
////        props.put("sasl.oauthbearer.jwks.endpoint.url","http://localhost:8080/realms/master/protocol/openid-connect/certs ");
//
//
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//        producer.send(new ProducerRecord<>("myTopic", "key", "value"));
//        producer.close();
//    }
//}
