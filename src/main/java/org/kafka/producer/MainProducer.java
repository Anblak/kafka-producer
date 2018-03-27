package org.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class MainProducer {
    @Value("message.sending.number")
    private static int messageSendingNumber;
    @Value("topic.name")
    private static String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainProducer.class);



    public static void main(String[] args) throws IOException {
        KafkaProducer<String, String> producer;
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);

        }
        LOGGER.info("Starting sending messages ...");
        try {
            for (int i = 0; i < messageSendingNumber; i++) {

                producer.send(new ProducerRecord<>(
                        topicName,
                        objectMapper.writeValueAsString(new Message(
                                i,
                                InetAddress.getLocalHost().toString(),
                                LocalDateTime.now(),
                                UUID.randomUUID().toString()))));
                if (i % 1000 == 0){
                    LOGGER.info("Sent msg number " + i);
                }
            }
        } catch (Throwable throwable) {
            LOGGER.error(Arrays.toString(throwable.getStackTrace()));
        } finally {
            LOGGER.info(messageSendingNumber+ " - messages were sent");
            producer.close();
        }
    }
}
