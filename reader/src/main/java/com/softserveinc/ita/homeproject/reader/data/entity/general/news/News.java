package com.softserveinc.ita.homeproject.reader.data.entity.general.news;

import java.time.LocalDateTime;

import com.softserveinc.ita.homeproject.reader.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@EqualsAndHashCode(callSuper = true)
@Document
public class News extends BaseEntity {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String title;
    private String text;
    private String description;
    private String photoUrl;
    private String source;
    private Boolean enabled;
}
