package com.softserveinc.ita.homeproject.reader.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserveinc.ita.homeproject.reader.data.dto.general.news.KafkaMessageDto;
import com.softserveinc.ita.homeproject.reader.data.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.reader.mapper.ServiceMapper;
import com.softserveinc.ita.homeproject.reader.service.general.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    @Autowired
    private NewsService newsService;

    private final ServiceMapper serviceMapper;

    @KafkaListener(topics = "javelin_demo")
    public void consume(String kafkaMessage) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        KafkaMessageDto kafkaMessageDto = mapper.readValue(kafkaMessage, KafkaMessageDto.class);

        NewsDto newsDto = serviceMapper.convert(kafkaMessageDto, NewsDto.class);

        if (HttpMethod.POST.equals(kafkaMessageDto.getMethodType())) {
            newsService.create(newsDto);
        } else if (HttpMethod.PUT.equals(kafkaMessageDto.getMethodType())) {
            newsService.update(Long.valueOf(newsDto.getId()), newsDto);
        } else if (HttpMethod.DELETE.equals(kafkaMessageDto.getMethodType())) {
            newsService.deactivateNews(Long.valueOf(newsDto.getId()));
        }
    }
}
