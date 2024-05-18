package com.joker.mms.model.dto.meetingreserve;



import com.joker.mms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
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
     * 发起人
     */
    private Long userId;


    /**
     * 参会人员
     */
    private List<String> meetingParticipants;


    private static final long serialVersionUID = 1L;
}
