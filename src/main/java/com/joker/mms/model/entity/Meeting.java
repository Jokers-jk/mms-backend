package com.joker.mms.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "meeting")
@Data
public class Meeting implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * name
     */
    private String meetingName;

    /**
     * 容纳人数
     */
    private Long  meetingCapacity;

    /**
     * 会议室地点
     */
    private String  meetingLocation;

    /**
     * 会议室状态
     */
    private Long  meetingStatus;

    /**
     * 温度
     */
    private String  meetingTemperature;

    /**
     * 湿度
     */
    private String  meetingHumidity;

    /**
     * 光照强度
     */
    private String meetingIllumination;

    /**
     * 附带设备
     */
    private String  meetingTags;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
