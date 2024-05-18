package com.joker.mms.model.vo;


import cn.hutool.json.JSONUtil;
import com.joker.mms.model.entity.Meeting;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;


@Data
public class MeetingVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String meetingName;

    /**
     * 容纳人数
     */
    private Long meetingCapacity;

    /**
     * 会议室地点
     */
    private String meetingLocation;

    /**
     * 会议室状态
     */
    private Long meetingStatus;

    /**
     * 温度
     */
    private String  meetingTemperature;

    /**
     * 湿度
     */
    private String  meetingHumidity;

    /**
     * 气压
     */
    private String meetingPressure;

    /**
     * 附带设备
     */
    private List<String> meetingTags;

    /**
     * 包装类转对象
     *
     * @param meetingVO
     * @return
     */
    public static Meeting voToObj(MeetingVO meetingVO) {
        if (meetingVO == null) {
            return null;
        }
        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(meetingVO, meeting);
        List<String> tagList = meetingVO.getMeetingTags();
        if(tagList != null){
            meeting.setMeetingTags(JSONUtil.toJsonStr(tagList));
        };
        return meeting;
    }

    /**
     * 对象转包装类
     *
     * @param meeting
     * @return
     */
    public static MeetingVO objToVo(Meeting meeting) {
        if (meeting == null) {
            return null;
        }
        MeetingVO meetingVO = new MeetingVO();
        BeanUtils.copyProperties(meeting, meetingVO);
        List<String> tagList = JSONUtil.toList(meeting.getMeetingTags(),String.class);
        meetingVO.setMeetingTags(tagList);
        return meetingVO;
    }

    private static final long serialVersionUID = 1L;
}
