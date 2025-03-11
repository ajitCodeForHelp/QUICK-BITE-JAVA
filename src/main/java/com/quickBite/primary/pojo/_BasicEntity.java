package com.quickBite.primary.pojo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public abstract class _BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @MongoId
    @Field("_id")
    protected ObjectId id;

    protected boolean active = true;

    @CreatedDate
    protected LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    protected LocalDateTime modifiedAt = LocalDateTime.now();

    public String getId() {
        return id.toString();
    }

    public ObjectId getObjectId() {
        return id;
    }
}
