package com.example.kafkaoauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The `KafkaController` class is a Spring REST controller that provides endpoints for producing and consuming messages to/from a Kafka topic.
 * 
 * The controller uses the `KafkaService` to send and receive messages, and the `OpaService` to check if the user is authorized to perform the requested action.
 * 
 * The `produceMessageGet` method allows a user to produce a message to a specified Kafka topic, if they are authorized to do so.
 * 
 * The `consumeMessageGet` method allows a user to consume a message from a specified Kafka topic, if they are authorized to do so.
 * 
 * The `extractJwt` method is a helper method that extracts the JWT token from the user's authentication, which is then used to check authorization with the `OpaService`.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaService kafkaService;
    private final OpaService opaService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public KafkaController(KafkaService kafkaService, OpaService opaService, OAuth2AuthorizedClientService authorizedClientService) {
        this.kafkaService = kafkaService;
        this.opaService = opaService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/produce")
    public String produceMessageGet(Authentication authentication,
                                    @RequestParam("topic") String topic,
                                    @RequestParam("message") String message) {
        try {
            System.out.println("Authentication class: " + authentication.getClass().getName());

            String jwt = extractJwt(authentication);
            if (jwt == null) {
                throw new IllegalArgumentException("Invalid authentication token type or missing JWT.");
            }

            System.out.println("Sending JWT to OPA: " + jwt);
            System.out.println("Sending topic to OPA: " + topic);

            boolean isAuthorized = opaService.isAuthorized(jwt, topic,"produce");
            if (!isAuthorized) {
                throw new SecurityException("Not authorized to produce messages for topic: " + topic);
            }

            kafkaService.sendMessage(message, topic);
            return "Message produced: " + message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/consume")
    public String consumeMessageGet(Authentication authentication,
                                    @RequestParam("topic") String topic) {
        try {
            System.out.println("Authentication class: " + authentication.getClass().getName());

            String jwt = extractJwt(authentication);
            if (jwt == null) {
                throw new IllegalArgumentException("Invalid authentication token type or missing JWT.");
            }

            System.out.println("Sending JWT to OPA: " + jwt);
            System.out.println("Sending topic to OPA: " + topic);

            boolean isAuthorized = opaService.isAuthorized(jwt, topic,"consume");
            if (!isAuthorized) {
                throw new SecurityException("Not authorized to consume messages from topic: " + topic);
            }

            String message = kafkaService.consumeMessage(topic);
            return "Message consumed: " + message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String extractJwt(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getTokenValue();
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            String principalName = oauthToken.getName();

            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);

            if (authorizedClient != null) {
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                if (accessToken != null) {
                    return accessToken.getTokenValue();
                }
            }

            throw new IllegalArgumentException("JWT not found in authentication token.");
        } else {
            throw new IllegalArgumentException("Invalid authentication token type: " + authentication.getClass().getName());
        }
    }
}
