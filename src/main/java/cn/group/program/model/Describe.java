package cn.group.program.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "describe")
public class Describe {
    private Long questionId;
    private String token;
}
