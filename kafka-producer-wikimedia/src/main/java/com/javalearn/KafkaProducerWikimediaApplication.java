package com.javalearn;

import com.javalearn.kafka.WikimediaKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerWikimediaApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerWikimediaApplication.class,args);
    }

    @Autowired
    private WikimediaKafkaProducer kafkaProducer;

    @Override
    public void run(String... args) throws Exception {
        kafkaProducer.sendMessage();
    }
}