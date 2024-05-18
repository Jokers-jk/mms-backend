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
import com.joker.mms.model.dto.meeting.MeetingQueryRequest;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveAddRequest;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveDeleteRequest;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveQueryRequest;
import com.joker.mms.model.dto.meetingreserve.MeetingReserveUpdateRequest;
import com.joker.mms.model.entity.Meeting;
import com.joker.mms.model.entity.MeetingReserve;
import com.joker.mms.model.entity.User;
import com.joker.mms.model.enums.MeetingStatusEnum;
import com.joker.mms.model.enums.UserRoleEnum;
import com.joker.mms.model.vo.MeetingReserveVO;
import com.joker.mms.service.MeetingReserveService;
import com.joker.mms.service.MeetingService;
import com.joker.mms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/meeting_reserve")
@Slf4j
@CrossOrigin
public class MeetingReserveController {

    @Resource
    private MeetingReserveService meetingReserveService;

    @Resource
    private MeetingService meetingService;

    @Resource
    private UserService userService;



    @PostMapping("add")
    @AuthCheck(mustRole = UserConstant.EMPLOYEE_ROLE)
    public BaseResponse<Long> meetingReserveAdd(@RequestBody MeetingReserveAddRequest meetingReserveAddRequest,HttpServletRequest request){
        if (meetingReserveAddRequest == null || meetingReserveAddRequest.getMeetingId() == null || meetingReserveAddRequest.getMeetingStartTime() == null || meetingReserveAddRequest.getMeetingEndTime() == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        User user = userService.getLoginUser(request);
        MeetingReserve meetingReserve = new MeetingReserve();
        MeetingReserveQueryRequest meetingReserveQueryRequest = new MeetingReserveQueryRequest();
        BeanUtils.copyProperties(meetingReserveAddRequest,meetingReserve);
        meetingReserve.setUserId(user.getId());
        MeetingReserve meetingReserveTmp = new MeetingReserve();
        meetingReserveTmp.setMeetingEndTime(meetingReserve.getMeetingEndTime());
        meetingReserveTmp.setMeetingStartTime(meetingReserve.getMeetingStartTime());
        meetingReserveTmp.setMeetingId(meetingReserve.getMeetingId());
        BeanUtils.copyProperties(meetingReserveTmp,meetingReserveQueryRequest);

        MeetingQueryRequest meetingQueryRequest = new MeetingQueryRequest();
        meetingQueryRequest.setId(meetingReserveAddRequest.getMeetingId());
        meetingQueryRequest.setMeetingStatus(MeetingStatusEnum.READY.getValue());
        Long meetingSum = meetingService.count(meetingService.getQueryWrapper(meetingQueryRequest));
        ThrowUtils.throwIf(meetingSum < 1, ErrorCode.REQUEST_ERROR,"会议室不存在");

        Date currentDate = new Date();
        boolean flag = meetingReserveTmp.getMeetingStartTime().after(currentDate) && meetingReserveTmp.getMeetingEndTime().after(meetingReserveTmp.getMeetingStartTime());
        ThrowUtils.throwIf(!flag, ErrorCode.REQUEST_ERROR,"时间非法");

        Long result = meetingReserveService.count(meetingReserveService.getConflictQueryWrapper(meetingReserveQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"该时间段会议室已被占用");

        List<Long> participants = meetingReserveAddRequest.getMeetingParticipants();
        if(participants != null){
            meetingReserve.setMeetingParticipants(JSONUtil.toJsonStr(participants));
        }
        boolean userExist = true;
        for(Long participant : participants){
             if(userService.getById(participant) == null){
                 userExist = false;
             }
            ThrowUtils.throwIf(!userExist, ErrorCode.REQUEST_ERROR,"用户不存在");
        }

        meetingReserve.setParticipateNumber((long) meetingReserveAddRequest.getMeetingParticipants().size());


        boolean b = meetingReserveService.save(meetingReserve);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(meetingReserve.getId());
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.EMPLOYEE_ROLE)
    public BaseResponse<Boolean> deleteMeetingReserve(@RequestBody MeetingReserveDeleteRequest meetingReserveDeleteRequest,HttpServletRequest request) {

        if (meetingReserveDeleteRequest == null || meetingReserveDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        User user = userService.getLoginUser(request);
        MeetingReserve meetingReserve = meetingReserveService.getById(meetingReserveDeleteRequest.getId());
        boolean result = meetingReserve.getUserId().equals(user.getId()) || user.getUserRole().equals(UserRoleEnum.ADMIN.getValue());
        ThrowUtils.throwIf(!result,ErrorCode.FORBIDDEN_ERROR,"不是会议发起人或管理员无法取消会议");
        boolean b = meetingReserveService.removeById(meetingReserveDeleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 分页获取会议记录列表
     *
     * @param meetingReserveQueryRequest
     * @param
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<MeetingReserveVO>> listMeetingReserveByPage(@RequestBody MeetingReserveQueryRequest meetingReserveQueryRequest) {
        long current = meetingReserveQueryRequest.getCurrent();
        long size = meetingReserveQueryRequest.getPageSize();
        Page<MeetingReserve> meetingReservePage = meetingReserveService.page(new Page<>(current, size),
                meetingReserveService.getQueryWrapper(meetingReserveQueryRequest));
        Page<MeetingReserveVO> meetingReserveVOPage = meetingReserveService.getMeetingReserveVOPage(meetingReservePage);
        return ResultUtils.success(meetingReserveVOPage);
    }

    /**
     * 分页获取自己的会议记录
     *
     * @param meetingReserveQueryRequest
     * @param
     * @return
     */
    @PostMapping("/list/page/my")
    public BaseResponse<Page<MeetingReserveVO>> listMyMeetingReserveByPage(@RequestBody MeetingReserveQueryRequest meetingReserveQueryRequest,HttpServletRequest request) {
        long current = meetingReserveQueryRequest.getCurrent();
        long size = meetingReserveQueryRequest.getPageSize();
        User user = userService.getLoginUser(request);

        meetingReserveQueryRequest.setMeetingParticipants(Collections.singletonList(user.getId()));

        Page<MeetingReserve> meetingReservePage = meetingReserveService.page(new Page<>(current, size),
                meetingReserveService.getMyQueryWrapper(meetingReserveQueryRequest));
        Page<MeetingReserveVO> meetingReserveVOPage = meetingReserveService.getMeetingReserveVOPage(meetingReservePage);
        return ResultUtils.success(meetingReserveVOPage);
    }

    /**
     * 更新会议记录
     *
     * @param meetingReserveUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.EMPLOYEE_ROLE)
    public BaseResponse<Boolean> updateMeetingReserve(@RequestBody MeetingReserveUpdateRequest meetingReserveUpdateRequest,HttpServletRequest request) {
        if (meetingReserveUpdateRequest == null || meetingReserveUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        User user = userService.getLoginUser(request);
        MeetingReserve meetingReserve = meetingReserveService.getById(meetingReserveUpdateRequest.getId());
        boolean result = meetingReserve.getUserId().equals(user.getId()) || user.getUserRole().equals(UserRoleEnum.ADMIN.getValue());
        ThrowUtils.throwIf(!result,ErrorCode.FORBIDDEN_ERROR,"不是会议发起人或管理员无法修改会议记录");

        if(meetingReserveUpdateRequest.getMeetingId()!= null){
            Meeting meeting = new Meeting();
            MeetingQueryRequest meetingQueryRequest = new MeetingQueryRequest();
            meeting.setMeetingStatus(MeetingStatusEnum.READY.getValue());
            meeting.setId(meetingReserveUpdateRequest.getMeetingId());
            Long meetingSum = meetingService.count(meetingService.getQueryWrapper(meetingQueryRequest));
            ThrowUtils.throwIf(meetingSum < 1,ErrorCode.SYSTEM_ERROR,"会议室维护中或会议室不存在");
        }

        if(meetingReserveUpdateRequest.getMeetingStartTime() != null && meetingReserveUpdateRequest.getMeetingEndTime() != null){
            MeetingReserve meetingReserveTmp = new MeetingReserve();
            MeetingReserveQueryRequest meetingReserveQueryRequest = new MeetingReserveQueryRequest();
            meetingReserveTmp.setMeetingId(meetingReserveUpdateRequest.getMeetingId());
            meetingReserveTmp.setMeetingStartTime(meetingReserveUpdateRequest.getMeetingStartTime());
            meetingReserveTmp.setMeetingEndTime(meetingReserveUpdateRequest.getMeetingEndTime());
            BeanUtils.copyProperties(meetingReserveTmp,meetingReserveQueryRequest);
            Date currentDate = new Date();
            boolean flag = meetingReserveTmp.getMeetingStartTime().after(currentDate) && meetingReserveTmp.getMeetingEndTime().after(meetingReserveTmp.getMeetingStartTime());
            ThrowUtils.throwIf(!flag, ErrorCode.REQUEST_ERROR,"时间非法");
            Long meetingReserveSum = meetingReserveService.count(meetingReserveService.getQueryWrapper(meetingReserveQueryRequest));
            ThrowUtils.throwIf(meetingReserveSum > 0, ErrorCode.REQUEST_ERROR,"该时间段会议室已被占用");
        }




        List<Long> participants = meetingReserveUpdateRequest.getMeetingParticipants();
        if(participants != null){
            meetingReserve.setMeetingParticipants(JSONUtil.toJsonStr(participants));
        }

        boolean b = meetingReserveService.updateById(meetingReserve);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(true);
    }
}
