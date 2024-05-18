package com.joker.mms.model.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户更新请求
 *
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;


    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;


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
    private Integer phoneNumber;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;


    /**
     * 出生日期
     */
    private Date birthday;


    /**
     * 人脸数据
     */
    private String tags;

    private static final long serialVersionUID = 1L;
}
