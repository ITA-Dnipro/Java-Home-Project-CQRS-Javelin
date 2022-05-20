package com.softserveinc.ita.homeproject.reader.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.softserveinc.ita.homeproject.reader.mapper.HomeMapper;
import com.softserveinc.ita.homeproject.reader.model.CreateNews;
import com.softserveinc.ita.homeproject.reader.model.ReadNews;
import com.softserveinc.ita.homeproject.reader.model.UpdateNews;
import com.softserveinc.ita.homeproject.reader.service.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.reader.service.general.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Provider
@Component
@RequestMapping("/api/0")
@RestController
public class NewsApiImpl implements NewsApi {

    @Autowired
    private NewsService newsService;

    @Autowired
    protected HomeMapper mapper;

    @Override
    public ResponseEntity<ReadNews> createNews(CreateNews createNews) {
        NewsDto newsDto = mapper.convert(createNews, NewsDto.class);
        NewsDto createdNewsDto = newsService.create(newsDto);
        ReadNews response = mapper.convert(createdNewsDto, ReadNews.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteNews(Long id) {
        newsService.deactivateNews(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<ReadNews>> getAllNews(Integer pageNumber,
                                                     Integer pageSize,
                                                     String sort,
                                                     String filter,
                                                     Long id,
                                                     String title,
                                                     String text,
                                                     String source) {
        Page<NewsDto> readNews = newsService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(readNews.getContent().stream()
            .map(newsDto -> mapper.convert(newsDto, ReadNews.class))
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ReadNews> getNews(Long id) {
        NewsDto readNewsDto = newsService.getOne(id);
        ReadNews news = mapper.convert(readNewsDto, ReadNews.class);

        return ResponseEntity.status(HttpStatus.OK).body(news);
    }

    @Override
    public ResponseEntity<ReadNews> updateNews(Long id, UpdateNews updateNews) {
        NewsDto updateNewsDto = mapper.convert(updateNews, NewsDto.class);
        NewsDto readNewsDto = newsService.update(id, updateNewsDto);
        ReadNews response = mapper.convert(readNewsDto, ReadNews.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
