package com.joker.mms.model.vo;


import lombok.Data;

import java.io.Serializable;


@Data
public class DepartmentVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String departmentName;


    private static final long serialVersionUID = 1L;
}
