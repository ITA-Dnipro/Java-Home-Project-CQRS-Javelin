package com.softserveinc.ita.homeproject.writer.mapper.model;

import com.softserveinc.ita.homeproject.writer.mapper.model.config.HomeMappingConfig;
import com.softserveinc.ita.homeproject.writer.mapper.service.config.AbstractTypeConverter;
import com.softserveinc.ita.homeproject.writer.model.dto.BaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.ConditionalConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HomeMapper {

    public static final String MODEL_PACKAGE = "com.softserveinc.ita.homeproject.writer.model.entity";

    public static final String DTO_PACKAGE = "com.softserveinc.ita.homeproject.writer.model.dto";

    private ModelMapper modelMapper;

    private final Set<HomeMappingConfig<?,?>> homeMappingConfigs;

    @PostConstruct
    @SuppressWarnings({"unchecked cast", "rawtypes"})
    public void init() {
        modelMapper = new ModelMapper();

        ConditionalConverter<Object, BaseDto> conditionalConverter =
            new AbstractTypeConverter<>(MODEL_PACKAGE, DTO_PACKAGE, (s, d) -> {
                String sourceSimpleDomainName = getSimpleDomainName(s);
                String destinationSimpleName = d.getSimpleName();

                return destinationSimpleName.contains(sourceSimpleDomainName)
                    || sourceSimpleDomainName.contains(destinationSimpleName);
            });
        ConditionalConverter<BaseDto, Object> conditionalConverter2 =
            new AbstractTypeConverter<>(DTO_PACKAGE, MODEL_PACKAGE, (s, d) -> {
                String sourceSimpleDomainName = s.getSimpleName();
                String destinationSimpleName = getSimpleDomainName(d);

                return destinationSimpleName.contains(sourceSimpleDomainName)
                    || sourceSimpleDomainName.contains(destinationSimpleName);
            }, (r, d) -> !r.getSubTypesOf(d).isEmpty());

        modelMapper.getConfiguration().getConverters().add(0, conditionalConverter);
        modelMapper.getConfiguration().getConverters().add(0, conditionalConverter2);

        for (HomeMappingConfig homeMappingConfig : homeMappingConfigs) {
            TypeMap typeMap =
                    modelMapper.typeMap(homeMappingConfig.getSourceType(), homeMappingConfig.getDestinationType());
            homeMappingConfig.addMappings(typeMap);
        }
    }

    private static String getSimpleDomainName(Class<?> s) {
        return s.getSimpleName().replace("Read", "").replace("Create", "").replace("Update", "");
    }

    public <D> D convert(Object source, Class<D> destination) {
        return modelMapper.map(source, destination);
    }
}
