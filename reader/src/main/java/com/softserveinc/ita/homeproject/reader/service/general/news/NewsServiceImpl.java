package com.softserveinc.ita.homeproject.reader.service.general.news;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.ita.homeproject.reader.data.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.reader.data.entity.general.news.News;
import com.softserveinc.ita.homeproject.reader.data.entity.general.news.NewsRepository;
import com.softserveinc.ita.homeproject.reader.mapper.ServiceMapper;
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
        News newsToCreate = mapper.convert(newsDto, News.class);

        newsToCreate.setId(ID_PREFIX + newsToCreate.getId());
        newsToCreate.setCreateDate(LocalDateTime.now());
        newsToCreate.setEnabled(true);

        newsRepository.save(newsToCreate);
        newsToCreate.setId(newsToCreate.getId().substring(ID_PREFIX.length()));

        return mapper.convert(newsToCreate, NewsDto.class);
    }

    @Override
    @Transactional
    public NewsDto update(Long id, NewsDto newsDto) {
        News newsToUpdate = newsRepository.findById(ID_PREFIX + id)
            .filter(News::getEnabled)
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));

        if (newsDto.getTitle() != null) {
            newsToUpdate.setTitle(newsDto.getTitle());
        }

        if (newsDto.getText() != null) {
            newsToUpdate.setText(newsDto.getText());
        }

        if (newsDto.getDescription() != null) {
            newsToUpdate.setDescription(newsDto.getDescription());
        }

        if (newsDto.getPhotoUrl() != null) {
            newsToUpdate.setPhotoUrl(newsDto.getPhotoUrl());
        }

        if (newsDto.getSource() != null) {
            newsToUpdate.setSource(newsDto.getSource());
        }

        newsToUpdate.setUpdateDate(LocalDateTime.now());

        newsRepository.save(newsToUpdate);
        newsToUpdate.setId(newsToUpdate.getId().substring(ID_PREFIX.length()));

        return mapper.convert(newsToUpdate, NewsDto.class);
    }

    @Override
    public NewsDto getOne(Long id) {
        NewsDto newsDto = newsRepository.findById(ID_PREFIX + id)
            .filter(News::getEnabled)
            .map(news -> mapper.convert(news, NewsDto.class))
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));

        newsDto.setId(newsDto.getId().substring(ID_PREFIX.length()));

        return newsDto;
    }

    public Page<NewsDto> findAll() {
        List<NewsDto> allNews = newsRepository.findAll().stream()
            .map(news -> mapper.convert(news, NewsDto.class))
            .collect(Collectors.toList());

        allNews.forEach(newsDto -> newsDto.setId(newsDto.getId().substring(ID_PREFIX.length())));

        return new PageImpl<>(allNews);
    }

    @Override
    public void deactivateNews(Long id) {
        News newsToDelete = newsRepository.findById(ID_PREFIX + id)
            .filter(News::getEnabled)
            .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        newsToDelete.setEnabled(false);

        newsRepository.save(newsToDelete);
    }
}
