package com.joker.mms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveQueryRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.entity.MeetingReserve;
import com.joker.mms.model.entity.User;
import com.joker.mms.model.vo.MeetingReserveVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 会议室预定服务
 */
public interface MeetingReserveService extends IService<MeetingReserve> {

    QueryWrapper<MeetingReserve> getConflictQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest);

    QueryWrapper<MeetingReserve> getQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest);

    QueryWrapper<MeetingReserve> getMyQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest);


    Page<MeetingReserveVO> getMeetingReserveVOPage(Page<MeetingReserve>  meetingReservePage);

    MeetingReserveVO getMeetingReserveVO(MeetingReserve meetingReserve);
}
