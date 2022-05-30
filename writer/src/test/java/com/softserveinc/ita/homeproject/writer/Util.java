package com.softserveinc.ita.homeproject.writer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.softserveinc.ita.homeproject.writer.model.CreateNews;
import com.softserveinc.ita.homeproject.writer.model.ReadNews;
import com.softserveinc.ita.homeproject.writer.model.UpdateNews;
import com.softserveinc.ita.homeproject.writer.model.dto.general.news.NewsDto;
import com.softserveinc.ita.homeproject.writer.model.entity.general.news.News;

public class Util {

    public static News createBaseNews() {
        News news = new News();

        news.setTitle("title");
        news.setDescription("description");
        news.setText("text");
        news.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        news.setEnabled(true);

        return news;
    }

    public static NewsDto createBaseNewsDto() {
        NewsDto news = new NewsDto();

        news.setTitle("title");
        news.setDescription("description");
        news.setText("text");

        return news;
    }

    public static CreateNews createBasicModelForNewsCreation() {
        return new CreateNews()
            .title("title")
            .description("description")
            .text("text");
    }

    public static ReadNews createBasicReadNewsModel() {
        return new ReadNews()
            .title("title")
            .description("description")
            .text("text");
    }

    public static UpdateNews createBasicUpdateNewsModel() {
        return new UpdateNews()
            .title("title")
            .description("description")
            .text("text");
    }
}
