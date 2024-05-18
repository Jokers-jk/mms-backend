package com.joker.mms.model.dto.user;

import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

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

    private static final long serialVersionUID = 1L;
}