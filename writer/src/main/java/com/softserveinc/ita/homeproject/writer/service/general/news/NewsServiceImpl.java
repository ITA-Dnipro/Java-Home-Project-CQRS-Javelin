package com.softserveinc.ita.homeproject.writer.service.general.news;

import com.softserveinc.ita.homeproject.writer.exception.NotFoundHomeException;
import com.softserveinc.ita.homeproject.writer.mapper.service.ServiceMapper;
import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.model.entity.general.news.News;
import com.softserveinc.ita.homeproject.writer.repository.general.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final String NOT_FOUND_NEWS = "Can't find news with given ID:";

    private static final String FORMAT = "%s %d";

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
        News fromDB = newsRepository.findById(id)
                .filter(News::getEnabled)
                .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));

        if (newsDto.getTitle() != null) {
            fromDB.setTitle(newsDto.getTitle());
        }

        if (newsDto.getText() != null) {
            fromDB.setText(newsDto.getText());
        }

        if (newsDto.getDescription() != null) {
            fromDB.setDescription(newsDto.getDescription());
        }

        if (newsDto.getPhotoUrl() != null) {
            fromDB.setPhotoUrl(newsDto.getPhotoUrl());
        }

        if (newsDto.getSource() != null) {
            fromDB.setSource(newsDto.getSource());
        }

        fromDB.setUpdateDate(LocalDateTime.now());
        newsRepository.save(fromDB);
        return mapper.convert(fromDB, NewsDto.class);
    }

    @Override
    public Page<NewsDto> findAll() {
        List<News> newsDto = new ArrayList<>();
        newsRepository.findAll().forEach(newsDto::add);
        return new PageImpl<>(newsDto.stream()
                .filter(News::getEnabled)
                .map(news -> mapper.convert(news, NewsDto.class))
                .collect(Collectors.toList()));

    }

    @Override
    public void deactivateNews(Long id) {
        News toDelete = newsRepository.findById(id)
                .filter(News::getEnabled)
                .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        toDelete.setEnabled(false);
        newsRepository.save(toDelete);
    }

    @Override
    public NewsDto getOne(Long id) {
        News newsFromDb = newsRepository.findById(id)
                .filter(News::getEnabled)
                .orElseThrow(() -> new NotFoundHomeException(String.format(FORMAT, NOT_FOUND_NEWS, id)));
        return mapper.convert(newsFromDb, NewsDto.class);
    }

}
