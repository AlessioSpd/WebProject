package com.Tidy.criteria;

import com.Tidy.common.BaseCriteria;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClienteCriteria extends BaseCriteria {
    String email;
    String token;
    Boolean blacklist;
}