package Kafka.example;

import org.apache.kafka.clients.consumer.*;

import java.util.Properties;

public class ServerConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "server-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(java.util.Collections.singletonList("test-topic"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(java.time.Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Recibido: " + record.value());
                }
            }
        }
    }
}