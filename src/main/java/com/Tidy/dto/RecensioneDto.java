package com.Tidy.dto;

import com.Tidy.common.BaseDto;
import com.Tidy.entity.Cliente;
import com.Tidy.entity.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
public class RecensioneDto extends BaseDto {
    private ClienteDto source;
    private LocaleDto target;
    Integer rating;
    String testo;
    Integer stato;
}
