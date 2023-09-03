package org.example.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.parent.BaseModel;

@Getter
@Setter
public class User extends BaseModel {

    private String name;
}
