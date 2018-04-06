package org.es.producer;

import com.google.common.io.Resources;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.message.generator.MessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

public class ESProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESProducer.class);
    //    private final String[] INDEXES = {"testdata_burger", "testdata_alien", "testdata_freedom", "testdata_hotdog", "testdata_metallica"};
//    private final String[] indexes_old = {"burger", "alien", "freedom", "hotdog", "metallica"};
    private String[] indexes = new String[5];
    private TransportClient client;

    private int messageSendingNumber;
    private Random random;
    private int numberOfOwners;


    public ESProducer() throws IOException {
        try (InputStream props = Resources.getResource("familiar-producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            messageSendingNumber = Integer.valueOf(properties.getProperty("message.sending.number"));
            numberOfOwners = Integer.valueOf(properties.getProperty("number.of.owners"));
        }
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        random = new Random();
    }

    public void createIndex(String indexName, int ownerNumber) {
        String timestamp = indexName + "_" + ownerNumber + "_" + LocalDateTime.now()
                .toLocalDate().toString().replaceAll("-", "");
        client.admin().indices().prepareCreate(timestamp).execute().actionGet();
        indexes[ownerNumber] = timestamp;
        LOGGER.info("Created index - " + indexName);
    }

    public void createIndexes() {
        for (int i = 0; i < numberOfOwners; i++) {
            createIndex("testdata", i);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void generateMessages() throws IOException {
        BulkRequestBuilder bulkRequestBuilder = prepareBulk();
        MessageGenerator messageGenerator = new MessageGenerator();
        for (int i = 0; i < messageSendingNumber; i++) {
            int index = random.nextInt(indexes.length);
            IndexRequestBuilder indexRequestBuilder = prepareIndex(indexes[index], "Does");
            indexRequestBuilder.setSource(messageGenerator.createJSONMessage(i, index), XContentType.JSON);
            bulkRequestBuilder.add(indexRequestBuilder);
            if (i % 10000 == 0) {
                LOGGER.info(bulkRequestBuilder.execute().actionGet().status() + "");
                LOGGER.info("Messages were sent - " + i);
                bulkRequestBuilder = prepareBulk();
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

    public void deleteIndexes() {
        Arrays.stream(client.admin()
                .indices()
                .getIndex(new GetIndexRequest())
                .actionGet()
                .getIndices())
                .filter(index -> index.startsWith("testdata"))
                .forEach(index -> client.admin().indices().delete(new DeleteIndexRequest(index)));
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Indexes deleted");
    }

    public void countDocsInIndex(String index) {
        System.out.println("Total doc in index" + index + " = " + client.prepareSearch(index).get().getHits().totalHits);
    }

    public void test(String index) {
        Map<String, Long> map = new HashMap<>();
        StringTerms stringTerms = client.prepareSearch(index).addAggregation(AggregationBuilders.terms("by_city")
                .field("client_location.city").size(1000)).setSize(0).get().getAggregations().get("by_city");
        stringTerms.getBuckets().forEach(bucket -> map.put(bucket.getKeyAsString(), bucket.getDocCount()));
        map.forEach((s, aLong) -> System.out.println(s + " " + aLong));
        ;
//        doc_count
//        for (SearchHit hit : response.getHits()) {
//            hit.getSource().forEach((s, o) -> System.out.println(s+ o));
//
//        }
//        StringTerms stringTerms =
//        System.out.println(response.getAggregations().get("by_city").ge);
    }

    public void countDocsInAllTestIndexes() {
//        Arrays.stream(client.admin()
//                .indices()
//                .getIndex(new GetIndexRequest())
//                .actionGet()
//                .getIndices())
//                .filter(index -> index.startsWith("testdata"))
//                .forEach(this::countDocsInIndex);

        Arrays.stream(client.admin()
                .indices()
                .getIndex(new GetIndexRequest())
                .actionGet()
                .getIndices())
                .filter(index -> index.startsWith("testdata"))
                .forEach(this::test);
    }

}
