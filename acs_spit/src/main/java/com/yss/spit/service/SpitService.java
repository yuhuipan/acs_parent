package com.yss.spit.service;

import com.yss.common.util.IdWorker;
import com.yss.spit.dao.SpitDao;
import com.yss.spit.entity.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询全部记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据主键查询实体
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 发布吐槽（或吐槽评论）
     *
     * @param spit
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + ""); //设置Id
        spit.setPublishtime(new Date()); //发布日期
        spit.setVisits(0); //浏览量
        spit.setShare(0); //分享数
        spit.setThumbup(0); //点赞数
        spit.setComment(0); //回复数
        spit.setState("1"); //状态
        if (spit.getParentid() != null && "".equals(spit.getParentid())) {
            // 存在上级id,评论,上级id对应记录的评论数加1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 根据上级ID查询吐槽列表
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return spitDao.findByParentid(parentid, pageRequest);
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void updateThumbup(String id) {
        /*Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup() + 1);
        spitDao.save(spit);*/

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
