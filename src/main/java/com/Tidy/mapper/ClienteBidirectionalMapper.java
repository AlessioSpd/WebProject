package com.Tidy.mapper;

import com.Tidy.common.BidirectionalMapper;
import com.Tidy.entity.Cliente;
import com.Tidy.dto.ClienteDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteBidirectionalMapper extends BidirectionalMapper<ClienteDto, Cliente> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "cognome", target = "cognome")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "blacklist", target = "blacklist")
    @Mapping(source = "verify", target = "verify")
    @Mapping(source = "token", target = "token")
    ClienteDto toDto(Cliente entity);

    @Override
    @InheritInverseConfiguration
    Cliente toEntity(ClienteDto dto);

    @Override
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.createdDate", target = "createdDate")
    @Mapping(source = "dto.modifiedDate", target = "modifiedDate")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.password", target = "password")
    @Mapping(source = "dto.nome", target = "nome")
    @Mapping(source = "dto.cognome", target = "cognome")
    @Mapping(source = "dto.telefono", target = "telefono")
    @Mapping(source = "dto.blacklist", target = "blacklist")
    @Mapping(source = "dto.verify", target = "verify")
    @Mapping(source = "dto.token", target = "token")
    Cliente toUpdateEntity(ClienteDto dto, Cliente entity);
}