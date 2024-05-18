package com.joker.mms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.mms.model.dto.department.DepartmentQueryRequest;
import com.joker.mms.model.dto.team.TeamQueryRequest;
import com.joker.mms.model.entity.Department;
import com.joker.mms.model.entity.Team;
import com.joker.mms.model.vo.TeamVO;


/**
 * 团队服务
 */
public interface TeamService  extends IService<Team> {

    QueryWrapper<Team> getQueryWrapper(TeamQueryRequest teamQueryRequest);

    Page<TeamVO> getTeamVOPage(Page<Team> teamPage);

    TeamVO getTeamVO(Team team);
}
