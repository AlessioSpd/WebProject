package com.Tidy.dto;

import com.Tidy.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
public class LocaleDto extends BaseDto {
    String email;
    String password;
    String nome;
    String luogo;
    String telefono;
    Boolean parcheggio;
    Boolean pos;
    Boolean accettaOrdini;
    Boolean accettaRecensioni;
    String menu;
    String tag;
    String foto;
    String descrizione;
    Integer media;
    String token;
    Boolean verify;
}
