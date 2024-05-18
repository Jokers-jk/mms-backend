package com.joker.mms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.mms.annotation.AuthCheck;
import com.joker.mms.common.BaseResponse;
import com.joker.mms.common.DeleteRequest;
import com.joker.mms.common.ErrorCode;
import com.joker.mms.common.ResultUtils;
import com.joker.mms.constant.UserConstant;
import com.joker.mms.exception.BusinessException;
import com.joker.mms.exception.ThrowUtils;
import com.joker.mms.model.dto.department.DepartmentAddRequest;
import com.joker.mms.model.dto.department.DepartmentDeleteRequest;
import com.joker.mms.model.dto.department.DepartmentQueryRequest;
import com.joker.mms.model.dto.department.DepartmentUpdateRequest;
import com.joker.mms.model.dto.team.TeamQueryRequest;
import com.joker.mms.model.dto.user.UserQueryRequest;
import com.joker.mms.model.dto.user.UserUpdateRequest;
import com.joker.mms.model.entity.Department;
import com.joker.mms.model.entity.User;
import com.joker.mms.service.DepartmentService;
import com.joker.mms.service.TeamService;
import com.joker.mms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/department")
@Slf4j
@CrossOrigin
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @Resource
    private TeamService teamService;



    @PostMapping("add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> departmentAdd(@RequestBody DepartmentAddRequest departmentAddRequest){
        if (departmentAddRequest == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Department department = new Department();
        DepartmentQueryRequest departmentQueryRequest = new DepartmentQueryRequest();
        BeanUtils.copyProperties(departmentAddRequest,department);
        BeanUtils.copyProperties(department,departmentQueryRequest);
        Long result = departmentService.count(departmentService.getQueryWrapper(departmentQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"部门已存在");
        boolean b = departmentService.save(department);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(department.getId());
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteDepartment(@RequestBody DepartmentDeleteRequest departmentDeleteRequest) {
        if (departmentDeleteRequest == null || departmentDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        TeamQueryRequest teamQueryRequest = new TeamQueryRequest();
        teamQueryRequest.setDepartmentId(departmentDeleteRequest.getId());
        Long teamSum = teamService.count(teamService.getQueryWrapper(teamQueryRequest));
        ThrowUtils.throwIf(teamSum > 0, ErrorCode.REQUEST_ERROR,"该部门下存在团队");
        boolean b = departmentService.removeById(departmentDeleteRequest.getId());
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取部门列表
     *
     * @param departmentQueryRequest
     * @param
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Department>> listDepartmentByPage(@RequestBody DepartmentQueryRequest departmentQueryRequest) {
        long current = departmentQueryRequest.getCurrent();
        long size = departmentQueryRequest.getPageSize();
        Page<Department> departmentPage = departmentService.page(new Page<>(current, size),
                departmentService.getQueryWrapper(departmentQueryRequest));
        return ResultUtils.success(departmentPage);
    }

    /**
     * 更新部门
     *
     * @param departmentUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateDepartment(@RequestBody DepartmentUpdateRequest departmentUpdateRequest) {
        if (departmentUpdateRequest == null || departmentUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Department department = new Department();
        DepartmentQueryRequest departmentQueryRequest = new DepartmentQueryRequest();
        BeanUtils.copyProperties(departmentUpdateRequest, department);
        BeanUtils.copyProperties(department, departmentQueryRequest);
        Long result = departmentService.count(departmentService.getQueryWrapper(departmentQueryRequest));
        ThrowUtils.throwIf(result > 0, ErrorCode.REQUEST_ERROR,"部门已存在");
        boolean b = departmentService.updateById(department);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(b);
    }


}
