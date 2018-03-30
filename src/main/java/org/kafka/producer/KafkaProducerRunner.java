package org.kafka.producer;


import java.io.IOException;

public class KafkaProducerRunner {
    public static void main(String[] args) throws IOException {
        Producer producer = new Producer();
        producer.runProducer();
    }
}
