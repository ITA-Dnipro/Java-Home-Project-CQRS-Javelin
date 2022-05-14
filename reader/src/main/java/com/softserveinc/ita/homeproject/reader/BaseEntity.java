package com.softserveinc.ita.homeproject.reader;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public abstract class BaseEntity {

    @Id
    String id;
}
