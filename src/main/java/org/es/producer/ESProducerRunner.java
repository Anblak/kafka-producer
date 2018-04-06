package org.es.producer;


import org.message.generator.MessageGenerator;

import java.io.IOException;

public class ESProducerRunner {

    public static void main(String[] args) throws IOException {
        ESProducer esProducer = new ESProducer();
        esProducer.test("testdata_1_20180404");
//        esProducer.deleteIndexes();
//        esProducer.createIndexes();
//        esProducer.generateMessages();
        esProducer.close();
//        MessageGenerator messageGenerator = new MessageGenerator();
//        System.out.println(messageGenerator.createJSONMessage(10,1));
    }

}
