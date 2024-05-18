package com.joker.mms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.mms.constant.CommonConstant;
import com.joker.mms.mapper.DepartmentMapper;
import com.joker.mms.model.dto.department.DepartmentQueryRequest;
import com.joker.mms.model.entity.Department;
import com.joker.mms.service.DepartmentService;
import com.joker.mms.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 部门服务实现
 *
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public QueryWrapper<Department> getQueryWrapper(DepartmentQueryRequest departmentQueryRequest) {
     QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
     if(departmentQueryRequest == null){
        return queryWrapper;
     }
     Long departmentId = departmentQueryRequest.getId();
     String departmentName = departmentQueryRequest.getDepartmentName();
     String sortField = departmentQueryRequest.getSortField();
     String sortOrder = departmentQueryRequest.getSortOrder();

     queryWrapper.eq(ObjectUtils.isNotEmpty(departmentId), "id",departmentId);
     queryWrapper.eq(StringUtils.isNotBlank(departmentName), "departmentName", departmentName);
     queryWrapper.eq("isDelete",false);
     queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
     return queryWrapper;
    }

}
