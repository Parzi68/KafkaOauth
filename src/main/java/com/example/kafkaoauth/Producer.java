//package com.example.kafkaoauth;
//
//import com.nimbusds.oauth2.sdk.GrantType;
//import com.nimbusds.oauth2.sdk.ParseException;
//import com.nimbusds.oauth2.sdk.token.AccessToken;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.UserRepresentation;
//
//import java.util.Properties;
//
//public class Producer {
//
//    public static void main(String[] args) {
//        // Kafka configuration
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
////        props.put("security.protocol", "SASL_PLAINTEXT");
////        props.put("sasl.mechanism", "OAUTHBEARER");
//
//        // Keycloak server details (replace with your actual values)
//        String serverUrl = "http://localhost:8080/auth";
//        String realm = "ems-realm";
//        String clientId = "ems-client";
//
//        // User details (replace with your actual values)
//        String username = "john.doe";
//        String password = "Passw0rd!";
//
//        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
//            // Obtain access token for the user
//            String accessToken = getAccessToken(serverUrl, realm, clientId, username, password);
//
//            // Get user details from Keycloak
//            UserRepresentation user = getUserDetails(serverUrl, realm, clientId, username, password);
//
////            // Determine the data the user is allowed to access based on their role
////            String topic = getTopicForUser(user);
//
//            // Send message to Kafka topic
//            producer.send(new ProducerRecord<>("myTopic", "message with access token"));
//        } catch (ParseException e) {
//            System.err.println("Failed to obtain access token: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private static String getAccessToken(String serverUrl, String realm, String clientId, String username, String password) throws ParseException {
//        Keycloak keycloak = KeycloakBuilder.builder()
//                .serverUrl(serverUrl)
//                .realm(realm)
//                .clientId(clientId)
//                .username(username)
//                .password(password)
//                .grantType(OAuth2Constants.PASSWORD)
//                .build();
//
//        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
//        AccessToken accessToken = AccessToken.parse(tokenResponse.getToken());
//
//        System.out.println("Access token: " + accessToken.getValue());
//
//
//        return accessToken.getValue();
//    }
//
//    private static UserRepresentation getUserDetails(String serverUrl, String realm, String clientId, String username, String password) {
//        Keycloak keycloak = KeycloakBuilder.builder()
//                .serverUrl(serverUrl)
//                .realm(realm)
//                .clientId(clientId)
//                .username(username)
//                .password(password)
//                .grantType(OAuth2Constants.PASSWORD)
//                .build();
//
//        return keycloak.realm(realm).users().search(username).get(0);
//    }
//
////    private static String getTopicForUser(UserRepresentation user) {
////        // Determine the topic based on the user's role
////        if (user.hasRole("admin")) {
////            return "admin-topic";
////        } else if (user.hasRole("manager")) {
////            return "manager-topic";
////        } else {
////            return "employee-topic";
////        }
////    }
//}