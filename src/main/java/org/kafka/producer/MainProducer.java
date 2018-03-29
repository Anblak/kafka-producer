package org.kafka.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.io.IOException;

public class MainProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainProducer.class);

    public static void main(String[] args) throws IOException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigurations.class);
        Producer producer = new Producer();
        producer.runProducer();
    }
}
