package com.javalearn.handlers;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


@Slf4j
public class WikimediaChangesHandler implements BackgroundEventHandler {
    @Autowired
    private KafkaTemplate<String,String> template;


    private String topicName;

    public WikimediaChangesHandler(KafkaTemplate<String, String> template, String topicName) {
        this.template = template;
        this.topicName = topicName;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        log.info(String.format("event data %s",messageEvent.getData()));
        template.send(topicName,messageEvent.getData());
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
