package com.javalearn.kafka;

import com.javalearn.handlers.WikimediaChangesHandler;
import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WikimediaKafkaProducer {


    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${default-topic-name}")
    private String topicName;

    @Value("${api.wikimedia.url}")
    private String url;

    public WikimediaKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws Exception{

        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate,topicName);

        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler,new EventSource.Builder(
                ConnectStrategy.http(URI.create(url))
        )).build();

        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        }catch (InterruptedException e){
            log.error(String.format("exception %s",e.getMessage()));
            throw new RuntimeException("Error has occurred");
        }

    }


}
