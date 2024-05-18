package com.joker.mms.model.vo;


import cn.hutool.json.JSONUtil;
import com.joker.mms.model.entity.MeetingReserve;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class MeetingReserveVO implements Serializable {

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
    private Date meetingEndTime;

    /**
     * 预定状态（0 - 等待中、1 - 预定中、2 - 成功、3 - 失败）
     */
    private Long meetingStatus;

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
    private List<Long> meetingParticipantsList;

    /**
     * 是否签到
     */
    private Map<Long,Boolean> meetingSign;

    /**
     * 会议室信息
     */
    private MeetingVO meetingVO;

    /**
     * 参会人员信息
     */
    private List<UserVO> userVOList;

    /**
     * 包装类转对象
     *
     * @param meetingReserveVO
     * @return
     */
    public static MeetingReserve voToObj(MeetingReserveVO meetingReserveVO) {
        if (meetingReserveVO == null) {
            return null;
        }
        MeetingReserve meetingReserve = new MeetingReserve();
        BeanUtils.copyProperties(meetingReserveVO, meetingReserve);
        List<Long> employeesList = meetingReserveVO.getMeetingParticipantsList();
        if(employeesList != null){
            meetingReserve.setMeetingParticipants(JSONUtil.toJsonStr(employeesList));
        };

        return meetingReserve;
    }

    /**
     * 对象转包装类
     *
     * @param meetingReserve
     * @return
     */
    public static MeetingReserveVO objToVo(MeetingReserve meetingReserve) {
        if (meetingReserve == null) {
            return null;
        }
        MeetingReserveVO meetingReserveVO = new MeetingReserveVO();
        BeanUtils.copyProperties(meetingReserve, meetingReserveVO);
        List<Long> employeesList = JSONUtil.toList(meetingReserve.getMeetingParticipants(),Long.class);
        meetingReserveVO.setMeetingParticipantsList(employeesList);
        return meetingReserveVO;
    }


    private static final long serialVersionUID = 1L;
}
