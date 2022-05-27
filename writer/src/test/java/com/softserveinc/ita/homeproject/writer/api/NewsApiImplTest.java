package com.softserveinc.ita.homeproject.writer.api;

import static com.softserveinc.ita.homeproject.writer.Util.createBaseNewsDto;
import static com.softserveinc.ita.homeproject.writer.Util.createBasicModelForNewsCreation;
import static com.softserveinc.ita.homeproject.writer.Util.createBasicReadNewsModel;
import static com.softserveinc.ita.homeproject.writer.Util.createBasicUpdateNewsModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import com.softserveinc.ita.homeproject.writer.mapper.model.HomeMapper;
import com.softserveinc.ita.homeproject.writer.model.CreateNews;
import com.softserveinc.ita.homeproject.writer.model.ReadNews;
import com.softserveinc.ita.homeproject.writer.model.UpdateNews;
import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.service.general.news.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class NewsApiImplTest {

    @Mock
    private NewsService newsService;

    @Mock
    private static HomeMapper mapper;

    @InjectMocks
    private NewsApiImpl api;

    @Test
    void createStatusShouldReturnCreatedNewsWithStatusCreated() {
        CreateNews createdNews = createBasicModelForNewsCreation();
        NewsDto createdNewsDto = createBaseNewsDto();
        ReadNews newsToReturn = createBasicReadNewsModel();
        when(mapper.convert(createdNews, NewsDto.class)).thenReturn(createdNewsDto);
        when(newsService.create(createdNewsDto)).thenReturn(createdNewsDto);
        when(mapper.convert(createdNewsDto, ReadNews.class)).thenReturn(newsToReturn);

        ResponseEntity<ReadNews> response = api.createNews(createdNews);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(newsToReturn);
    }

    @Test
    void deleteNewsShouldReturnResponseWithStatusNoContent() {
        Long newsId = 1000L;
        doNothing().when(newsService).deactivateNews(newsId);

        assertThat(api.deleteNews(newsId).getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllNewsShouldReturnCorrectResponseWithCodeOk() {
        NewsDto newsDto = createBaseNewsDto();
        ReadNews newsToReturn = createBasicReadNewsModel();
        when(newsService.findAll()).thenReturn(new PageImpl<>(List.of(newsDto)));
        when(mapper.convert(newsDto, ReadNews.class)).thenReturn(newsToReturn);

        ResponseEntity<List<ReadNews>> response = api.getAllNews(1, 1, "", "", 1L, "", "", "");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0)).usingRecursiveComparison().isEqualTo(newsToReturn);
    }

    @Test
    void getNewsShouldReturnCorrectResponseWithCodeOk() {
        Long newsId = 1000L;
        NewsDto savedNews = createBaseNewsDto();
        ReadNews newsToReturn = createBasicReadNewsModel();
        when(newsService.getOne(newsId)).thenReturn(savedNews);
        when(mapper.convert(savedNews, ReadNews.class)).thenReturn(newsToReturn);

        ResponseEntity<ReadNews> response = api.getNews(newsId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(newsToReturn);
    }

    @Test
    void updateNewsShouldResponseCorrectReturnCorrectResponseWithStatusOk() {
        Long newsId = 1000L;
        NewsDto newsDto = createBaseNewsDto();
        ReadNews newsToReturn = createBasicReadNewsModel();
        UpdateNews updatedNews = createBasicUpdateNewsModel();
        when(mapper.convert(updatedNews, NewsDto.class)).thenReturn(newsDto);
        when(newsService.update(newsId, newsDto)).thenReturn(newsDto);
        when(mapper.convert(newsDto, ReadNews.class)).thenReturn(newsToReturn);

        ResponseEntity<ReadNews> response = api.updateNews(newsId, updatedNews);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(newsToReturn);
    }
}
