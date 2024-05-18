package com.joker.mms.model.dto.team;

import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamQueryRequest  extends PageRequest implements Serializable {

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
