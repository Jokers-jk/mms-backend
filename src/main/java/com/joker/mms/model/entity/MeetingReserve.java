package com.joker.mms.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "meeting_reserve")
@Data
public class MeetingReserve implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会议室名称
     */
    private Long meetingId;

    /**
     * 会议题目
     */
    private String meetingTheme;

    /**
     * 会议内容
     */
    private String meetingSummary;

    /**
     * 会议开始时间
     */
    private Date meetingStartTime;

    /**
     * 会议结束时间
     */
    private Date meetingEndTime;

    /**
     * 预定状态（0 - 等待中、1 - 预定中、2 - 成功、3 - 失败）
     */
    private Long meetingReserveStatus;

    /**
     * 发起人
     */
    private Long userId;

    /**
     * 参会人数
     */
    private Long participateNumber;

    /**
     * 参会人员
     */
    private String meetingParticipants;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
