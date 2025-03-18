package Kafka.example;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class ClientProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 10; i++) {
                String message = "Mensaje-" + i;
                producer.send(new ProducerRecord<>("test-topic", "key", message));
                System.out.println("Enviado: " + message);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}