package com.softserveinc.ita.homeproject.reader.api;

import javax.ws.rs.POST;
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
import org.springframework.stereotype.Component;
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
    @PostMapping
    public Response createNews(CreateNews createNews) {
        NewsDto newsDto = mapper.convert(createNews, NewsDto.class);
        NewsDto createdNewsDto = newsService.create(newsDto);
        ReadNews response = mapper.convert(createdNewsDto, ReadNews.class);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response deleteNews(Long id) {
        newsService.deactivateNews(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getAllNews(Integer pageNumber,
                               Integer pageSize,
                               String sort,
                               String filter,
                               Long id,
                               String title,
                               String text,
                               String source) {
        Page<NewsDto> readNews = newsService.findAll();

        return Response.ok().entity(mapper.convert(readNews, ReadNews.class)).build();
    }

    @Override
    public Response getNews(Long id) {
        NewsDto readNewsDto = newsService.getOne(id);
        ReadNews newsApiResponse = mapper.convert(readNewsDto, ReadNews.class);

        return Response.ok().entity(newsApiResponse).build();
    }

    @Override
    public Response updateNews(Long id, UpdateNews updateNews) {
        NewsDto updateNewsDto = mapper.convert(updateNews, NewsDto.class);
        NewsDto readNewsDto = newsService.update(id, updateNewsDto);
        ReadNews response = mapper.convert(readNewsDto, ReadNews.class);

        return Response.ok().entity(response).build();
    }
}
