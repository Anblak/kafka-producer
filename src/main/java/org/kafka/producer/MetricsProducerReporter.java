package org.kafka.producer;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MetricsProducerReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsProducerReporter.class);


    private static final String metricsNameFilter[] = {
            "record-queue-time-avg", "record-send-rate", "records-per-request-avg",
            "request-size-max", "network-io-rate", "record-queue-time-avg",
            "incoming-byte-rate", "batch-size-avg", "response-rate", "requests-in-flight"
    };

    public static void displayMetrics(Map<MetricName, ? extends Metric> metrics) {
        final Map<String, MetricPair> metricsDisplayMap = metrics.entrySet().stream()
                .filter(metricNameEntry ->
                        Arrays.asList(metricsNameFilter).contains(metricNameEntry.getKey().name()))
                .filter(metricNameEntry ->
                        !Double.isInfinite(metricNameEntry.getValue().value()) &&
                                !Double.isNaN(metricNameEntry.getValue().value()) &&
                                metricNameEntry.getValue().value() != 0
                )
                .map(entry -> new MetricPair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(
                        MetricPair::toString, it -> it, (a, b) -> a, TreeMap::new
                ));


        final StringBuilder builder = new StringBuilder(255);
        builder.append("\n---------------------------------------\n");
        metricsDisplayMap.forEach((name, metricPair) -> builder.append(String.format(Locale.US, "%50s%25s\t\t%,-10.2f\t\t%s\n",
                name,
                metricPair.metricName.name(),
                metricPair.metric.value(),
                metricPair.metricName.description()
        )));
        builder.append("\n---------------------------------------\n");
        LOGGER.info(builder.toString());
    }

    static class MetricPair {
        private final MetricName metricName;
        private final Metric metric;

        MetricPair(MetricName metricName, Metric metric) {
            this.metricName = metricName;
            this.metric = metric;
        }

        public String toString() {
            return metricName.group() + "." + metricName.name();
        }
    }
}
