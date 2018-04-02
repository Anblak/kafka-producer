package org.kafka.producer;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.message.generator.MessageGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private int messageSendingNumber;
    private String topicName;

    public Producer() throws IOException {
        try (InputStream props = Resources.getResource("familiar-producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            messageSendingNumber = Integer.valueOf(properties.getProperty("message.sending.number"));
            topicName = properties.getProperty("topic.name");
        }
    }

    public void runProducer() throws IOException {
        KafkaProducer<String, String> producer;

        MessageGenerator messageGenerator = new MessageGenerator();

        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }
        LOGGER.info("Starting sending messages ...");
        try {
            for (int i = 0; i < messageSendingNumber; i++) {
                //TODO companyID
                producer.send(new ProducerRecord<>(topicName, messageGenerator.createJSONMessage(i , 1231)));

                if (i % 1000 == 0) {
                    LOGGER.info("Sent msg number " + i);
                }
            }
        } catch (Throwable throwable) {
            LOGGER.error(Arrays.toString(throwable.getStackTrace()));
        } finally {
            producer.flush();
            LOGGER.info(" - messages were sent");
            MetricsProducerReporter.displayMetrics(producer.metrics());
            producer.close();
        }
    }
}
