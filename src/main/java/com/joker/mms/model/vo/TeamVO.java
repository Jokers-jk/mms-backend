package com.joker.mms.model.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class GroupVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String name;

    /**
     * 所属部门
     */
    private Long departmentId;


    private static final long serialVersionUID = 1L;
}
