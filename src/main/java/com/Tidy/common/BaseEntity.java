package com.Tidy.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements com.Tidy.common.IdentifiableEntity {
    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_date", updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    protected LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    protected LocalDateTime modifiedDate;

    @Column(name = "deleted", columnDefinition = "integer default 0")
    protected Long deleted = 0L;
}
