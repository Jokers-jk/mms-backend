package com.joker.mms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.mms.constant.CommonConstant;
import com.joker.mms.mapper.MeetingMapper;
import com.joker.mms.model.dto.meeting.MeetingQueryRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.entity.Team;
import com.joker.mms.model.vo.MeetingVO;
import com.joker.mms.model.vo.TeamVO;
import com.joker.mms.service.MeetingService;
import com.joker.mms.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements MeetingService {



    @Override
    public MeetingVO getMeetingVO(Meeting meeting) {
        MeetingVO meetingVO = MeetingVO.objToVo(meeting);
        return meetingVO;
    }

    @Override
    public Page<MeetingVO> getMeetingVOPage(Page<Meeting> meetingPage) {
        List<Meeting> meetingList = meetingPage.getRecords();
        Page<MeetingVO> meetingVOPage = new Page<>(meetingPage.getCurrent(), meetingPage.getSize(), meetingPage.getTotal());
        if (CollUtil.isEmpty(meetingList)) {
            return  meetingVOPage;
        }
        List<MeetingVO> meetingVOList = meetingList.stream().map(meeting ->{
            return getMeetingVO(meeting);
        }).collect(Collectors.toList());
        meetingVOPage.setRecords(meetingVOList);
        return meetingVOPage;
    }



    @Override
    public QueryWrapper<Meeting> getQueryWrapper(MeetingQueryRequest meetingQueryRequest) {
        QueryWrapper<Meeting> queryWrapper = new QueryWrapper<>();
        if(meetingQueryRequest == null){
            return queryWrapper;
        }

        Long meetingId = meetingQueryRequest.getId();
        String meetingName = meetingQueryRequest.getMeetingName();
        Long meetingCapacity = meetingQueryRequest.getMeetingCapacity();
        String meetingLocation = meetingQueryRequest.getMeetingLocation();
        Long meetingStatus = meetingQueryRequest.getMeetingStatus();
        List<String> meetingTags = meetingQueryRequest.getMeetingTags();
        String sortField = meetingQueryRequest.getSortField();
        String sortOrder = meetingQueryRequest.getSortOrder();


        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingId), "id",meetingId);
        queryWrapper.le(ObjectUtils.isNotEmpty(meetingCapacity), "meetingCapacity",meetingCapacity);
        queryWrapper.eq(ObjectUtils.isNotEmpty(meetingStatus), "meetingStatus",meetingStatus);

        if (CollUtil.isNotEmpty(meetingTags)) {
            for (String meetingTag : meetingTags) {
                queryWrapper.like("meetingTags", "\"" + meetingTag + "\"");
            }
        }

        queryWrapper.like(StringUtils.isNotBlank(meetingLocation), "meetingLocation", meetingLocation);
        queryWrapper.eq(StringUtils.isNotBlank(meetingName), "meetingName", meetingName);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}
