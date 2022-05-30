package com.softserveinc.ita.homeproject.reader.data.dto.general.news;

import com.softserveinc.ita.homeproject.reader.data.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class KafkaMessageDto extends BaseDto {

    private String title;

    private String photoUrl;

    private String description;

    private String source;

    private String text;

    private HttpMethod methodType;
}
