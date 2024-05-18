package com.joker.mms.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "team")
@Data
public class Team implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;




    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
