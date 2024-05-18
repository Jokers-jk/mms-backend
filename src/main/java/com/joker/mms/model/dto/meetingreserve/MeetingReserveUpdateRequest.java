package com.joker.mms.model.dto.meetingreserve;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class MeetingReserveUpdateRequest implements Serializable {

    /**
     * id
     */
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
    private  Date meetingEndTime;


    /**
     * 参会人数
     */
    private Long participateNumber;

    /**
     * 参会人员
     */
    private List<Long> meetingParticipants;


    private static final long serialVersionUID = 1L;
}
