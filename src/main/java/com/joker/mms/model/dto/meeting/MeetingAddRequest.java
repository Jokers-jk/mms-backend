package com.joker.mms.model.dto.meeting;


import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingAddRequest implements Serializable {



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
    private Integer  meetingStatus;

    /**
     * 附带设备
     */
    private String  meetingTags;

    private static final long serialVersionUID = 1L;
}
