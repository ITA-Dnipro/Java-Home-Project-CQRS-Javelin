package com.softserveinc.ita.homeproject.writer.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.softserveinc.ita.homeproject.writer.model.CreateNews;
import com.softserveinc.ita.homeproject.writer.model.ReadNews;
import com.softserveinc.ita.homeproject.writer.model.UpdateNews;
import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.service.general.news.NewsService;
import org.springframework.data.domain.Page;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * NewsApiServiceImpl class is the inter layer between generated
 * News controller and service layer of the application.
 *
 * @author Ihor Svyrydenko
 */

@Provider
@Component
public class NewsApiImpl extends CommonApi implements NewsApi {

    private final NewsService newsService;

    public NewsApiImpl(NewsService newsService) {
        this.newsService = newsService;
    }

    //    @PreAuthorize(MANAGE_NEWS)
    @Override
    public Response createNews(CreateNews createNews) {
        NewsDto newsDto = mapper.convert(createNews, NewsDto.class);
        NewsDto createdNewsDto = newsService.create(newsDto);
        ReadNews response = mapper.convert(createdNewsDto, ReadNews.class);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

//    @PreAuthorize(MANAGE_NEWS)
    @Override
    public Response deleteNews(Long id) {
        newsService.deactivateNews(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

//    @PreAuthorize(READ_NEWS)
    @Override
    public Response getAllNews(Integer pageNumber,
                               Integer pageSize,
                               String sort,
                               String filter,
                               Long id,
                               String title,
                               String text,
                               String source) {

        Page<NewsDto> readNews = newsService.findAll(pageNumber, pageSize, getSpecification());
        return buildQueryResponse(readNews, ReadNews.class);
    }

//    @PreAuthorize(READ_NEWS)
    @Override
    public Response getNews(Long id) {
        NewsDto readNewsDto = newsService.getOne(id);
        ReadNews newsApiResponse = mapper.convert(readNewsDto, ReadNews.class);
        return Response.ok().entity(newsApiResponse).build();
    }

//    @PreAuthorize(MANAGE_NEWS)
    @Override
    public Response updateNews(Long id, UpdateNews updateNews) {
        NewsDto updateNewsDto = mapper.convert(updateNews, NewsDto.class);
        NewsDto readNewsDto = newsService.update(id, updateNewsDto);
        ReadNews response = mapper.convert(readNewsDto, ReadNews.class);
        return Response.ok().entity(response).build();
    }
}
