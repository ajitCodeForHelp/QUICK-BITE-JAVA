package com.quickBite.primary.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "user_admin")
public class UserAdmin extends _BaseUser {

}