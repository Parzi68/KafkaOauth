package com.example.kafkaoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpaService {

    private final RestTemplate restTemplate;
    private final String opaUrl;

    @Autowired
    public OpaService(RestTemplate restTemplate, @Value("${opa.url}") String opaUrl) {
        this.restTemplate = restTemplate;
        this.opaUrl = opaUrl;
    }

    public boolean isAuthorized(String jwt, String topic, String action) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> requestPayload = new HashMap<>();
            requestPayload.put("input", Map.of("jwt", jwt, "topic", topic, "action", action));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(opaUrl, request, Map.class);

            // Log the full response from OPA
            System.out.println("OPA Response: " + response.getBody());

            // Extract the decision from the response
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("result")) {
                Object result = responseBody.get("result");
                if (result instanceof Map) {
                    Map<String, Object> resultMap = (Map<String, Object>) result;
                    if (resultMap.containsKey("allow")) {
                        return (Boolean) resultMap.get("allow");
                    }
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
} 