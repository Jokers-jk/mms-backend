package com.joker.mms.controller;


import com.joker.mms.common.BaseResponse;
import com.joker.mms.common.ErrorCode;
import com.joker.mms.exception.BusinessException;
import com.joker.mms.exception.ThrowUtils;
import com.joker.mms.model.dto.department.DepartmentAddRequest;
import com.joker.mms.model.entity.Department;
import com.joker.mms.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import com.joker.mms.common.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/department")
@Slf4j
@CrossOrigin
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;


    @PostMapping("add")
    public BaseResponse<Long> departmentAdd(@RequestBody DepartmentAddRequest departmentAddRequest){
        if (departmentAddRequest == null) {
            throw new BusinessException(ErrorCode.REQUEST_ERROR);
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentAddRequest,department);
        boolean result = departmentService.save(department);
        ThrowUtils.throwIf(!result, ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(department.getId());

    }


}
