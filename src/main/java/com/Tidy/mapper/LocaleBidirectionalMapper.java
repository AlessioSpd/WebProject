package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.entity.Locale;
import com.Tidy.dto.LocaleDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocaleBidirectionalMapper extends BidirectionalMapper<LocaleDto, Locale> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "luogo", target = "luogo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "parcheggio", target = "parcheggio")
    @Mapping(source = "pos", target = "pos")
    @Mapping(source = "accettaOrdini", target = "accettaOrdini")
    @Mapping(source = "accettaRecensioni", target = "accettaRecensioni")
    @Mapping(source = "menu", target = "menu")
    @Mapping(source = "tag", target = "tag")
    @Mapping(source = "foto", target = "foto")
    @Mapping(source = "descrizione", target = "descrizione")
    @Mapping(source = "media", target = "media")
    @Mapping(source = "verify", target = "verify")
    @Mapping(source = "token", target = "token")
    LocaleDto toDto(Locale entity);

    @Override
    @InheritInverseConfiguration
    Locale toEntity(LocaleDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.password", target = "password")
    @Mapping(source = "dto.nome", target = "nome")
    @Mapping(source = "dto.luogo", target = "luogo")
    @Mapping(source = "dto.telefono", target = "telefono")
    @Mapping(source = "dto.parcheggio", target = "parcheggio")
    @Mapping(source = "dto.pos", target = "pos")
    @Mapping(source = "dto.accettaOrdini", target = "accettaOrdini")
    @Mapping(source = "dto.accettaRecensioni", target = "accettaRecensioni")
    @Mapping(source = "dto.menu", target = "menu")
    @Mapping(source = "dto.tag", target = "tag")
    @Mapping(source = "dto.foto", target = "foto")
    @Mapping(source = "dto.descrizione", target = "descrizione")
    @Mapping(source = "dto.media", target = "media")
    @Mapping(source = "dto.verify", target = "verify")
    @Mapping(source = "dto.token", target = "token")
    Locale toUpdateEntity(LocaleDto dto, Locale entity);

}