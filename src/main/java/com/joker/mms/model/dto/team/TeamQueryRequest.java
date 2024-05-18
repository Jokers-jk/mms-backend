package com.joker.mms.model.dto.team;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamQueryRequest implements Serializable {

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
    private static final long serialVersionUID = 1L;
}
