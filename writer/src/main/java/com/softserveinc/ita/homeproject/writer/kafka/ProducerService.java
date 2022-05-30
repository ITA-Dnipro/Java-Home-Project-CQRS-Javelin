package com.softserveinc.ita.homeproject.writer.kafka;

import com.softserveinc.ita.homeproject.writer.model.dto.general.news.KafkaMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;

    public void produce(KafkaMessageDto kafkaMessageDto) {
        kafkaTemplate.send("javelin_demo", kafkaMessageDto);
    }
}
