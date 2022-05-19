package com.softserveinc.ita.homeproject.writer.mapper.service;

import com.softserveinc.ita.homeproject.writer.mapper.service.config.AbstractTypeConverter;
import com.softserveinc.ita.homeproject.writer.mapper.service.config.ServiceMappingConfig;
import com.softserveinc.ita.homeproject.writer.model.dto.BaseDto;
import com.softserveinc.ita.homeproject.writer.model.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.ConditionalConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ServiceMapper {

    private static final String ENTITY_PACKAGE = "com.softserveinc.ita.homeproject.homedata";

    private static final String DTO_PACKAGE = "com.softserveinc.ita.homeproject.homeservice.dto";

    private ModelMapper modelMapper;

    private final Set<ServiceMappingConfig<?,?>> serviceMappingConfigs;

    @PostConstruct
    @SuppressWarnings({"unchecked cast", "rawtypes"})
    public void init() {
        modelMapper = new ModelMapper();

        ConditionalConverter<BaseEntity, BaseDto> conditionalConverter =
            new AbstractTypeConverter<>(ENTITY_PACKAGE, DTO_PACKAGE);
        ConditionalConverter<BaseDto, BaseEntity> conditionalConverter2 =
            new AbstractTypeConverter<>(DTO_PACKAGE, ENTITY_PACKAGE);

        modelMapper.getConfiguration().getConverters().add(0, conditionalConverter);
        modelMapper.getConfiguration().getConverters().add(0, conditionalConverter2);

        for (ServiceMappingConfig serviceMappingConfig : serviceMappingConfigs) {
            TypeMap typeMap = modelMapper.typeMap(
                    serviceMappingConfig.getSourceType(), serviceMappingConfig.getDestinationType());
            serviceMappingConfig.addMappings(typeMap);
        }
    }

    public <D> D convert(Object source, Class<D> destination) {
        return modelMapper.map(source, destination);
    }
}
