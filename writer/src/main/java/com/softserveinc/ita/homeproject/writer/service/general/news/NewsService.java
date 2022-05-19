package com.softserveinc.ita.homeproject.writer.service.general.news;

import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.model.entity.general.news.News;
import com.softserveinc.ita.homeproject.writer.service.QueryableService;

public interface NewsService extends QueryableService<News, NewsDto> {
    NewsDto create(NewsDto newsDto);

    NewsDto update(Long id, NewsDto newsDto);

    void deactivateNews(Long id);
}
