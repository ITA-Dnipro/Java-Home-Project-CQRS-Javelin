package com.softserveinc.ita.homeproject.reader.data.dto.general.news;

import com.softserveinc.ita.homeproject.reader.data.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewsDto extends BaseDto {

    private String title;
    private String photoUrl;
    private String description;
    private String source;
    private String text;
}
