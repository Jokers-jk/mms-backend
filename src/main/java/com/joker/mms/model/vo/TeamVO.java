package com.joker.mms.model.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class TeamVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String teamName;

    /**
     * 所属部门
     */
    private Long departmentId;

    /**
     * 部门信息
     */
    private DepartmentVO departmentVO;


    private static final long serialVersionUID = 1L;
}
