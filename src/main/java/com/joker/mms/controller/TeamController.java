package com.joker.mms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.mms.annotation.AuthCheck;
import com.joker.mms.common.BaseResponse;
import com.joker.mms.common.ErrorCode;
import com.joker.mms.common.ResultUtils;
import com.joker.mms.constant.UserConstant;
import com.joker.mms.exception.BusinessException;
import com.joker.mms.exception.ThrowUtils;
import com.joker.mms.model.dto.department.DepartmentQueryRequest;
import com.joker.mms.model.dto.team.TeamAddRequest;
import com.joker.mms.model.dto.team.TeamDeleteRequest;
import com.joker.mms.model.dto.team.TeamQueryRequest;
import com.joker.mms.model.dto.team.TeamUpdateRequest;
import com.joker.mms.model.dto.user.UserQueryRequest;
import com.joker.mms.model.entity.Team;
import com.joker.mms.model.vo.TeamVO;
import com.joker.mms.service.DepartmentService;
import com.joker.mms.service.TeamService;
import com.joker.mms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/team")
@Slf4j
@CrossOrigin
public class TeamController {

    @Resource
    private TeamService teamService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private UserService userService;



    @PostMapping("add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> teamAdd(@RequestBody TeamAddRequest teamAddRequest){
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Team team = new Team();
        TeamQueryRequest teamQueryRequest = new TeamQueryRequest();
        BeanUtils.copyProperties(teamAddRequest,team);
        BeanUtils.copyProperties(team,teamQueryRequest);
        DepartmentQueryRequest departmentQueryRequest = new DepartmentQueryRequest();
        departmentQueryRequest.setId(teamAddRequest.getDepartmentId());
        Long departmentSum = departmentService.count(departmentService.getQueryWrapper(departmentQueryRequest));
        ThrowUtils.throwIf(departmentSum < 1, ErrorCode.REQUEST_ERROR,"部门不存在");
        Long result = teamService.count(teamService.getQueryWrapper(teamQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"团队已存在");

        boolean b = teamService.save(team);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(team.getId());
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteTeam(@RequestBody TeamDeleteRequest teamDeleteRequest) {
        if (teamDeleteRequest == null || teamDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setTeamId(teamDeleteRequest.getId());
        Long userSum = userService.count(userService.getQueryWrapper(userQueryRequest));
        ThrowUtils.throwIf(userSum > 0, ErrorCode.REQUEST_ERROR,"团队下存在用户");
        boolean b = teamService.removeById(teamDeleteRequest.getId());
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取团队列表
     *
     * @param teamQueryRequest
     * @param
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<TeamVO>> listTeamByPage(@RequestBody TeamQueryRequest teamQueryRequest) {
        long current = teamQueryRequest.getCurrent();
        long size = teamQueryRequest.getPageSize();

        Page<Team> teamPage = teamService.page(new Page<>(current, size),
                teamService.getQueryWrapper(teamQueryRequest));
        Page<TeamVO> teamVOPage = teamService.getTeamVOPage(teamPage);
        return ResultUtils.success(teamVOPage);
    }

    /**
     * 更新团队
     *
     * @param teamUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest) {
        if (teamUpdateRequest == null || teamUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Team team = new Team();
        TeamQueryRequest teamQueryRequest = new TeamQueryRequest();
        BeanUtils.copyProperties(teamUpdateRequest, team);
        BeanUtils.copyProperties(team,teamQueryRequest);
        if(teamUpdateRequest.getDepartmentId() != null ){
            DepartmentQueryRequest departmentQueryRequest = new DepartmentQueryRequest();
            departmentQueryRequest.setId(teamUpdateRequest.getDepartmentId());
            Long departmentSum = departmentService.count(departmentService.getQueryWrapper(departmentQueryRequest));
            ThrowUtils.throwIf(departmentSum < 1, ErrorCode.REQUEST_ERROR,"部门不存在");
        }
        Long result = teamService.count(teamService.getQueryWrapper(teamQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"团队已存在");
        boolean b = teamService.updateById(team);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(true);
    }
}
