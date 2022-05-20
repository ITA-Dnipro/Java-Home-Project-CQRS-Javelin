package com.softserveinc.ita.homeproject.reader.service.general.news;

import java.time.LocalDateTime;
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

        news.setCreateDate(LocalDateTime.now());
        news.setEnabled(true);
        newsRepository.save(news);

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

        return mapper.convert(readNews, NewsDto.class);
    }

    @Override
    public NewsDto getOne(Long id) {
        return newsRepository.findById(ID_PREFIX + id).filter(News::getEnabled)
            .map(news -> mapper.convert(news, NewsDto.class))
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
    }

    public Page<NewsDto> findAll() {
        return new PageImpl<>(newsRepository.findAll().stream()
            .map(news -> mapper.convert(news, NewsDto.class))
            .collect(Collectors.toList()));
    }

    @Override
    public void deactivateNews(Long id) {
        News toDelete = newsRepository.findById(ID_PREFIX + id).filter(News::getEnabled)
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        toDelete.setEnabled(false);

        newsRepository.save(toDelete);
    }
}
