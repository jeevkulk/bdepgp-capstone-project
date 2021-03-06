package org.upgrad.capstone.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.upgrad.capstone.config.Options;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.processor.TransactionProcessor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Gets transaction details from kafka and calls transaction processor for every new transaction
 */
public class KafkaSparkStreaming {
    private static Logger LOGGER = LoggerFactory.getLogger(KafkaSparkStreaming.class);

    public void getTransactionsDataStream() throws Exception {
        Options options = Options.getInstance();
        SparkConf sparkConf = new SparkConf().setAppName("bdepgp-capstone-project").setMaster("local");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "18.211.252.152:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", options.getKAFKA_GROUP_ID());
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);
        Collection<String> topics = Arrays.asList("transactions-topic-verified");

        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
            javaStreamingContext,
            LocationStrategies.PreferConsistent(),
            ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
        );

        JavaDStream<String> javaDStream = stream.map(x -> x.value());
        javaDStream.foreachRDD(x -> System.out.println(x.count()));
        javaDStream.foreachRDD(new VoidFunction<JavaRDD<String>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(JavaRDD<String> rdd) {
                rdd.foreach(jsonString -> {

                    System.out.println("New Transaction: "+jsonString);
                    Gson gson = new Gson();
                    CardTransaction cardTransaction = gson.fromJson(jsonString, CardTransaction.class);
                    TransactionProcessor processor = new TransactionProcessor(cardTransaction);
                    processor.doProcess();
                });
            }
        });
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
}
