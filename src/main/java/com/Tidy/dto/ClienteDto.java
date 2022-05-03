package com.Tidy.dto;

import com.Tidy.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
public class ClienteDto extends BaseDto {
    String email;
    String password;
    String nome;
    String cognome;
    String telefono;
    Boolean blacklist;
    String token;
    Boolean verify;
}