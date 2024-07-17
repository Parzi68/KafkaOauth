//package com.example.kafkaoauth;
//
//import jakarta.annotation.PostConstruct;
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.common.acl.AclBinding;
//import org.apache.kafka.common.acl.AclOperation;
//import org.apache.kafka.common.acl.AclPermissionType;
//import org.apache.kafka.common.resource.PatternType;
//import org.apache.kafka.common.resource.ResourcePattern;
//import org.apache.kafka.common.resource.ResourceType;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Properties;
//
//@Service
//public class KafkaAdminService {
//
//    private AdminClient adminClient;
//
//    @PostConstruct
//    public void init(){
//        Properties config = new Properties();
//        config.put("bootstrap.servers", "localhost:9092");
//        config.put("security.protocol", "SASL_PLAINTEXT");
//        config.put("sasl.mechanism", "OAUTHBEARER");
////        config.put("sasl.enabled.mechanisms","OAUTHBEARER,PLAIN");
////        config.put("listener.name.sasl_plaintext.sasl.enabled.mechanisms","OAUTHBEARER");
////        config.put("sasl.mechanism.inter.broker.protocol","OAUTHBEARER");
//        config.put("sasl.oauthbearer.token.endpoint.url", "http://localhost:8080/realms/master/protocol/openid-connect/token");
//        config.put("sasl.oauthbearer.jwks.endpoint.url","http://localhost:8080/realms/master/protocol/openid-connect/certs");
//        config.put("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required;");
////        config.put("sasl.oauthbearer.expected.audience","account");
////        config.put("oauth.client.id","kafka-client");
////        config.put("oauth.client.secret","QGiEFwkfRYw496g2qCPnev97aCVsVDgb");
////        config.put("sasl.oauthbearer.expected.audience","account,kafka-client");
//        adminClient = AdminClient.create(config);
//        addWriteAclToRole("kafka-client-producer", "myTopic");
//    }
//
//    void addWriteAclToRole(String role, String topic) {
//        AclBinding aclBinding = new AclBinding(
//                new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL),
//                new org.apache.kafka.common.acl.AccessControlEntry(
//                        "User:" + role,
//                        "*",
//                        AclOperation.WRITE,
//                        AclPermissionType.ALLOW
//                )
//        );
//        AclBinding aclBinding1 =  new AclBinding(
//                new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL),
//                new org.apache.kafka.common.acl.AccessControlEntry(
//                        "User:" + role,
//                        "*",
//                        AclOperation.READ,
//                        AclPermissionType.ALLOW
//                )
//        );
//        AclBinding aclBinding2 =  new AclBinding(
//                new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL),
//                new org.apache.kafka.common.acl.AccessControlEntry(
//                        "User:" + role,
//                        "*",
//                        AclOperation.ALTER,
//                        AclPermissionType.ALLOW
//                )
//        );
//
//        try {
//            adminClient.createAcls(Collections.singleton(aclBinding)).all().get();
//            System.out.println("Write ACL added to role: " + role + " for topic: " + topic);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
