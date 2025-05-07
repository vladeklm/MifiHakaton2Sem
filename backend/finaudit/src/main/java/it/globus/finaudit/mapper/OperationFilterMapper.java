package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.DTO.reportfilter.PeriodOperationFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationFilterMapper {

    @Mapping(target = "bankFromId", ignore = true)
    @Mapping(target = "bankToId", ignore = true)
    @Mapping(target = "statusId", ignore = true)
    @Mapping(target = "inn", ignore = true)
    @Mapping(target = "minAmount", ignore = true)
    @Mapping(target = "maxAmount", ignore = true)
    @Mapping(target = "operationTypeId", ignore = true)
    @Mapping(target = "operationCategoryId", ignore = true)
    OperationFilter toOperationFilter(PeriodOperationFilter filter);
}
