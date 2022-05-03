package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.entity.Amministratore;
import com.Tidy.dto.AmministratoreDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmministratoreBidirectionalMapper extends BidirectionalMapper<AmministratoreDto, Amministratore> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")

    AmministratoreDto toDto(Amministratore entity);

    @Override
    @InheritInverseConfiguration
    Amministratore toEntity(AmministratoreDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.password", target = "password")
    Amministratore toUpdateEntity(AmministratoreDto dto, Amministratore entity);

}