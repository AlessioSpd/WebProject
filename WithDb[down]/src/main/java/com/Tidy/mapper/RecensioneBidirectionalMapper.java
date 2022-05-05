package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.dto.ClienteDto;
import com.Tidy.dto.LocaleDto;
import com.Tidy.entity.Recensione;
import com.Tidy.dto.RecensioneDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecensioneBidirectionalMapper extends BidirectionalMapper<RecensioneDto, Recensione> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "target", target = "target")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "testo", target = "testo")
    @Mapping(source = "stato", target = "stato")
    RecensioneDto toDto(Recensione entity);

    @Override
    @InheritInverseConfiguration
    Recensione toEntity(RecensioneDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.source", target = "source")
    @Mapping(source = "dto.target", target = "target")
    @Mapping(source = "dto.rating", target = "rating")
    @Mapping(source = "dto.testo", target = "testo")
    @Mapping(source = "dto.stato", target = "stato")
    Recensione toUpdateEntity(RecensioneDto dto, Recensione entity);

}