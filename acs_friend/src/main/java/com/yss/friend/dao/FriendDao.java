package com.yss.friend.dao;

import com.yss.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 根据用户Id与被关注用户查询记录个数
     *
     * @param userid
     * @param friendid
     * @return
     */
    @Query("select count(f) from Friend f where f.userid = ?1 and f.friendid = ?2")
    int selectCount(String userid, String friendid);

    /**
     * 更新为互相喜欢
     *
     * @param userid
     * @param friendid
     * @param islike
     */
    @Modifying
    @Query("update Friend f set f.islike = ?3 where f.userid = ?1 and f.friendid = ?2")
    void updateLike(String userid, String friendid, String islike);

    /**
     * 删除喜欢
     *
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2")
    void deleteFriend(String userid, String friendid);
}
