package com.softserveinc.ita.homeproject.reader.api;

import java.util.List;
import java.util.stream.Collectors;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.softserveinc.ita.homeproject.reader.mapper.HomeMapper;
import com.softserveinc.ita.homeproject.reader.security.QueryApiService;
import com.softserveinc.ita.homeproject.reader.service.QueryableService;
import com.softserveinc.ita.homeproject.reader.service.dto.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class CommonApi {

    private static final String PAGING_COUNT = "Paging-count";

    private static final String PAGING_TOTAL_PAGES = "Paging-total-pages";

    private static final String PAGING_TOTAL_COUNT = "Paging-total-count";

    @Context
    private UriInfo uriInfo;

    @Autowired
    protected HomeMapper mapper;

    @Autowired
    private QueryApiService queryApiService;

    protected <D extends BaseDto> Response buildQueryResponse(Page<D> page, Class clazz) {
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int numberOfElements = page.getNumberOfElements();

        List<?> pageElements = page.stream()
            .map(p -> mapper.convert(p, clazz))
            .collect(Collectors.toList());

        return Response.status(Response.Status.OK)
            .entity(pageElements)
            .header(PAGING_COUNT, numberOfElements)
            .header(PAGING_TOTAL_PAGES, totalPages)
            .header(PAGING_TOTAL_COUNT, totalElements)
            .build();
    }

    protected <T> Specification<T> getSpecification() {
        return queryApiService.getSpecification(uriInfo);
    }

    public void verifyExistence(Long parentId, QueryableService service){
        service.getOne(parentId);
    }
}
