package com.softserveinc.ita.homeproject.reader.mapper.config;


public interface TypeMatcher<S, D> {

    boolean match(Class<? extends S> sourceType, Class<? extends D> destinationType);
}
