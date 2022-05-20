package com.softserveinc.ita.homeproject.reader.service.general.news;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.ita.homeproject.reader.data.entity.general.news.News;
import com.softserveinc.ita.homeproject.reader.data.entity.general.news.NewsRepository;
import com.softserveinc.ita.homeproject.reader.mapper.ServiceMapper;
import com.softserveinc.ita.homeproject.reader.service.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.reader.service.exception.NotFoundHomeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final String NOT_FOUND_NEWS = "Can't find news with given ID:";

    private static final String FORMAT = "%s %d";

    private static final String ID_PREFIX = "News";

    private final NewsRepository newsRepository;

    private final ServiceMapper mapper;

    @Override
    @Transactional
    public NewsDto create(NewsDto newsDto) {
        News news = mapper.convert(newsDto, News.class);
        news.setId(ID_PREFIX + news.getId());
        news.setCreateDate(LocalDateTime.now());
        news.setEnabled(true);
        newsRepository.save(news);
        news.setId(news.getId().substring(ID_PREFIX.length()));
        return mapper.convert(news, NewsDto.class);
    }

    @Override
    @Transactional
    public NewsDto update(Long id, NewsDto newsDto) {
        News readNews = newsRepository.findById(ID_PREFIX + id)
            .filter(News::getEnabled)
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));

        if (newsDto.getTitle() != null) {
            readNews.setTitle(newsDto.getTitle());
        }

        if (newsDto.getText() != null) {
            readNews.setText(newsDto.getText());
        }

        if (newsDto.getDescription() != null) {
            readNews.setDescription(newsDto.getDescription());
        }

        if (newsDto.getPhotoUrl() != null) {
            readNews.setPhotoUrl(newsDto.getPhotoUrl());
        }

        if (newsDto.getSource() != null) {
            readNews.setSource(newsDto.getSource());
        }

        readNews.setUpdateDate(LocalDateTime.now());
        newsRepository.save(readNews);
        readNews.setId(readNews.getId().substring(ID_PREFIX.length()));

        return mapper.convert(readNews, NewsDto.class);
    }

    @Override
    public NewsDto getOne(Long id) {
        NewsDto newsDto = newsRepository.findById(ID_PREFIX + id).filter(News::getEnabled)
            .map(news -> mapper.convert(news, NewsDto.class))
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        newsDto.setId(newsDto.getId().substring(ID_PREFIX.length()));

        return newsDto;
    }

    public Page<NewsDto> findAll() {
        List<NewsDto> collect = newsRepository.findAll().stream()
            .map(news -> mapper.convert(news, NewsDto.class))
            .collect(Collectors.toList());
        collect.forEach(newsDto -> newsDto.setId(newsDto.getId().substring(ID_PREFIX.length())));
        return new PageImpl<>(collect);
    }

    @Override
    public void deactivateNews(Long id) {
        News toDelete = newsRepository.findById(ID_PREFIX + id).filter(News::getEnabled)
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        toDelete.setEnabled(false);

        newsRepository.save(toDelete);
    }
}
