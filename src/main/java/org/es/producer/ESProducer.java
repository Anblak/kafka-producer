package org.es.producer;

import com.google.common.io.Resources;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.message.generator.MessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Random;

public class ESProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESProducer.class);
    private final String[] indexes = {"burger", "alien", "freedom", "hotdog", "metallica"};

    private TransportClient client;
    private BulkRequestBuilder bulkRequestBuilder;
    private int messageSendingNumber;
    private Random random;


    public ESProducer() throws IOException {
        try (InputStream props = Resources.getResource("familiar-producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            messageSendingNumber = Integer.valueOf(properties.getProperty("message.sending.number"));
        }
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        random = new Random();
    }

    public void createIndex(String indexName) {
        client.admin().indices().prepareCreate(indexName).execute().actionGet();
        LOGGER.info("Created index - " + indexName);
    }

    public void createIndexes() {
        for (int i = 0; i < indexes.length; i++) {
            createIndex(indexes[i]);
        }
    }

    public void generateMessages() throws IOException {

        MessageGenerator messageGenerator = new MessageGenerator();
        bulkRequestBuilder = prepareBulk();

        for (int i = 0; i < messageSendingNumber; i++) {
            IndexRequestBuilder indexRequestBuilder = prepareIndex(indexes[random.nextInt(indexes.length)], "Does'nt matter");
//            IndexRequest indexRequest = new IndexRequest(indexes[random.nextInt(indexes.length)]);
//            indexRequest.source(messageGenerator.createJSONMessage(i), XContentType.JSON);
//            client.index(indexRequest).actionGet();
            indexRequestBuilder.setSource(messageGenerator.createJSONMessage(i), XContentType.JSON);
            bulkRequestBuilder.add(indexRequestBuilder);

            if (i % 1000 == 0) {
                LOGGER.info("Messages were sent - " + i);
                LOGGER.error(bulkRequestBuilder.execute().actionGet().buildFailureMessage());
            }
        }
    }

    public BulkRequestBuilder prepareBulk() {
        return client.prepareBulk();
    }

    public IndexRequestBuilder prepareIndex(String indexName, String indexType) {
        return client.prepareIndex(indexName, indexType);
    }

    public void close() {
        client.close();
        LOGGER.info("ESProducer stopped");
    }

}
