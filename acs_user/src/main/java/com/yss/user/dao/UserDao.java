package com.yss.user.dao;

import com.yss.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {

    /**
     * 更新粉丝数
     *
     * @param userid
     * @param count
     */
    @Modifying
    @Query("update User u set u.fanscount = u.fanscount + ?2 where u.id = ?1")
    void incFanscount(String userid, int count);

    /**
     * 更新关注数
     *
     * @param userid
     * @param count
     */
    @Modifying
    @Query("update User u set u.followcount = u.followcount + ?2 where u.id = ?1")
    void incFollowcount(String userid, int count);


}
