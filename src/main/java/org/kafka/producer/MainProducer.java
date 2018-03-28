package org.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

public class MainProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainProducer.class);
    @Value("message.sending.number")
    private static int messageSendingNumber;
    @Value("topic.name")
    private static String topicName;

    public static void main(String[] args) throws IOException {
        KafkaProducer<String, String> producer;
        Random rand = new Random();

        ObjectMapper objectMapper = new ObjectMapper();
        String browserNames[] = {"Google Chrome 48–55", "Mozilla Firefox 44–50", "Microsoft Edge 14", "Opera 35–42",
                "Apple Safari 10", "SeaMonkey 2.24–2.30", "Pale Moon 27.0.0[18]"};

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Coordinates.class);
        List<Coordinates> coordinates = objectMapper.readValue(new File("locations.json"), collectionType);

        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }
        LOGGER.info("Starting sending messages ...");
        try {
            for (int i = 0; i < messageSendingNumber; i++) {
                Coordinates location = coordinates.get(rand.nextInt(coordinates.size()));
                if (i % 2 == 0) {
                    location.setLongitude(location.getLongitude() + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
                    location.setLatitude(location.getLatitude() + ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
                } else {
                    location.setLongitude(location.getLongitude() - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
                    location.setLatitude(location.getLatitude() - ((double) (int) (rand.nextDouble() * 1000000.0)) / 1000000.0);
                }
                producer.send(new ProducerRecord<>(
                        topicName,
                        objectMapper.writeValueAsString(new Message(
                                i,
                                InetAddress.getLocalHost().toString(),
                                LocalDateTime.now(),
                                UUID.randomUUID().toString(),
                                UUID.randomUUID().toString(),
                                rand.nextInt(600) + "",
                                new Random().longs().toString(),
                                location,
                                browserNames[rand.nextInt(browserNames.length)]))));
                if (i % 1000 == 0) {
                    LOGGER.info("Sent msg number " + i);
                }
            }
        } catch (Throwable throwable) {
            LOGGER.error(Arrays.toString(throwable.getStackTrace()));
        } finally {
            LOGGER.info(messageSendingNumber + " - messages were sent");
            producer.close();
        }
    }
}
