package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.dto.ClienteDto;
import com.Tidy.dto.LocaleDto;
import com.Tidy.entity.Ordine;
import com.Tidy.dto.OrdineDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdineBidirectionalMapper extends BidirectionalMapper<OrdineDto, Ordine> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "target", target = "target")
    @Mapping(source = "orario", target = "orario")
    @Mapping(source = "dettagli", target = "dettagli")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "stato", target = "stato")
    OrdineDto toDto(Ordine entity);

    @Override
    @InheritInverseConfiguration
    Ordine toEntity(OrdineDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.source", target = "source")
    @Mapping(source = "dto.target", target = "target")
    @Mapping(source = "dto.orario", target = "orario")
    @Mapping(source = "dto.dettagli", target = "dettagli")
    @Mapping(source = "dto.info", target = "info")
    @Mapping(source = "dto.stato", target = "stato")
    Ordine toUpdateEntity(OrdineDto dto, Ordine entity);

}