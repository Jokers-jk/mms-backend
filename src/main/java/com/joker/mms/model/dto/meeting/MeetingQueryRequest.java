package com.joker.mms.model.dto.meeting;


import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String  meetingName;

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
