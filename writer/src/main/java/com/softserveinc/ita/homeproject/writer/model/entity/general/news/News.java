package com.softserveinc.ita.homeproject.writer.model.entity.general.news;

import com.softserveinc.ita.homeproject.writer.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
public class News extends BaseEntity {

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "description")
    private String description;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "source")
    private String source;

    @Column(name = "enabled")
    private Boolean enabled;
}
