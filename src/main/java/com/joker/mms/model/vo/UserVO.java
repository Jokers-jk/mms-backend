package com.joker.mms.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户视图（脱敏）
 *
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户电话
     */
    private Long userPhoneNumber;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 所属团队
     */
    private Long teamId;



    private TeamVO teamVO;

    /**
     * 创建时间
     */
    private Date createTime;


    private static final long serialVersionUID = 1L;
}
