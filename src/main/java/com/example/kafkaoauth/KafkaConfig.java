//package com.example.kafkaoauth;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        configProps.put("security.protocol", "SASL_PLAINTEXT");
////        configProps.put("sasl.mechanism.inter.broker.protocol","OAUTHBEARER");
////        configProps.put("listener.name.sasl_plaintext.sasl.enabled.mechanism", "OAUTHBEARER");
////        configProps.put("sasl.enabled.mechanisms","OAUTHBEARER");
////        configProps.put("sasl.mechanism","OAUTHBEARER");
////        configProps.put("sasl.oauthbearer.token.endpoint.url", "http://localhost:8080/realms/master/protocol/openid-connect/token");
////        configProps.put("sasl.login.callback.handler.class","org.apache.kafka.common.security.oauthbearer.secured.OAuthBearerLoginCallbackHandler");
////        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required clientId=\"kafka\" clientSecret=\"5HEzPHIIqjBw5RYFye5oRsi7rFYglwoY\";");
////        configProps.put("sasl.oauthbearer.jwks.endpoint.url","http://localhost:8080/realms/master/protocol/openid-connect/certs ");
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        props.put("security.protocol", "SASL_PLAINTEXT");
////        props.put("sasl.mechanism.inter.broker.protocol","OAUTHBEARER");
////        props.put("sasl.enabled.mechanisms","OAUTHBEARER");
////        props.put("sasl.mechanism","OAUTHBEARER");
////        props.put("listener.name.sasl_plaintext.sasl.enabled.mechanism", "OAUTHBEARER");
////        props.put("sasl.oauthbearer.token.endpoint.url", "http://localhost:8080/realms/master/protocol/openid-connect/token");
////        props.put("sasl.login.callback.handler.class","org.apache.kafka.common.security.oauthbearer.secured.OAuthBearerLoginCallbackHandler");
////        props.put("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required         clientId=\"kafka\" clientSecret=\"5HEzPHIIqjBw5RYFye5oRsi7rFYglwoY\";");
////        props.put("sasl.oauthbearer.jwks.endpoint.url","http://localhost:8080/realms/master/protocol/openid-connect/certs ");
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public NewTopic topic1() {
//        return new NewTopic("myTopic", 1, (short) 1);
//    }
//}
