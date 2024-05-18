package com.joker.mms.model.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户更新个人信息请求
 *
 */
@Data
public class UserUpdateMyRequest implements Serializable {
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
    private Long userPhoneNumber;


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