package com.example.kafkaoauth;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Map;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaService kafkaService;
    private final OpaService opaService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
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
            // Log the authentication object class type
            System.out.println("Authentication class: " + authentication.getClass().getName());

            // Extract JWT from the authentication token
            String jwt = extractJwt(authentication);
            if (jwt == null) {
                throw new IllegalArgumentException("Invalid authentication token type or missing JWT.");
            }

            // Perform authorization check with OPA
            System.out.println("Sending JWT to OPA: " + jwt);
            System.out.println("Sending topic to OPA: " + topic);

            boolean isAuthorized = opaService.isAuthorized(jwt, topic);
            if (!isAuthorized) {
                throw new SecurityException("Not authorized to produce messages for topic: " + topic);
            }

            // Send the message to the specified Kafka topic
            kafkaService.sendMessage(message, topic);
            return "Message produced: " + message;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
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
