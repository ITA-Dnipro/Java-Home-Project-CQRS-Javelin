package com.softserveinc.ita.homeproject.writer.service.general.news;

import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import org.springframework.data.domain.Page;

public interface NewsService {

    Page<NewsDto> findAll();

    NewsDto create(NewsDto newsDto);

    NewsDto update(Long id, NewsDto newsDto);

    void deactivateNews(Long id);

    NewsDto getOne(Long id);
}
