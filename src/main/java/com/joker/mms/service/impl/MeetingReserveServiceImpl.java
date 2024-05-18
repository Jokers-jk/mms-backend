package com.joker.mms.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.mms.constant.CommonConstant;
import com.joker.mms.mapper.MeetingReserveMapper;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveQueryRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.entity.MeetingReserve;
import com.joker.mms.model.entity.Team;
import com.joker.mms.model.entity.User;
import com.joker.mms.model.vo.MeetingReserveVO;
import com.joker.mms.model.vo.MeetingVO;
import com.joker.mms.model.vo.TeamVO;
import com.joker.mms.model.vo.UserVO;
import com.joker.mms.service.MeetingReserveService;
import com.joker.mms.service.MeetingService;
import com.joker.mms.service.UserService;
import com.joker.mms.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会议室预定管理
 */
@Service
@Slf4j
public class MeetingReserveServiceImpl extends ServiceImpl<MeetingReserveMapper, MeetingReserve> implements MeetingReserveService {

    @Resource
    private MeetingService meetingService;

    @Resource
    private UserService userService;


    @Override
    public QueryWrapper<MeetingReserve> getConflictQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest) {
        QueryWrapper<MeetingReserve> queryWrapper = new QueryWrapper<>();
        if(meetingReserveQueryRequest == null){
            return queryWrapper;
        }

        Long meetingId = meetingReserveQueryRequest.getMeetingId();
        Date meetingStartTime = meetingReserveQueryRequest.getMeetingStartTime();
        Date meetingEndTime = meetingReserveQueryRequest.getMeetingEndTime();
        String sortField = meetingReserveQueryRequest.getSortField();
        String sortOrder = meetingReserveQueryRequest.getSortOrder();


        queryWrapper.between(ObjectUtils.isNotEmpty(meetingStartTime), "meetingStartTime",meetingStartTime,meetingEndTime)
                .or().between(ObjectUtils.isNotEmpty(meetingEndTime), "meetingStartTime",meetingStartTime,meetingEndTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingId), "meetingId", meetingId);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QueryWrapper<MeetingReserve> getQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest) {
        QueryWrapper<MeetingReserve> queryWrapper = new QueryWrapper<>();
        if(meetingReserveQueryRequest == null){
            return queryWrapper;
        }

        Long meetingId = meetingReserveQueryRequest.getMeetingId();
        String meetingTheme = meetingReserveQueryRequest.getMeetingTheme();
        Date meetingStartTime = meetingReserveQueryRequest.getMeetingStartTime();
        Date meetingEndTime = meetingReserveQueryRequest.getMeetingEndTime();
        Long userId = meetingReserveQueryRequest.getUserId();
        Long meetingReserveStatus = meetingReserveQueryRequest.getMeetingReserveStatus();

        String sortField = meetingReserveQueryRequest.getSortField();
        String sortOrder = meetingReserveQueryRequest.getSortOrder();

        queryWrapper.ge(ObjectUtils.isNotEmpty(meetingStartTime), "meetingStartTime",meetingStartTime);
        queryWrapper.le(ObjectUtils.isNotEmpty(meetingEndTime), "meetingEndTime",meetingEndTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"userId",userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingReserveStatus),"meetingReserveStatus",meetingReserveStatus);

        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingId), "meetingId", meetingId);
        queryWrapper.like(StringUtils.isNotBlank(meetingTheme),"meetingTheme",meetingTheme);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;

    }

    @Override
    public QueryWrapper<MeetingReserve> getMyQueryWrapper(MeetingReserveQueryRequest meetingReserveQueryRequest) {
        QueryWrapper<MeetingReserve> queryWrapper = new QueryWrapper<>();
        if(meetingReserveQueryRequest == null){
            return queryWrapper;
        }

        Long meetingId = meetingReserveQueryRequest.getMeetingId();
        String meetingTheme = meetingReserveQueryRequest.getMeetingTheme();
        Date meetingStartTime = meetingReserveQueryRequest.getMeetingStartTime();
        Date meetingEndTime = meetingReserveQueryRequest.getMeetingEndTime();
        Long meetingReserveStatus = meetingReserveQueryRequest.getMeetingReserveStatus();
        List<Long> userIdList = meetingReserveQueryRequest.getMeetingParticipants();



        String sortField = meetingReserveQueryRequest.getSortField();
        String sortOrder = meetingReserveQueryRequest.getSortOrder();

        for(Long userId : userIdList){
            queryWrapper.like("meetingParticipants", "," + userId + ",").or().like("meetingParticipants", "[" + userId + ",")
                    .or().like("meetingParticipants", "," + userId + "]").or().like("meetingParticipants", "[" + userId + "]");
        }



        queryWrapper.ge(ObjectUtils.isNotEmpty(meetingStartTime), "meetingStartTime",meetingStartTime);
        queryWrapper.le(ObjectUtils.isNotEmpty(meetingEndTime), "meetingEndTime",meetingEndTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingReserveStatus),"meetingReserveStatus",meetingReserveStatus);

        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingId), "meetingId", meetingId);
        queryWrapper.like(StringUtils.isNotBlank(meetingTheme),"meetingTheme",meetingTheme);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<MeetingReserveVO> getMeetingReserveVOPage(Page<MeetingReserve> meetingReservePage) {
        List<MeetingReserve> meetingReserveList = meetingReservePage.getRecords();
        Page<MeetingReserveVO> meetingReserveVOPage = new Page<>(meetingReservePage.getCurrent(), meetingReservePage.getSize(), meetingReservePage.getTotal());
        if (CollUtil.isEmpty(meetingReserveList)) {
            return  meetingReserveVOPage;
        }
        List<MeetingReserveVO> meetingReserveVOList = meetingReserveList.stream().map(meetingReserve ->{
            return getMeetingReserveVO(meetingReserve);
        }).collect(Collectors.toList());
        meetingReserveVOPage.setRecords(meetingReserveVOList);
        return meetingReserveVOPage;
    }

    @Override
    public MeetingReserveVO getMeetingReserveVO(MeetingReserve meetingReserve) {
        MeetingReserveVO meetingReserveVO = MeetingReserveVO.objToVo(meetingReserve);
        Meeting meeting = meetingService.getById(meetingReserve.getMeetingId());
        MeetingVO meetingVO = meetingService.getMeetingVO(meeting);
        meetingReserveVO.setMeetingVO(meetingVO);

        List<Long> meetingParticipantsList = meetingReserveVO.getMeetingParticipantsList();
        if(CollUtil.isEmpty(meetingParticipantsList)){
            return meetingReserveVO;
        }

        List<UserVO> userVOList = meetingParticipantsList.stream().map(userId ->{
            return userService.getUserVO(userId);
        }).collect(Collectors.toList());
        meetingReserveVO.setUserVOList(userVOList);
        return meetingReserveVO;

    }
}
