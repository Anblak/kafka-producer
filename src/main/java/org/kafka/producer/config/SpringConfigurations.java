//package org.kafka.producer.config;
//
//import org.kafka.producer.Producer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//@Configuration
//@PropertySource("classpath:familiar-producer.properties")
//public class SpringConfigurations {
//
//    @Value("${message.sending.number}")
//    private int messageSendingNumber;
//
//    @Value("${topic.name}")
//    private String topicName;
//
//    @Bean
//    public Producer producer() {
//        return new Producer(messageSendingNumber, topicName);
//    }
//}
