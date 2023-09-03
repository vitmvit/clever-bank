package org.example.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.parent.BaseModel;

@Getter
@Setter
public class Bank extends BaseModel {

    private String name;
}
