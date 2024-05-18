package com.joker.mms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.mms.model.dto.department.DepartmentQueryRequest;
import com.joker.mms.model.entity.Department;

/**
 * 部门服务
 */
public interface DepartmentService extends IService<Department> {


     QueryWrapper<Department> getQueryWrapper(DepartmentQueryRequest departmentQueryRequest);
}
