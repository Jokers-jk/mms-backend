package com.joker.mms.model.vo;


import cn.hutool.json.JSONUtil;
import com.joker.mms.model.entity.MeetingReserve;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
    private String theme;

    /**
     * 会议内容
     */
    private String summary;

    /**
     * 会议开始时间
     */
    private Date startTime;

    /**
     * 会议结束时间
     */
    private Date endTime;

    /**
     * 预定状态（0 - 等待中、1 - 预定中、2 - 成功、3 - 失败）
     */
    private Long status;

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
    private List<String> employeesList;

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
        List<String> employeesList = meetingReserveVO.getEmployeesList();
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
        List<String> employeesList = JSONUtil.toList(meetingReserve.getMeetingParticipants(),String.class);
        meetingReserveVO.setEmployeesList(employeesList);
        return meetingReserveVO;
    }


    private static final long serialVersionUID = 1L;
}
