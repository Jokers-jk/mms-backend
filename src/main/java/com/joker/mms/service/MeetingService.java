package com.joker.mms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.mms.model.dto.meeting.MeetingQueryRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.vo.MeetingVO;

/**
 * 会议室服务
 */
public interface MeetingService  extends IService<Meeting> {

    QueryWrapper<Meeting> getQueryWrapper(MeetingQueryRequest meetingQueryRequest);

    Page<MeetingVO> getMeetingVOPage(Page<Meeting> meetingPage);

    MeetingVO getMeetingVO(Meeting meeting);
}
