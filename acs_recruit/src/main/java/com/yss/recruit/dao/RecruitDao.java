package com.yss.recruit.dao;

import com.yss.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    //推荐的职位列表  查询tb_recruit表中state为2(推荐), 并以创建日期降序排序，查询前4条记录
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    //最新职位 查询状态不为0(关闭的不查询出来)并以创建日期降序排序，查询前12条记录
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
