package com.javalearn.kafka;

import com.javalearn.entity.WikimediaData;
import com.javalearn.repository.WikimediaDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaDatabaseWikiConsumer {

    private WikimediaDataRepository wikimediaDataRepository;


    @KafkaListener(topics = "${default-topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage){
        log.info(String.format("Event message received-> %s",eventMessage));
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        wikimediaDataRepository.save(wikimediaData);
    }

}
