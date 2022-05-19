package com.softserveinc.ita.homeproject.writer.repository.general.news;

import com.softserveinc.ita.homeproject.writer.model.entity.general.news.News;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News> {
}
