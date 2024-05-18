package com.joker.mms.model.dto.department;


import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentAddRequest implements Serializable {

    /**
     * name
     */
    private String departmentName;

    private static final long serialVersionUID = 1L;
}
