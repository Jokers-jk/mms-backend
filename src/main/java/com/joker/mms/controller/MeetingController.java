package com.joker.mms.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.mms.annotation.AuthCheck;
import com.joker.mms.common.BaseResponse;
import com.joker.mms.common.ErrorCode;
import com.joker.mms.common.ResultUtils;
import com.joker.mms.constant.UserConstant;
import com.joker.mms.exception.BusinessException;
import com.joker.mms.exception.ThrowUtils;
import com.joker.mms.model.dto.meeting.MeetingAddRequest;
import com.joker.mms.model.dto.meeting.MeetingDeleteRequest;
import com.joker.mms.model.dto.meeting.MeetingQueryRequest;
import com.joker.mms.model.dto.meeting.MeetingUpdateRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.vo.MeetingVO;
import com.joker.mms.model.vo.TeamVO;
import com.joker.mms.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/meeting")
@Slf4j
@CrossOrigin
public class MeetingController {

    @Resource
    private MeetingService meetingService;



    @PostMapping("add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> meetingAdd(@RequestBody MeetingAddRequest meetingAddRequest){
        if (meetingAddRequest == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Meeting meeting = new Meeting();
        MeetingQueryRequest meetingQueryRequest = new MeetingQueryRequest();
        BeanUtils.copyProperties(meetingAddRequest,meeting);
        Meeting meetingTmp = new Meeting();
        meetingTmp.setMeetingName(meeting.getMeetingName());
        BeanUtils.copyProperties(meetingTmp,meetingQueryRequest);
        Long result = meetingService.count(meetingService.getQueryWrapper(meetingQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"会议室已存在");

        List<String> meetingTags = meetingAddRequest.getMeetingTags();
        if(meetingTags != null){
            meeting.setMeetingTags(JSONUtil.toJsonStr(meetingTags));
        }

        boolean b = meetingService.save(meeting);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(meeting.getId());
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteMeeting(@RequestBody MeetingDeleteRequest meetingDeleteRequest) {
        if (meetingDeleteRequest == null || meetingDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        boolean b = meetingService.removeById(meetingDeleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 分页获取会议室列表
     *
     * @param meetingQueryRequest
     * @param
     * @return
     */

    @PostMapping("/list/page")
    public BaseResponse<Page<MeetingVO>> listMeetingByPage(@RequestBody MeetingQueryRequest meetingQueryRequest) {
        long current = meetingQueryRequest.getCurrent();
        long size = meetingQueryRequest.getPageSize();
        Page<Meeting> meetingPage = meetingService.page(new Page<>(current, size),
                meetingService.getQueryWrapper(meetingQueryRequest));
        Page<MeetingVO> meetingVOPage = meetingService.getMeetingVOPage(meetingPage);
        return ResultUtils.success(meetingVOPage);
    }

    /**
     * 更新团队
     *
     * @param meetingUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateMeeting(@RequestBody MeetingUpdateRequest meetingUpdateRequest) {
        if (meetingUpdateRequest == null || meetingUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Meeting meeting = new Meeting();
        MeetingQueryRequest meetingQueryRequest = new MeetingQueryRequest();
        BeanUtils.copyProperties(meetingUpdateRequest,meeting);
        Meeting meetingName = new Meeting();
        meetingName.setMeetingName(meeting.getMeetingName());
        BeanUtils.copyProperties(meetingName,meetingQueryRequest);
        Long result = meetingService.count(meetingService.getQueryWrapper(meetingQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"会议室已存在");

        List<String> meetingTags = meetingUpdateRequest.getMeetingTags();
        if(meetingTags != null){
            meeting.setMeetingTags(JSONUtil.toJsonStr(meetingTags));
        }

        boolean b = meetingService.updateById(meeting);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(true);
    }
}
