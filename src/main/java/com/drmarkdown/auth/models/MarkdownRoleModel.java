package com.drmarkdown.auth.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "roles")
@EqualsAndHashCode(callSuper = true)
@Data
public class MarkdownRoleModel extends GenericModel {

    private String role;

    public MarkdownRoleModel() {
        super();
    }
}
