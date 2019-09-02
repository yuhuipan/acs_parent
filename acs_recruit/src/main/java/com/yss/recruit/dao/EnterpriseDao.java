package com.yss.recruit.dao;

import com.yss.recruit.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {
    /**
     * 根据热门状态获取企业列表
     *
     * @param ishot
     * @return
     */
    List<Enterprise> findByIshot(String ishot);
}
