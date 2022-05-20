package com.softserveinc.ita.homeproject.reader.service.general.news;

import com.softserveinc.ita.homeproject.reader.service.dto.general.news.NewsDto;
import org.springframework.data.domain.Page;


public interface NewsService {

    Page<NewsDto> findAll();
    
    NewsDto getOne(Long id);

    NewsDto create(NewsDto newsDto);

    NewsDto update(Long id, NewsDto newsDto);

    void deactivateNews(Long id);
}
