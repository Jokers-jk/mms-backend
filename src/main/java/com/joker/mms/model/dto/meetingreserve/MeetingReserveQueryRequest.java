package com.joker.mms.model.dto.meetingreserve;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingReserveQueryRequest extends PageRequest implements Serializable {

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
     * 会议开始时间
     */

    private Date meetingStartTime;

    /**
     * 会议结束时间
     */
    private  Date meetingEndTime;


    /**
     * 发起人
     */
    private Long userId;

    /**
     * 预定状态（0 - 等待中、1 - 预定中、2 - 成功、3 - 失败）
     */
    private Long meetingReserveStatus;


    /**
     * 参会人员
     */
    private List<Long> meetingParticipants;




    private static final long serialVersionUID = 1L;
}
