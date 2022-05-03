package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.dto.ClienteDto;
import com.Tidy.dto.LocaleDto;
import com.Tidy.entity.Prenotazione;
import com.Tidy.dto.PrenotazioneDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrenotazioneBidirectionalMapper extends BidirectionalMapper<PrenotazioneDto, Prenotazione> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "numeroPersone", target = "numeroPersone")
    @Mapping(source = "data", target = "data")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "target", target = "target")
    @Mapping(source = "stato", target = "stato")
    PrenotazioneDto toDto(Prenotazione entity);

    @Override
    @InheritInverseConfiguration
    Prenotazione toEntity(PrenotazioneDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.numeroPersone", target = "numeroPersone")
    @Mapping(source = "dto.data", target = "data")
    @Mapping(source = "dto.source", target = "source")
    @Mapping(source = "dto.target", target = "target")
    @Mapping(source = "dto.stato", target = "stato")
    Prenotazione toUpdateEntity(PrenotazioneDto dto, Prenotazione entity);

}