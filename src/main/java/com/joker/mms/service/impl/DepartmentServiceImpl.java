package com.joker.mms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.mms.mapper.DepartmentMapper;
import com.joker.mms.model.entity.Department;
import com.joker.mms.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 部门服务实现
 *
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
