package com.softserveinc.ita.homeproject.reader.data.entity.general.news;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface NewsRepository extends MongoRepository<News, String> {
}
