package com.softserveinc.ita.homeproject.writer.mapper.service.config;

public interface TypeMatcher<S, D> {
    boolean match(Class<? extends S> sourceType, Class<? extends D> destinationType);
}
