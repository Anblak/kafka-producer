package org.es.producer;


import java.io.IOException;

public class ESProducerRunner {

    public static void main(String[] args) throws IOException {
        ESProducer esProducer = new ESProducer();
        esProducer.deleteIndexes();
        esProducer.createIndexes();
        esProducer.generateMessages();
        esProducer.close();
    }

}
