package com.softserveinc.ita.homeproject.writer.service.general.news;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.softserveinc.ita.homeproject.writer.exception.NotFoundHomeException;
import com.softserveinc.ita.homeproject.writer.mapper.service.ServiceMapper;
import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.model.entity.general.news.News;
import com.softserveinc.ita.homeproject.writer.repository.general.news.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private static ServiceMapper serviceMapper;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    void createShouldSaveNews() {
        NewsDto createdNews = createBaseNewsDto();
        News savedNews = createBaseNews();
        when(serviceMapper.convert(createdNews, News.class)).thenReturn(savedNews);
        when(serviceMapper.convert(savedNews, NewsDto.class)).thenReturn(new NewsDto());
        when(newsRepository.save(savedNews)).thenReturn(savedNews);

        newsService.create(createdNews);

        verify(newsRepository, times(1)).save(savedNews);
    }

    @Test
    void createShouldReturnCorrectNews() {
        NewsDto createdNews = createBaseNewsDto();
        News savedNews = createBaseNews();
        when(serviceMapper.convert(createdNews, News.class)).thenReturn(savedNews);
        when(serviceMapper.convert(savedNews, NewsDto.class)).thenReturn(createdNews);
        when(newsRepository.save(savedNews)).thenReturn(savedNews);

        assertThat(newsService.create(createdNews)).usingRecursiveComparison().isEqualTo(createdNews);
    }

    @Test
    void updateShouldSaveNews() {
        Long newsId = 1000L;
        NewsDto newsToUpdate = createBaseNewsDto();
        News savedNews = createBaseNews();
        savedNews.setEnabled(true);
        newsToUpdate.setPhotoUrl("photoUrl");
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(savedNews));
        when(newsRepository.save(savedNews)).thenReturn(savedNews);

        newsService.update(newsId, newsToUpdate);

        verify(newsRepository, times(1)).save(savedNews);
    }

    @Test
    void updateShouldReturnUpdatedNews() {
        Long newsId = 1000L;
        NewsDto newsToUpdate = createBaseNewsDto();
        News savedNews = createBaseNews();
        savedNews.setEnabled(true);
        newsToUpdate.setPhotoUrl("photoUrl");
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(savedNews));
        when(newsRepository.save(savedNews)).thenReturn(savedNews);
        savedNews.setUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        when(serviceMapper.convert(savedNews, NewsDto.class)).thenReturn(newsToUpdate);

        assertThat(newsService.update(newsId, newsToUpdate)).usingRecursiveComparison().isEqualTo(newsToUpdate);
    }

    @Test
    void updateShouldThrowNotFoundHomeExceptionWhenNewsNotFound() {
        Long newsId = 1000L;
        NewsDto newsToUpdate = createBaseNewsDto();
        when(newsRepository.findById(newsId)).thenReturn(Optional.ofNullable(null));

        assertThatExceptionOfType(NotFoundHomeException.class)
            .isThrownBy(() -> newsService.update(newsId, newsToUpdate))
            .withMessageContaining("Can't find news with given ID: 1000");
    }

    @Test
    void deactivateNewsShouldSaveDisabledNews() {
        Long newsId = 1000L;
        News savedNews = createBaseNews();
        News expectedNews = createBaseNews();
        savedNews.setEnabled(true);
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(savedNews));
        expectedNews.setEnabled(false);

        newsService.deactivateNews(newsId);

        ArgumentCaptor<News> captor = ArgumentCaptor.forClass(News.class);
        verify(newsRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(expectedNews);
    }

    @Test
    void deactivateNewsShouldThrowNotFoundHomeExceptionWhenNewsNotFound() {
        Long newsId = 1000L;
        when(newsRepository.findById(newsId)).thenReturn(Optional.ofNullable(null));

        assertThatExceptionOfType(NotFoundHomeException.class)
            .isThrownBy(() -> newsService.deactivateNews(newsId))
            .withMessageContaining("Can't find news with given ID: 1000");
    }

    @Test
    void getOneShouldReturnNews() {
        Long newsId = 1000L;
        News savedNews = createBaseNews();
        NewsDto expectedNews = createBaseNewsDto();
        savedNews.setEnabled(true);
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(savedNews));
        when(serviceMapper.convert(savedNews, NewsDto.class)).thenReturn(expectedNews);

        assertThat(newsService.getOne(newsId)).usingRecursiveComparison().isEqualTo(expectedNews);
    }

    @Test
    void getOneShouldThrowNotFoundHomeExceptionWhenNewsNotFound() {
        Long newsId = 1000L;
        when(newsRepository.findById(newsId)).thenReturn(Optional.ofNullable(null));

        assertThatExceptionOfType(NotFoundHomeException.class)
            .isThrownBy(() -> newsService.getOne(newsId))
            .withMessageContaining("Can't find news with given ID: 1000");
    }

    @Test
    void findAllShouldReturnPageOfNews() {
        News savedNews = createBaseNews();
        NewsDto newsToReturn = createBaseNewsDto();
        when(newsRepository.findAll()).thenReturn(List.of(savedNews));
        when(serviceMapper.convert(savedNews, NewsDto.class)).thenReturn(newsToReturn);

        assertThat(newsService.findAll().getContent().get(0)).usingRecursiveComparison()
            .isEqualTo(newsToReturn);
    }

    private News createBaseNews() {
        News news = new News();

        news.setTitle("title");
        news.setDescription("description");
        news.setText("text");
        news.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        news.setEnabled(true);

        return news;
    }

    private NewsDto createBaseNewsDto() {
        NewsDto news = new NewsDto();

        news.setTitle("title");
        news.setDescription("description");
        news.setText("text");

        return news;
    }
}