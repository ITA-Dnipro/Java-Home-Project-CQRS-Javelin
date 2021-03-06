package com.softserveinc.ita.homeproject.writer.service.security;

import com.softserveinc.ita.homeproject.writer.model.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriInfo;

/**
 * QueryApiServiceImpl class is implementation of QueryApiService - service
 * that provides Spring Data Page by request query
 *
 * @author Oleksii Zinkevych
 * @see QueryApiService
 */
@Service
@RequiredArgsConstructor
public class QueryApiServiceImpl<T extends BaseEntity> implements QueryApiService<T> {

    private final EntitySpecificationService<T> specificationService;

    @Override
    public Specification<T> getSpecification(UriInfo uriInfo) {
        String filter = QueryApiService.getParameterValue(DefaultQueryParams.FILTER.getParameter(), uriInfo)
                .orElse(null);
        String sort = QueryApiService.getParameterValue(DefaultQueryParams.SORT.getParameter(), uriInfo)
                .orElse(DefaultQueryParams.SORT.getValue());
        return specificationService.getSpecification(QueryApiService.getFilterMap(uriInfo), filter, sort)
                .and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("enabled"), true));
    }
}
