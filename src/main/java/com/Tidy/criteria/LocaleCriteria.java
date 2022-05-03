package com.Tidy.criteria;

import com.Tidy.common.BaseCriteria;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LocaleCriteria extends BaseCriteria {
    Long id;
    String email;
    String tag;
    String luogo;
    Integer media;
    String token;
}