package com.example.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author banana
 * @create 2023-12-05 20:30
 */
@Data
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

}
