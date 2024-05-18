package com.joker.mms.model.dto.department;

import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentQueryRequest extends PageRequest implements Serializable {

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
